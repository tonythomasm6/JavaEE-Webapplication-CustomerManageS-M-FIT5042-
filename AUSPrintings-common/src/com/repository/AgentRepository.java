package com.repository;

import com.entities.Agent;

public interface AgentRepository {

	public Agent getLoggedAgentDetails(String loggedUserName);
}
