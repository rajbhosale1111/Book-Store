package com.shopping.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.models.Admin;
import com.shopping.repos.AdminRepository;

@Service
public class AdminService {
	
	@Autowired AdminRepository vrepo;
	
	public void registerAdmin(Admin v) {
		vrepo.save(v);
	}
	
	public boolean updatePassword(String userid,String opwd,String pwd) {
		Admin c=findAdminById(userid);
		System.err.println(pwd);
		if(c.getPwd().equals(opwd)) {
			c.setPwd(pwd);
			vrepo.save(c);
			return true;
		}
		return false;
	}
	
	public Admin findAdminById(String vid) {
		Optional<Admin> v=vrepo.findById(vid);
		if(v.isPresent())
			return vrepo.findById(vid).get();
		else
			return null;
	}
	
	public long countVendor() {
		return vrepo.count();
	}
	
	public Admin ValidateLogin(String userid,String pwd) {
		Admin c=findAdminById(userid);
		if(c==null) {
			return null;
		}
		else  {
			if(c.getPwd().equals(pwd))
				return c;
			else 
				return null;
		}
	}
	
	
}
