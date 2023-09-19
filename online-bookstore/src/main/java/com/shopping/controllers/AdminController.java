package com.shopping.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.shopping.models.Category;
import com.shopping.models.Order;
import com.shopping.models.OrderDetails;
import com.shopping.models.Product;
import com.shopping.services.AdminService;
import com.shopping.services.CategoryService;
import com.shopping.services.CustomerService;
import com.shopping.services.OrderDetailsService;
import com.shopping.services.OrderService;
import com.shopping.services.ProductService;

@Controller
public class AdminController {
	
	@Autowired private CategoryService catsrv;
	@Autowired private ProductService prodsrv;
	@Autowired private CustomerService custsrv;
	@Autowired private OrderService ordersrv;
	@Autowired private OrderDetailsService odsrv;
	@Autowired private AdminService vsrv;
	@Autowired private HttpSession session;
	

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("totalusers", custsrv.allCustomers().size());
		model.addAttribute("totalproducts", prodsrv.countProducts());
		model.addAttribute("totalcategories", catsrv.totalCategories());
		model.addAttribute("totalorders", ordersrv.allOrders().size());		
		return "dashboard";
	}
	
	@GetMapping("/customers")
	public String customerlist(Model model){	
		model.addAttribute("users",custsrv.allCustomers());
		return "customers";
	}
	
	@GetMapping("/orders")
	public String orderslist(Model model){
		model.addAttribute("orders", ordersrv.allOrders());
		return "orders";
	}
	
	@GetMapping("/confirm/{id}")
	public String confirmOrder(@PathVariable("id") int orderid){
		ordersrv.confirmOrder(orderid);
		session.setAttribute("msg", "Order Confirmed successfully");
		return "redirect:/orders";
	}
	
	@GetMapping("/details/{id}")
	public String orderdetails(@PathVariable("id") int orderid, Model model){
		Order order=ordersrv.getOrderDetails(orderid);
		List<OrderDetails> odlist=odsrv.allItemsinOrder(orderid);
		System.out.println("Total items : "+odlist.size());
		model.addAttribute("cats", catsrv.getAllCategories());
		model.addAttribute("o", order);
		model.addAttribute("items",odlist);		
		model.addAttribute("cqty", odlist.size());
		return "orderdetails";
	}
	
	@GetMapping("/changepwd")
	public String changepassword(){		
		return "achangepwd";
	}
	
	@PostMapping("/changepwd")
	public String changepassword(String userid,String cpwd,String pwd,String opwd) {	
		System.out.println(userid);
		if(vsrv.updatePassword(userid, opwd, pwd)) {
			session.setAttribute("msg", "Password updated successfully");
		}
		else {
			session.setAttribute("error", "Incorrect current password");
		}
		return "redirect:/changepwd";
	}
	
	@GetMapping(path = {"/products","/products/{page}"})
	public String products(@PathVariable("page") Optional<Integer> page,Model model,String name) {
		int pageno=page.isPresent() ? page.get()-1 : 0;
		Page<Product> plist=prodsrv.allProducts(pageno,name);
		model.addAttribute("prods", plist.toList());
		model.addAttribute("current", pageno+1);
		model.addAttribute("totalpages", plist.getTotalPages());
		model.addAttribute("totalprods", plist.getTotalElements());
		model.addAttribute("cats", catsrv.getAllCategories());
		return "products";
	}
	
	@GetMapping("/delprod/{id}")
	public String deleteproduct(@PathVariable("id") int id) {
		prodsrv.deleteProduct(id);
		session.setAttribute("msg", "Book deleted successfully");
		return "redirect:/products";
	}
	
	@PostMapping("/products")
	public String saveProduct(MultipartFile photo,Product p) {

		Category cat=catsrv.findByCatId(p.getCatid());
		p.setCategory(cat);
		System.err.println(p);
		prodsrv.saveProduct(p, photo);
		session.setAttribute("msg", "Book saved successfully");
		return "redirect:/products";
	}
	
	@GetMapping(path = {"/categories","/categories/{id}"})
	public String categories(Model model,@PathVariable("id") Optional<Integer> id) {
		if(id.isPresent()) {
			Category cat=catsrv.findByCatId(id.get());
			model.addAttribute("catid",cat.getCatid());
			model.addAttribute("catname",cat.getCatname());				
		}else {			
			model.addAttribute("catid", catsrv.generateCatId());		
		}
		model.addAttribute("cats", catsrv.getAllCategories());		
		return "category";
	}	

	@PostMapping(path = {"/categories","/categories/{id}"})
	public String saveCategories(Category cat) {
		System.out.println("catid : "+cat.getCatname());
		catsrv.saveCategory(cat);
		session.setAttribute("msg", "Category saved successfully");
		return "redirect:/categories";
	}
	
	@GetMapping("/logout")
	public String logout(){
		session.invalidate();
		return "redirect:/";
	}
}
