package com.managedBeans.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.entities.CustomerContact;
import com.entities.IndustryType;
import com.managedBeans.common.ManagedBeanRepository;

@Named
@SessionScoped
public class ManageCustomersBean implements Serializable {

	private List<Customer> customers;
	private List<Agent> staffs;
	private int selectedStaffId = 0;
	
	public int getSelectedStaffId() {
		return selectedStaffId;
	}

	public void setSelectedStaffId(int selectedStaffId) {
		this.selectedStaffId = selectedStaffId;
	}

	public List<Agent> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<Agent> staffs) {
		this.staffs = staffs;
	}


	private boolean isAdmin = false;
	
	private String isUserAdmin="false";

	public String getIsUserAdmin() {
		return isUserAdmin;
	}

	public void setIsUserAdmin(String isUserAdmin) {
		this.isUserAdmin = isUserAdmin;
	}

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
		
		//To get all Staffs;
		this.staffs = managedBeanRepository.getAllStaff();

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
			this.isUserAdmin = "true";
		}
		
	}
	
	public void updateCustomersFromDB(String agentId) {

		if(Integer.parseInt(agentId) != 0) {
		Agent staff = managedBeanRepository.getAgentFromId(Integer.parseInt(agentId));
		this.customers = managedBeanRepository.getCustomers(staff.getAgentId(), staff.getRole());
		if(staff.getRole().equalsIgnoreCase("admin")) {
			this.isAdmin = true;
			this.isUserAdmin = "true";
		}
		}else {
			updateCustomersFromDB();
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
		updateCustomersFromDB(String.valueOf(selectedStaffId));

		FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
				new FacesMessage("New Customer added succesfully"));
	}

	public void editCustomer(Customer c) {
		managedBeanRepository.editCustomer(c);

		updateCustomersFromDB(String.valueOf(selectedStaffId));

		FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
				new FacesMessage("Changes saved succesfully"));
	}

	public void deleteCustomer(Customer customer) {
		
		//To delete all connected contacts to the customer
		List<CustomerContact> contacts = new ArrayList<CustomerContact>();
		contacts = managedBeanRepository.getContactsForCustomer(customer.getCustomerId());
		
		Iterator iterator = contacts.iterator();
		while(iterator.hasNext()) {
			CustomerContact cc = (CustomerContact) iterator.next();
			managedBeanRepository.deleteContact(cc);
		}
		
		

		managedBeanRepository.deleteCustomer(customer);

		updateCustomersFromDB(String.valueOf(selectedStaffId));

		FacesContext.getCurrentInstance().addMessage("addForm:formMessage",
				new FacesMessage("Customer deleted succesfully"));

	}

}
