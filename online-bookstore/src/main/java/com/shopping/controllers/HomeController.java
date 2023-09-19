package com.shopping.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.shopping.models.Admin;
import com.shopping.models.Cart;
import com.shopping.models.Customer;
import com.shopping.models.Order;
import com.shopping.models.OrderDetails;
import com.shopping.models.Product;
import com.shopping.services.AdminService;
import com.shopping.services.CartService;
import com.shopping.services.CategoryService;
import com.shopping.services.CustomerService;
import com.shopping.services.OrderDetailsService;
import com.shopping.services.OrderService;
import com.shopping.services.ProductService;

@Controller
@SessionAttributes({"cqty","userid","uname"})
public class HomeController {
	
	@Autowired
	private CategoryService catsrv;
	@Autowired private ProductService prodsrv;
	@Autowired private HttpSession session;
	@Autowired private CustomerService custsrv;
	@Autowired private CartService cartsrv;
	@Autowired private OrderService ordersrv;
	@Autowired private OrderDetailsService odsrv;
	@Autowired private AdminService vsrv;
	
	@GetMapping(path = {"/","/{page}"})
	public String homepage(@PathVariable("page") Optional<Integer> page,Model model,String name) {
		int pageno=page.isPresent() ? page.get()-1 : 0;
		Page<Product> plist=prodsrv.allProducts(pageno,name);
		System.err.println(plist.getSize());
		model.addAttribute("prods", plist.toList());
		model.addAttribute("current", pageno+1);
		model.addAttribute("totalpages", plist.getTotalPages());
		model.addAttribute("totalprods", plist.getTotalElements());
		model.addAttribute("cats", catsrv.getAllCategories());
		return "index";
	}
	
	@GetMapping(path = {"/product","/product/{page}"})
	public String productPage(@PathVariable("page") Optional<Integer> page,Model model,String name) {
		int pageno=page.isPresent() ? page.get()-1 : 0;
		Page<Product> plist=prodsrv.allProducts(pageno,name);
		System.err.println(plist.getSize());
		model.addAttribute("prods", plist.toList());
		model.addAttribute("current", pageno+1);
		model.addAttribute("totalpages", plist.getTotalPages());
		model.addAttribute("totalprods", plist.getTotalElements());
		model.addAttribute("cats", catsrv.getAllCategories());
		return "cproducts";
	}
	
	@GetMapping("/cats/{id}")
	public String listbycategory(Model model,@PathVariable("id") int id) {
		model.addAttribute("cats", catsrv.getAllCategories());
		model.addAttribute("prods", prodsrv.allCategoryProducts(id));
		return "catlist";
	}
	
	@GetMapping("/addtocart/{id}")
	public String addtocart(Model model,@PathVariable("id") int id) {
		model.addAttribute("cats", catsrv.getAllCategories());
		model.addAttribute("p", prodsrv.findProductById(id));
		return "addtocart";
	}
	
	@PostMapping("/addtocart/{prodid}")
	public String saveItemtoCart(Cart c,Model model) {
		String userid=session.getAttribute("userid").toString();
		c.setUserid(userid);
		System.out.println(c);
		if(cartsrv.checkItem(c)) {
			session.setAttribute("error", "Item already in cart");
		}else {
			cartsrv.saveItem(c);
			model.addAttribute("cqty", cartsrv.getItemsinCart(userid));
			session.setAttribute("msg", "Item added to cart");
		}
		return "redirect:/";
	}
	
	@GetMapping("/invoice/{id}")
	public String invoice(@PathVariable("id") int orderid,Model model) {
		Order order=ordersrv.getOrderDetails(orderid);
		List<OrderDetails> odlist=odsrv.allItemsinOrder(orderid);
		System.out.println("Total items : "+odlist.size());
		model.addAttribute("cats", catsrv.getAllCategories());
		model.addAttribute("o", order);
		model.addAttribute("items",odlist);	
		return "invoice";
	}
	
	@PostMapping("/placeorder")
	public String placeorder(Order order,Model model) {
		String userid=session.getAttribute("userid").toString();
		int id=ordersrv.placeOrder(order,userid);
		model.addAttribute("cqty", cartsrv.getItemsinCart(userid));
		session.setAttribute("msg", "Order Placed Successfully");
		return "redirect:/invoice/"+id;
	}
	
	@GetMapping("/history")
	public String orderhistory(Model model) {
		String userid=session.getAttribute("userid").toString();
		model.addAttribute("cats", catsrv.getAllCategories());
		model.addAttribute("orders", ordersrv.allUserOrders(userid));
		return "history";
	}
	
