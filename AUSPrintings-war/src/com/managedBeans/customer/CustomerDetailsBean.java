package com.managedBeans.customer;

import java.io.Serializable;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Customer;
import com.managedBeans.common.ManagedBeanRepository;

@Named
@RequestScoped
public class CustomerDetailsBean implements Serializable {

	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;
	
	private int customerId;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	private Customer customer;
	
	public CustomerDetailsBean() {
		customerId = Integer.valueOf(FacesContext.getCurrentInstance()
		                .getExternalContext()
		                .getRequestParameterMap()
		                .get("customerID"));
		
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "managedBeanRepository");
		
		customer = getCustomer(customerId);
		
	}
	
	
	public Customer getCustomer(int customerId) {
		customer = managedBeanRepository.getCustomer(customerId);
		return customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}