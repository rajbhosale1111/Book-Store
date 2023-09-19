package com.shopping.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.shopping.models.Cart;
import com.shopping.models.Customer;
import com.shopping.models.Order;
import com.shopping.models.OrderDetails;
import com.shopping.repos.OrderRepository;

@Service
public class OrderService {
	
	@Autowired private OrderRepository orepo;
	@Autowired private CartService cartsrv;
	@Autowired private CustomerService custsrv;
	@Autowired private OrderDetailsService odsrv;
	
	public List<Order> allOrders(){
		return orepo.findAll(Sort.by(Direction.DESC,"id"));
	}
	
	public List<Order> pendingOrders(){
		return orepo.findByStatusOrderByIdDesc("Pending");
	}
	
	public List<Order> allUserOrders(String userid){
		return orepo.findByUseridOrderByIdDesc(userid);
	}
	
	public Order getOrderDetails(int orderid) {
		return orepo.findById(orderid).get();
	}
	
	public void cancelOrder(int orderid) {
		odsrv.deleteAllItems(orderid);
		orepo.deleteById(orderid);
	}
	
	public void confirmOrder(int id) {
		Order od=orepo.findById(id).get();
		od.setStatus("Confirmed");
		orepo.save(od);
	}
	
	public int placeOrder(Order o,String userid) {
		
		Customer customer=custsrv.findByUserId(userid);
		o.setUserid(userid);
		o.setCustomer(customer);
		Order order=orepo.save(o);
		List<Cart> cartitems=cartsrv.findItemsByUserId(userid);
		for(Cart c : cartitems) {
			OrderDetails od=new OrderDetails();
			od.setOrder(order);
			od.setOrderid(order.getId());
			od.setProduct(c.getProduct());
			od.setQty(c.getQty());
			od.setProdid(c.getProdid());
			od.setPrice(c.getProduct().getSaleprice());
			od.setStatus("Pending");
			odsrv.saveItem(od);
		}		
		cartsrv.emptyCart(userid);
		return o.getId();
	}
}
