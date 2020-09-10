package com.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entities.Agent;
import com.entities.Customer;


@Stateless
public class CustomerSessionBean implements CustomerRepository{
	
	@PersistenceContext(unitName="AUSPrintings-ejb")
    private EntityManager entityManager;
	
	public List<Customer> getCustomers(int agentId, String role) {
		
		List customers = new ArrayList<Customer>();
		if(role.equalsIgnoreCase("admin")) {
			customers = entityManager.createNamedQuery(Customer.GET_ALL_QUERY_NAME).getResultList();
		}else {
			customers = entityManager.createQuery("SELECT a FROM Customer a where a.agent.agentId = :agentId").setParameter("agentId", agentId).getResultList();
		}
		return customers;
	}

	public void addCustomer(Customer customer) {
		List<Customer> customers =  entityManager.createNamedQuery(Customer.GET_ALL_QUERY_NAME).getResultList(); 
		if (customers.size() > 0){
			 customer.setCustomerId(customers.get(0).getCustomerId() + 1);
		}
		else {
			 customer.setCustomerId(1);
		}
       
        entityManager.persist(customer);
	}
	
	

}
