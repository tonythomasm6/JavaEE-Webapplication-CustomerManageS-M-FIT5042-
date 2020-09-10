package com.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entities.Agent;


@Stateless
public class AgentSessionBean implements AgentRepository{

	
	@PersistenceContext(unitName="AUSPrintings-ejb")
    private EntityManager entityManager;
	
	public Agent getLoggedAgentDetails(String loggedUserName) {
		
		Agent agent = (Agent) entityManager.createQuery("SELECT a FROM Agent a where a.userName = :loggedUserName").setParameter("loggedUserName", loggedUserName).getSingleResult();
		return agent;
	}

}
