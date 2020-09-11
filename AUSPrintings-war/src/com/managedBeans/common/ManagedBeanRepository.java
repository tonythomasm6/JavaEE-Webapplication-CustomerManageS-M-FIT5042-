package com.managedBeans.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.entities.Agent;
import com.entities.Customer;
import com.entities.IndustryType;
import com.repository.AgentRepository;
import com.repository.CustomerRepository;

@Named
@SessionScoped
public class ManagedBeanRepository implements Serializable {
	
	@EJB
	AgentRepository agentRepository;
	
	@EJB
	CustomerRepository customerRepository;
	
	public Agent getLoggedAgentDetails(String loggedUserName) {
		Agent agent = agentRepository.getLoggedAgentDetails(loggedUserName);
		return agent;
	}
	
	public List<Customer> getCustomers(int agentId, String role){
		List<Customer> customers = customerRepository.getCustomers(agentId, role);
		return customers;
		
	}
	
	public void addCustomer(Customer customer) {
		customerRepository.addCustomer(customer);
	}

	public List<IndustryType> getAllIndustryTypes() {
		List<IndustryType> allIndustryTypes = agentRepository.getAllIndustryTypes();
		return allIndustryTypes;
	}

	
}
