package com.managedBeans;

import java.io.Serializable;
import java.security.Principal;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class HeaderBean implements Serializable{

	private String loginUserName;

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	
	public HeaderBean() {
		 Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		 if(principal != null) {
			 loginUserName = principal.getName();
		 }
	}
}
