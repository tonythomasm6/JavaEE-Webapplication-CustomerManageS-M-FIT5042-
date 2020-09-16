package com.managedBeans.contacts;

import java.io.Serializable;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.CustomerContact;
import com.managedBeans.common.ManagedBeanRepository;

@Named
@RequestScoped
public class ContactDetailsBean implements Serializable {

	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;
	
	
	private int customerContactId;
	
	private CustomerContact contact;

	public ContactDetailsBean() {
		customerContactId = Integer.valueOf(FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("customerContactId"));

		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "managedBeanRepository");
		
		contact = getCustomerContactFromId(customerContactId);
	}
	
	public int getCustomerContactId() {
		return customerContactId;
	}

	public void setCustomerContactId(int customerContactId) {
		this.customerContactId = customerContactId;
	}

	public CustomerContact getContact() {
		return contact;
	}

	public void setContact(CustomerContact contact) {
		this.contact = contact;
	}
	
	
	//Method to get customerContact from customercontactid
	public CustomerContact getCustomerContactFromId(int contactId) {
		return managedBeanRepository.getCustomerContactFromId(contactId);
	}
	
	//Method to edit the customer contact details
	public void editContact(CustomerContact contact) {
		managedBeanRepository.editContact(contact);
	}
}
