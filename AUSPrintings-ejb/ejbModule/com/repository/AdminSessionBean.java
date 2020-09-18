package com.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entities.Agent;
import com.entities.CustomerContact;

@Stateless
public class AdminSessionBean implements AdminRepository{

	@PersistenceContext(unitName="AUSPrintings-ejb")
    private EntityManager entityManager;

	@Override
	public void addStaff(Agent staff) {
		List<Agent> staffs =  entityManager.createNamedQuery(Agent.GET_ALL_QUERY_NAME).getResultList(); 
		if (staffs.size() > 0){
			 staff.setAgentId(staffs.get(0).getAgentId() + 1);   
		}
		else {
			 staff.setAgentId(1);
		}
       
        entityManager.persist(staff);
		
	}

	@Override
	public Agent getAgentFromId(int agentId) {
		Agent agent = entityManager.find(Agent.class, agentId);
		return agent;
	}

	@Override
	public void editAgent(Agent agent) {
		entityManager.merge(agent);
		
	}

	@Override
	public void deleteAgent(Agent agent) {
		Agent a = entityManager.merge(agent);
		entityManager.remove(a);
		
	}
	
	
	
	
}
