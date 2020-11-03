package com.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
		/*CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = builder.createQuery(Customer.class);
		Root<Customer> c = criteriaQuery.from(Customer.class);
		criteriaQuery.select(c).where(builder.equal(c.get("firstName"), "Vincent"));
		Query query = em.createQuery(criteriaQuery).getResultList();
		List<Customer> customers = query.getResultList();*/
		
		//Criteria query implementation
		
		
		/*
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = builder.createQuery(Customer.class);
		Root<Customer> c = criteriaQuery.from(Customer.class);
		
		if(role.equalsIgnoreCase("admin")) {	
			criteriaQuery.select(c);
		}else {
			criteriaQuery.select(c).where(builder.equal(c.get("agentId.agentId"), agentId));
		}
		Query query = entityManager.createQuery(criteriaQuery);
		List<Customer> customers = query.getResultList();
		return customers;*/
		
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
	
	public Customer getCustomer( int customerId) {
		//Customer customer = new Customer();
		//customer = (Customer) entityManager.createQuery("SELECT c FROM Customer c where c.customerId =:cusId").setParameter("cusId", customerId).getSingleResult();
		//Customer customer = entityManager.find(Customer.class, customerId);
		//return customer;
		
		
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery = builder.createQuery(Customer.class);
		Root<Customer> c = criteriaQuery.from(Customer.class);
		criteriaQuery.select(c).where(builder.equal(c.get("customerId"), customerId));
		Query query = entityManager.createQuery(criteriaQuery);
		Object customers = query.getSingleResult();
		customers = (Customer)customers;
		return (Customer) customers;
		
		
		
		
		
		
		
	}

	@Override
	public void editCustomer(Customer c) {
		entityManager.merge(c);
		
	}

	@Override
	public void deleteCustomer(Customer customer) {

		Customer c = entityManager.merge(customer);
		entityManager.remove(c);
		
		
		
		
	}
	

}
