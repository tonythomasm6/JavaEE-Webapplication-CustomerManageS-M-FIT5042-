package com.managedBeans.common;

import java.io.Serializable;
import java.security.Principal;
import java.util.Map;

import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Agent;

@Named
@SessionScoped
public class HeaderBean implements Serializable{

	private String loggedUserName;

	
	
	public String getLoggedUserName() {
		return loggedUserName;
	}



	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}
	
	
	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;


	public HeaderBean() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		 managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
	                .getELResolver().getValue(elContext, null, "managedBeanRepository");
		 Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		 if(principal != null) {
			 loggedUserName = principal.getName();
		 }
		 Agent agent = managedBeanRepository.getLoggedAgentDetails(loggedUserName);
		
		loggedUserName = agent.getFirstName() + " " + agent.getLastName();
		
	}
}
