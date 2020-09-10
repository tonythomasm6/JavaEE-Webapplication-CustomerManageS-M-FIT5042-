package com.managedBeans.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Agent;
import com.entities.Customer;
import com.managedBeans.common.ManagedBeanRepository;




@Named
@RequestScoped
public class ManageCustomersBean  implements Serializable{

	private List<Customer> customers;
	
	public List<Customer> getCustomers() {
		return customers;
	}


	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}


	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;
	
	
	public ManageCustomersBean() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		 managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
	                .getELResolver().getValue(elContext, null, "managedBeanRepository");
		 
		 //To get all customers based on role
		 //If role is admin, all customers are required.
		 //If role is staff, only the customers of that particular staff is returned.
		 
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
}
