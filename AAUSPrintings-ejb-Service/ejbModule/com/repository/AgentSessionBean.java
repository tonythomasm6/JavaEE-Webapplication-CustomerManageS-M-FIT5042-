package com.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entities.Agent;
import com.entities.Customer;
import com.entities.IndustryType;


@Stateless
public class AgentSessionBean implements AgentRepository{

	
	@PersistenceContext(unitName="AUSPrintings-ejb")
    private EntityManager entityManager;
	
	public Agent getLoggedAgentDetails(String loggedUserName) {
		
		Agent agent = (Agent) entityManager.createQuery("SELECT a FROM Agent a where a.userName = :loggedUserName").setParameter("loggedUserName", loggedUserName).getSingleResult();
		return agent;
	}

	@Override
	public List<IndustryType> getAllIndustryTypes() {
		List<IndustryType> allIndustryTypes = entityManager.createNamedQuery(IndustryType.GET_ALL_QUERY_NAME).getResultList();
		/*Only if refreshed, updated values will be fetched*/
		for(IndustryType i : allIndustryTypes) {
			entityManager.refresh(i);
		}
		
		return allIndustryTypes;
	}

	@Override
	public List<Agent> getAllStaff() {
		String rol = "staff";
		List<Agent> agent = entityManager.createQuery("SELECT a FROM Agent a where  a.role= :rol").setParameter("rol", rol).getResultList();
		return agent;
	}

}
