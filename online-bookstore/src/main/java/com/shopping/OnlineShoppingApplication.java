package com.shopping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.shopping.models.Admin;
import com.shopping.services.AdminService;


@SpringBootApplication
@EnableJpaAuditing
public class OnlineShoppingApplication {
	
	private static final Logger log = LoggerFactory.getLogger(OnlineShoppingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(AdminService srv) {
	    return (args) -> {
	    	if(srv.countVendor()==0) {
	    		Admin user=new Admin();
	    		user.setUserid("admin");
	    		user.setPwd("admin123");
	    		user.setUname("Administrator");
	    		srv.registerAdmin(user);
	    		log.info("Admin user created successfully");
	    	}else {
	    		log.info("Admin user already created");
	    	}
	    };
	}
}
