package com.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = Customer.GET_ALL_QUERY_NAME, query = "SELECT c FROM Customer c order by c.customerId desc")})

public class Customer implements Serializable{
	
	public static final String GET_ALL_QUERY_NAME = "Customer.getAll";

	private int customerId;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String email;
	private Address address;
	private IndustryType industryType;
	private Set<CustomerContact> customerContact;
	private Agent agent;
	
	@OneToOne
	 public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	
	
	@Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
	
	@Embedded
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	@OneToOne
	public IndustryType getIndustryType() {
		return industryType;
	}
	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}
	
	@OneToMany
	public Set<CustomerContact> getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(Set<CustomerContact> customerContact) {
		this.customerContact = customerContact;
	}
	
	
}
