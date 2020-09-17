package com.managedBeans.contacts;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Agent;
import com.entities.Customer;
import com.entities.CustomerContact;
import com.managedBeans.common.ManagedBeanRepository;

@RequestScoped
@Named
public class ContactBean implements Serializable {

	private int customerContactId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String businessPhone;
	private String email;
	private int customerId;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	private List<Customer> customers;

	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;

	public ContactBean() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		 managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
	                .getELResolver().getValue(elContext, null, "managedBeanRepository");
		 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		 Map<String, Object> sessionMap = externalContext.getSessionMap();
		 Agent loggedAgent = new Agent();
		 try {
			  loggedAgent = (Agent) sessionMap.get("loggedAgent");
		 }
		 catch(Exception e) {
			 
		 }
		 
		 this.customers = managedBeanRepository.getCustomers(loggedAgent.getAgentId(), loggedAgent.getRole());
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public int getCustomerContactId() {
		return customerContactId;
	}

	public void setCustomerContactId(int customerContactId) {
		this.customerContactId = customerContactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/*

	public void addContacts(ContactBean contact) {
		CustomerContact customerContact = convertBeanToEntity(contact);
		managedBeanRepository.addCustomerContact(customerContact);
		
		FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
				new FacesMessage("New Customer Contact added succesfully"));
		
	}
	
	public CustomerContact convertBeanToEntity(ContactBean contact) {
		
		CustomerContact customerContact = new CustomerContact();
		customerContact.setFirstName(contact.getFirstName());
		customerContact.setLastName(contact.getLastName());
		customerContact.setEmail(contact.getEmail());
		customerContact.setPhoneNumber(contact.getPhoneNumber());
		customerContact.setBusinessPhone(contact.getBusinessPhone());

		int customerId = contact.getCustomerId();
		
		for(Customer c: customers) {
			if(c.getCustomerId() == customerId) {
				customerContact.setCustomer(c);
			}
		}
		
		return customerContact;
		
	}
	
	
	*/

}
