package com.managedBeans.common;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named
@RequestScoped
public class LogoutBean implements Serializable{
	

	private String logoutMsg = "You have successfully Loggedout !!!!! " ;
	
	public String getLogoutMsg() {
		return logoutMsg;
	}

	public void setLogoutMsg(String logoutMsg) {
		this.logoutMsg = logoutMsg;
	}

	public LogoutBean() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		 externalContext.invalidateSession();
		
		 
	}
	
	public String logoutNow() {
		
		return "faces/logout.xhtml";
	}
	
	
}
