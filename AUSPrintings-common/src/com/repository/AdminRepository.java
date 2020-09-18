package com.repository;

import com.entities.Agent;

public interface AdminRepository {

	public void addStaff(Agent staff);
	
	public Agent getAgentFromId(int agentId);

	public void editAgent(Agent agent);
	
	public void deleteAgent(Agent agent);
}
