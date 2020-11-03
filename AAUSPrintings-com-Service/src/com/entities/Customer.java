package com.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@NamedQueries({
    @NamedQuery(name = Customer.GET_ALL_QUERY_NAME, query = "SELECT c FROM Customer c order by c.customerId desc")})

public class Customer implements Serializable{
	
	public static final String GET_ALL_QUERY_NAME = "Customer.getAll";

	private int customerId;
	
	@NotNull(message = "First name cannot be null")
	private String firstName;
	@NotNull(message = "Last name cannot be null")
	private String lastName;
	@NotNull(message = "Phone number cannot be null")
	@Pattern(regexp = "^[0-9]*$" , message="Mobile number entered is invalid")
	private String phoneNo;
	@NotNull(message = "Email cannot be null")
	@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "This is not a valid email")
	private String email;
	@NotNull(message = "Address cannot be null")
	private Address address;
	@NotNull(message = "Industrytype cannot be null")
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
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	public Set<CustomerContact> getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(Set<CustomerContact> customerContact) {
		this.customerContact = customerContact;
	}
	
	
}
