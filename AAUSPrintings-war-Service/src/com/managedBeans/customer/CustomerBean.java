package com.managedBeans.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.*;
import com.managedBeans.common.ManagedBeanRepository;

@RequestScoped
@Named
public class CustomerBean implements Serializable {

	private List<IndustryType> allIndustryTypes;
	
	private int selectedStaffId;
	public int getSelectedStaffId() {
		return selectedStaffId;
	}

	public void setSelectedStaffId(int selectedStaffId) {
		this.selectedStaffId = selectedStaffId;
	}



	private List<Agent> allStaff = new ArrayList<Agent>();
	private String isAdmin = "false";

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<Agent> getAllStaff() {
		return allStaff;
	}

	public void setAllStaff(List<Agent> allStaff) {
		this.allStaff = allStaff;
	}
	
	

	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;

	// In constructor the repository method is called to fetch all industry types
	public CustomerBean() {

		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "managedBeanRepository");
		allIndustryTypes = managedBeanRepository.getAllIndustryTypes();
		
		//To get all staff for admin
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		Agent agent = (Agent) sessionMap.get("loggedAgent");
		if(agent.getRole().equalsIgnoreCase("admin")) {
			allStaff = managedBeanRepository.getAllStaff();
			if(allStaff.size() > 0) {
				isAdmin = "true";
			}
		}
		
	}
	
	
	

	public List<IndustryType> getAllIndustryTypes() {
		return allIndustryTypes;
	}

	public void setAllIndustryTypes(List<IndustryType> allIndustryTypes) {
		this.allIndustryTypes = allIndustryTypes;
	}

	private int customerId;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String email;
	private Address address;

	// Parameter from IndustryType -- Tony
	private IndustryType industry;

	public IndustryType getIndustry() {
		return industry;
	}

	public void setIndustry(IndustryType industry) {
		this.industry = industry;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private int typeId;
	private String industryType;
	private String description;

	private Agent agentId;

	// Address fields
	private String streetNumber;
	private String streetAddress;
	private String suburb;
	private String city;
	private String postcode;
	private String state;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public Agent getAgentId() {
		return agentId;
	}

	public void setAgentId(Agent agentId) {
		this.agentId = agentId;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
