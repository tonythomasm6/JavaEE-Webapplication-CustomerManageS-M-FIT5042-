package com.managedBeans.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Address;
import com.entities.Agent;
import com.entities.Customer;
import com.entities.IndustryType;
import com.managedBeans.common.ManagedBeanRepository;

@Named
@SessionScoped
public class ManageCustomersBean implements Serializable {

	private List<Customer> customers;
	
	private boolean isAdmin = false;

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

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

		// Method to update customers from DB
		updateCustomersFromDB();

	}

	public void updateCustomersFromDB() {

		// To get all customers based on role
		// If role is admin, all customers are required.
		// If role is staff, only the customers of that particular staff is returned.

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		Agent loggedAgent = new Agent();
		try {
			loggedAgent = (Agent) sessionMap.get("loggedAgent");
		} catch (Exception e) {

		}
		this.customers = managedBeanRepository.getCustomers(loggedAgent.getAgentId(), loggedAgent.getRole());
		if(loggedAgent.getRole().equalsIgnoreCase("admin")) {
			this.isAdmin = true;
		}
		
	}

	public Customer convertManagedBeantoEntity(CustomerBean customerManagedBean) {
		Customer customer = new Customer(); // Entity
		customer.setFirstName(customerManagedBean.getFirstName());
		customer.setLastName(customerManagedBean.getLastName());
		customer.setPhoneNo(customerManagedBean.getPhoneNo());
		customer.setEmail(customerManagedBean.getEmail());

		Address address = new Address();
		address.setStreetNumber(customerManagedBean.getStreetNumber());
		address.setStreetAddress(customerManagedBean.getStreetAddress());
		address.setSuburb(customerManagedBean.getSuburb());
		address.setCity(customerManagedBean.getCity());
		address.setPostcode(customerManagedBean.getPostcode());
		address.setState(customerManagedBean.getState());

		customer.setAddress(address);

		// Getting agentID details
		// Getting logged in agent details from session
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		Agent agent = new Agent();
		agent = (Agent) sessionMap.get("loggedAgent");
		
		if(agent.getRole().equalsIgnoreCase("admin")) {
			List<Agent> allStaff = managedBeanRepository.getAllStaff();
			for(Agent a : allStaff) {
				if(a.getAgentId() == customerManagedBean.getSelectedStaffId()) {
					customer.setAgent(a);
				}
			}
		}
		else {
				customer.setAgent(agent);
		}
		
		/** Getting industrytype details from the selected industry id **/
		int typeId = customerManagedBean.getTypeId();
		IndustryType industry = getIndustryTypeFromId(typeId);
		customer.setIndustryType(industry);

		return customer;
	}

	public IndustryType getIndustryTypeFromId(int typeId) {
		// Getting all industryTypes
		List<IndustryType> allIndustryTypes = managedBeanRepository.getAllIndustryTypes();

		for (IndustryType i : allIndustryTypes) {
			if (i.getTypeId() == typeId) {
				return i;
			}
		}
		return null;

	}

	public void addCustomer(CustomerBean customerBean) {
		Customer customer = convertManagedBeantoEntity(customerBean);
		managedBeanRepository.addCustomer(customer);

		// Following method is to update customers list to see in manage contacts after
		// adding customer;
		updateCustomersFromDB();

		FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
				new FacesMessage("New Customer added succesfully"));
	}

	public void editCustomer(Customer c) {
		managedBeanRepository.editCustomer(c);

		updateCustomersFromDB();

		FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
				new FacesMessage("Changes saved succesfully"));
	}

	public void deleteCustomer(Customer customer) {

		managedBeanRepository.deleteCustomer(customer);

		updateCustomersFromDB();

		FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
				new FacesMessage("Customer deleted succesfully"));

	}

}
