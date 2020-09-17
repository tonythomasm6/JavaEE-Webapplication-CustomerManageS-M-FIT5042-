package com.managedBeans.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.entities.Agent;
import com.entities.Customer;
import com.entities.CustomerContact;
import com.entities.IndustryType;
import com.repository.AgentRepository;
import com.repository.ContactsRepository;
import com.repository.CustomerRepository;

@Named
@SessionScoped
public class ManagedBeanRepository implements Serializable {
	
	@EJB
	AgentRepository agentRepository;
	
	@EJB
	CustomerRepository customerRepository;
	
	@EJB
	ContactsRepository contactsRepository;
	
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

	public Customer getCustomer(int customerId) {
		Customer customer = customerRepository.getCustomer(customerId);
		return customer;
	}
	
	public void editCustomer(Customer c) {
		customerRepository.editCustomer(c);
	}
	
	public  void deleteCustomer(Customer customer) {
		customerRepository.deleteCustomer(customer);
	}
	
	public List<CustomerContact> getContactsForCustomer(int customerId){
		List<CustomerContact> contacts = contactsRepository.getContactsForCustomer(customerId);
		return contacts;
	}
	
	public void addCustomerContact(CustomerContact c) {
		contactsRepository.addCustomerContact(c);
	}
	
	public CustomerContact getCustomerContactFromId(int contactId) {
		CustomerContact contact = contactsRepository.getCustomerContactFromId(contactId);
		return contact;
	}
	
	public void editContact(CustomerContact c) {
		contactsRepository.editContact(c);
	}
	
	public void deleteContact(CustomerContact c) {
		contactsRepository.deleteContact(c);
	}
	
}
