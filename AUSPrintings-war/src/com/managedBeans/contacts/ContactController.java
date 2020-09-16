package com.managedBeans.contacts;

import java.io.Serializable;

import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.CustomerContact;
import com.managedBeans.common.ManagedBeanRepository;

@Named
@SessionScoped
public class ContactController implements Serializable {
	/*
	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;

	public ContactsController() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "managedBeanRepository");
	}
	
	
	public void addContacts(ContactsBean contact) {
		CustomerContact customerContact = convertBeanToEntity(contact);
	}
	
	public CustomerContact convertBeanToEntity(ContactsBean contact) {
		
		CustomerContact customerContact = new CustomerContact();
		customerContact.setFirstName(contact.getFirstName());
		customerContact.setLastName(contact.getLastName());
		customerContact.setEmail(contact.getEmail());
		customerContact.setPhoneNumber(contact.getPhoneNumber());
		customerContact.setBusinessPhone(contact.getBusinessPhone());
		return null;
	}*/
}
