package com.repository;

import com.entities.Agent;
import com.entities.Customer;

public interface AdminRepository {

	public void addStaff(Agent staff);
	
	public Agent getAgentFromId(int agentId);

	public void editAgent(Agent agent);
	
	public void deleteAgent(Agent agent);
	
	public void updateCustomerStaffAllocation(Customer c);
}
