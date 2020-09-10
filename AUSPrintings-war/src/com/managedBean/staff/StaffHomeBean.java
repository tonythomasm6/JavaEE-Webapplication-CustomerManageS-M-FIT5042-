package com.managedBean.staff;

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
import com.managedBeans.common.ManagedBeanRepository;


@Named
@SessionScoped
public class StaffHomeBean implements Serializable{

	private String loggedUserName;
	private String headerURL="../staff/header.xhtml";
	
	public String getHeaderURL() {
		return headerURL;
	}

	public void setHeaderURL(String headerURL) {
		this.headerURL = headerURL;
	}

	public String getLoggedUserName() {
		return loggedUserName;
	}

	public void setLoggedUserName(String loggedUserName) {
		this.loggedUserName = loggedUserName;
	}

	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;
	
	public StaffHomeBean() {
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		 if(principal != null) {
			 loggedUserName = principal.getName();
		 }
		 
		 ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		 managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
	                .getELResolver().getValue(elContext, null, "managedBeanRepository");
	        
	        
		 getLoggedAgentDetails(loggedUserName);
		 
		 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		 Map<String, Object> sessionMap = externalContext.getSessionMap();
		 sessionMap.put("loggedUserName", loggedUserName);
		 
	}
	
	public void getLoggedAgentDetails(String loggedUserName) {
		Agent agent = managedBeanRepository.getLoggedAgentDetails(loggedUserName);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("loggedUserName", loggedUserName);
		sessionMap.put("loggedAgent", agent);
		
		
	}
}
