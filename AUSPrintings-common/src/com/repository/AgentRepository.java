package com.repository;

import java.util.List;

import com.entities.Agent;
import com.entities.IndustryType;

public interface AgentRepository {

	public Agent getLoggedAgentDetails(String loggedUserName);
	
	public List<IndustryType> getAllIndustryTypes();
	
	public List<Agent> getAllStaff();
	
}