	@GetMapping("/orderdetails/{id}")
	public String orderDetails(Model model,@PathVariable("id") int orderid) {
		Order order=ordersrv.getOrderDetails(orderid);
		List<OrderDetails> odlist=odsrv.allItemsinOrder(orderid);
		System.out.println("Total items : "+odlist.size());
		model.addAttribute("cats", catsrv.getAllCategories());
		model.addAttribute("o", order);
		model.addAttribute("items",odlist);		
		return "order-details";
	}
	
	@GetMapping("/cancel/{id}")
	public String cancelOrder(@PathVariable("id") int orderid){
		ordersrv.cancelOrder(orderid);
		session.setAttribute("msg", "Order Cancelled successfully");
		return "redirect:/history";
	}
	
	
	@GetMapping("/cart")
	public String viewcart(Model model) {
		String userid=session.getAttribute("userid").toString();
		List<Cart> items=cartsrv.findItemsByUserId(userid);
		int total=0;
		for(Cart i : items) {
			total+= (i.getQty()*i.getProduct().getSaleprice());
		}
		model.addAttribute("items", items);
		model.addAttribute("cqty", cartsrv.getItemsinCart(userid));
		model.addAttribute("ctotal", total);
		model.addAttribute("ctax", (total*.10));
		model.addAttribute("netamount", total+(total*.10));
		model.addAttribute("cats", catsrv.getAllCategories());
		return "cart";
	}
	
	@GetMapping("/delcart/{id}")
	public String deleteitemfromcart(@PathVariable("id") int id,Model model) {
		cartsrv.deleteItem(id);
		String userid=session.getAttribute("userid").toString();
		model.addAttribute("cqty", cartsrv.getItemsinCart(userid));
		session.setAttribute("msg", "Item deleted from cart");
		return "redirect:/cart";
	}
	
	@GetMapping("/login")
	public String loginpage(Model model) {
		model.addAttribute("customer", new Customer());
		model.addAttribute("genders", new String[] {"Male","Female"});
		model.addAttribute("cats", catsrv.getAllCategories());
		return "login";
	}
	
	@PostMapping("/login")
	public String validate(String userid,String pwd,Model model) {
		Customer c=custsrv.ValidateLogin(userid, pwd);
		Admin v=vsrv.ValidateLogin(userid, pwd);
		if(c!=null) {
			session.setAttribute("userid", userid);
			session.setAttribute("uname", c.getFname()+" "+c.getLname());
			model.addAttribute("cqty", cartsrv.getItemsinCart(userid));
			return "redirect:/";
		}
		else if(v!=null) {
			session.setAttribute("userid", userid);
			session.setAttribute("uname", v.getUname());
			session.setAttribute("role", "Admin");
			return "redirect:/dashboard";
		}
		else {
			session.setAttribute("error", "Invalid username or password");
			return "redirect:/login";
		}		
	}	
	
	@PostMapping("/register")
	public String registerUser(String cpwd,Model model,@Valid Customer c,BindingResult br) {
		boolean valid=true;
		model.addAttribute("genders", new String[] {"Male","Female"});
		if(br.hasErrors()) {
			valid=false;
		}
		if(custsrv.VerifyUser(c.getUserid()).equals("no")) {						
			br.rejectValue("userid", "error.userid", "User ID not available");
			valid=false;
		}
		if(custsrv.VerifyEmail(c.getEmail()).equals("no")) {						
			br.rejectValue("email", "error.email", "Email ID already taken");
			valid=false;
		}
		if(!cpwd.equals(c.getPwd())) {
			br.rejectValue("cpwd","error.cpwd","Password not match");
			valid=false;
		} 
		if(!valid) {
			model.addAttribute("customer", c);
			return "login";
		}
		else {
			Customer cust=custsrv.saveCustomer(c);
			session.setAttribute("userid", cust.getUserid());
			session.setAttribute("uname", cust.getFname());		
			return "redirect:/";
		}
	}
	
	@GetMapping("/cchangepwd")
	public String changepasswordpage(Model model) {
		model.addAttribute("cats", catsrv.getAllCategories());
		return "changepwd";
	}
	
	@PostMapping("/cchangepwd")
	public String changepassword(String opwd,String pwd) {
		String userid=session.getAttribute("userid").toString();
		if(custsrv.updatePassword(userid, pwd, opwd)) {
			session.setAttribute("msg", "Password updated successfully");
		}
		else {
			session.setAttribute("error", "Incorrect current password");
		}
		return "redirect:/cchangepwd";
	}
}
