package com.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entities.Customer;
import com.entities.CustomerContact;

@Stateless
public class ContactsSessionbean implements ContactsRepository{

	@PersistenceContext(unitName="AUSPrintings-ejb")
    private EntityManager entityManager;
	
	@Override
	public List<CustomerContact> getContactsForCustomer(int customerId) {
		
		List<CustomerContact> contacts = entityManager.createQuery("SELECT a FROM CustomerContact a where a.customer.customerId = :custId").setParameter("custId", customerId).getResultList();
		return contacts;
	}

	@Override
	public void addCustomerContact(CustomerContact contact) {
		List<CustomerContact> contacts =  entityManager.createNamedQuery(CustomerContact.GET_ALL_QUERY_NAME).getResultList(); 
		if (contacts.size() > 0){
			 contact.setCustomerContactId(contacts.get(0).getCustomerContactId() + 1);
		}
		else {
			 contact.setCustomerContactId(1);
		}
       
        entityManager.persist(contact);
		
	}

}
