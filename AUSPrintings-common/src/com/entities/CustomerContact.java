package com.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = CustomerContact.GET_ALL_QUERY_NAME, query = "SELECT c FROM CustomerContact c order by c.customerContactId desc")})
public class CustomerContact {

	public static final String GET_ALL_QUERY_NAME = "CustomerContact.getAll";
	
	private int customerContactId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String businessPhone;
	private String email;
	private Customer customer;
	
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getCustomerContactId() {
		return customerContactId;
	}
	public void setCustomerContactId(int customerContactId) {
		this.customerContactId = customerContactId;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToOne
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
