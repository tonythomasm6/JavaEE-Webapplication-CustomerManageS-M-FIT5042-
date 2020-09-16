package com.managedBeans.contacts;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Agent;
import com.entities.Customer;
import com.entities.CustomerContact;
import com.managedBeans.common.ManagedBeanRepository;

@Named
@RequestScoped
public class manageContactsBean  implements Serializable{
	
	private List<Customer> customers;
	
	public List<Customer> getCustomers() {
		return customers;
	}


	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	private List<CustomerContact> contacts ;

	public List<CustomerContact> getContacts() {
		return contacts;
	}


	public void setContacts(List<CustomerContact> contacts) {
		this.contacts = contacts;
	}
	
	private int selectedCustomerId;

	public int getSelectedCustomerId() {
		return selectedCustomerId;
	}


	public void setSelectedCustomerId(int selectedCustomerId) {
		this.selectedCustomerId = selectedCustomerId;
	}

	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;
	
	public manageContactsBean() {
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
	
	
	public void getContactsForCustomer(int customerId) {
		contacts = managedBeanRepository.getContactsForCustomer(customerId);
	}
}
