package com.repository;

import java.util.ArrayList;
import java.util.List;

import com.entities.Customer;

public interface CustomerRepository {

	public List<Customer> getCustomers(int agentId, String role);
	
	public void addCustomer(Customer customer);
	
	public Customer getCustomer(int customerId);
	
	public void editCustomer(Customer c);
	
	public void deleteCustomer(Customer customer);
}
