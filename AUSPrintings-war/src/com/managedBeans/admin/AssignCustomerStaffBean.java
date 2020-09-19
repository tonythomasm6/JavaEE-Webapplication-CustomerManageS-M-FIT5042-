package com.managedBeans.admin;

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
import com.managedBeans.common.ManagedBeanRepository;

@Named
@RequestScoped
public class AssignCustomerStaffBean implements Serializable{

	
	private List<Customer> allCustomers;
	private Agent currentStaff;
	private List<Agent> staffs;
	private int selectedCustomerId;
	private int selectedStaffId;
	
	
	
	public int getSelectedStaffId() {
		return selectedStaffId;
	}
	public void setSelectedStaffId(int selectedStaffId) {
		this.selectedStaffId = selectedStaffId;
	}
	public int getSelectedCustomerId() {
		return selectedCustomerId;
	}
	public void setSelectedCustomerId(int selectedCustomerId) {
		this.selectedCustomerId = selectedCustomerId;
	}

	
	public List<Customer> getAllCustomers() {
		return allCustomers;
	}
	public void setAllCustomers(List<Customer> allCustomers) {
		this.allCustomers = allCustomers;
	}
	public Agent getCurrentStaff() {
		return currentStaff;
	}
	public void setCurrentStaff(Agent currentStaff) {
		this.currentStaff = currentStaff;
	}
	public List<Agent> getStaffs() {
		return staffs;
	}
	public void setStaffs(List<Agent> staffs) {
		this.staffs = staffs;
	}

	

	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;
	public AssignCustomerStaffBean() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "managedBeanRepository");
		
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		Agent loggedAgent = new Agent();
		try {
			loggedAgent = (Agent) sessionMap.get("loggedAgent");
		} catch (Exception e) {

		}
		
		
		allCustomers = managedBeanRepository.getCustomers(loggedAgent.getAgentId(), loggedAgent.getRole());
		staffs = managedBeanRepository.getAllStaff();
	}
	
	
	
	public void getStaffFromCustomer(int customerId) {

		for(Customer c: allCustomers) {
			if(c.getCustomerId() == customerId) {
				currentStaff = c.getAgent();
			}
		}
	}
	
	public void saveAllocation() {
		/*
		 * private int selectedCustomerId;
		  private int selectedStaffId;
		 */
		Customer selectedCustomer = new Customer();
		for(Customer c : allCustomers) {
			if(selectedCustomerId == c.getCustomerId()) {
				selectedCustomer = c;
			}
			
		}
		Agent selectedAgent = new Agent();
		for(Agent a : staffs) {
			if(a.getAgentId() == selectedStaffId) {
				selectedAgent = a;
			}
		}
		
		selectedCustomer.setAgent(selectedAgent);
		
		managedBeanRepository.updateCustomerStaffAllocation(selectedCustomer);
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Allocation saved successfully"));
		
	}
	
	
}
