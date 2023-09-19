package com.shopping.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopping.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{
	
	List<Customer> findByEmail(String email);

}
