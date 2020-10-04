package com.managedBeans.staff;

import java.io.Serializable;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Agent;
import com.managedBeans.common.ManagedBeanRepository;

@Named
@RequestScoped
public class StaffDetailsBean implements Serializable {

	private int agentId;
	private Agent agent;
	
	
	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;
	
	public StaffDetailsBean() {
		agentId = Integer.valueOf(FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("agentId"));

	ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
			.getELResolver().getValue(elContext, null, "managedBeanRepository");
	
	agent = getAgentFromId(agentId);
	}
	
	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Agent getAgentFromId(int agentId) {
		Agent a = managedBeanRepository.getAgentFromId(agentId);
		return a;
	}
	
}
