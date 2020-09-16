package com.repository;

import java.util.List;

import com.entities.CustomerContact;

public interface ContactsRepository {

	public List<CustomerContact> getContactsForCustomer(int customerId);
	
	public void addCustomerContact(CustomerContact c);
	
}
