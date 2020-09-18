package com.managedBeans.contacts;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Agent;
import com.entities.Customer;
import com.entities.CustomerContact;
import com.managedBeans.common.ManagedBeanRepository;
import com.managedBeans.customer.ManageCustomersBean;

@Named
@SessionScoped // Needs to be session scoped as we need to call command action methods too.
				// Otherwise, if request scoped,
// only constructor will be called every time.
//If this method is request and action methods are defined in controller which is session scoped, still only the constructor
//of this class will be called.
public class ManageContactBean implements Serializable {

	private List<Customer> customers;

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	private List<CustomerContact> contacts;

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
	
	@ManagedProperty(value = "#{manageCustomersBean}")
	private ManageCustomersBean manageCustomersBean;

	public ManageContactBean() {
		
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "managedBeanRepository");
		
		manageCustomersBean = (ManageCustomersBean) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "manageCustomersBean");
		
		//To load the customer details from customer bean.
		customers = manageCustomersBean.getCustomers();
		
		//updateCustomersFromDB();
	}

	
	
	//This variable is given as hidden parameter in xhtml to ensure that the updated customers list is loaded 
	//everytime the managecontacts xhtml is loaded.
	private String onloadvar = "";
	public String getOnloadvar() {
		this.customers = manageCustomersBean.getCustomers();
		return onloadvar;
	}

	public void setOnloadvar(String onloadvar) {
		this.onloadvar = onloadvar;
	}
	
	
	
	
	
	
	

	// To load all contacts for the selected customer from dropdown
	//// this method is in this class as manageContact xhtml is binded to this class
	// and contacts is field of this class.
	public void getContactsForCustomer(int customerId) {
		contacts = managedBeanRepository.getContactsForCustomer(customerId);

		// To clear any messags in screen
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<FacesMessage> it = context.getMessages();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}

	}

	/*
	// Update customers after adding
	public void updateCustomersFromDB() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		Agent loggedAgent = new Agent();
		try {
			loggedAgent = (Agent) sessionMap.get("loggedAgent");
		} catch (Exception e) {

		}
		// To get list of all customers to populate drop down in manage contacts page
		this.customers = managedBeanRepository.getCustomers(loggedAgent.getAgentId(), loggedAgent.getRole());
	}
	*/

	// Method to delete customer contact for a customer
	public void deleteContact(CustomerContact contact) {

		managedBeanRepository.deleteContact(contact);
		// Customer ID to load the selected customer contacts after deletion
		int customerId = Integer.valueOf(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("customerId"));

		getContactsForCustomer(customerId);

		FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
				new FacesMessage("New Customer Deleted succesfully"));

	}

	public void addContacts(ContactBean contact) {
		CustomerContact customerContact = convertBeanToEntity(contact);
		managedBeanRepository.addCustomerContact(customerContact);

		getContactsForCustomer(selectedCustomerId);

		FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
				new FacesMessage("New Customer Contact added succesfully"));

	}

	
	//Method to edit the customer contact details
		public void editContact(CustomerContact contact) {
			managedBeanRepository.editContact(contact);
			FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
					new FacesMessage("New Customer Contact details edited succesfully"));
			
			//To update contact details after editing
			getContactsForCustomer(selectedCustomerId);
		}
		
		
		
		
		
		
	public CustomerContact convertBeanToEntity(ContactBean contact) {

		CustomerContact customerContact = new CustomerContact();
		customerContact.setFirstName(contact.getFirstName());
		customerContact.setLastName(contact.getLastName());
		customerContact.setEmail(contact.getEmail());
		customerContact.setPhoneNumber(contact.getPhoneNumber());
		customerContact.setBusinessPhone(contact.getBusinessPhone());

		int customerId = contact.getCustomerId();

		for (Customer c : customers) {
			if (c.getCustomerId() == customerId) {
				customerContact.setCustomer(c);
			}
		}

		return customerContact;

	}

}
