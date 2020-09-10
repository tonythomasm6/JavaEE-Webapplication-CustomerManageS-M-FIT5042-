package com.managedBeans.customer;

import java.io.Serializable;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

import com.entities.*;
import com.managedBeans.common.ManagedBeanRepository;

@RequestScoped
@Named
public class CustomerBean implements Serializable{
	
	private int customerId;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String email;
	private Address address;
	
	//Parameter from IndustryType -- Tony
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
	
	//Address fields
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
	

