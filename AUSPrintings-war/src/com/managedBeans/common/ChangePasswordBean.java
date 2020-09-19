package com.managedBeans.common;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Map;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Agent;

@Named
@RequestScoped
public class ChangePasswordBean implements Serializable {

	private Agent loggedAgent;

	private String currentPass;

	private String newPass;

	private String reNewPass;
	
	private String closeUrl;

	public String getCloseUrl() {
		return closeUrl;
	}

	public void setCloseUrl(String closeUrl) {
		this.closeUrl = closeUrl;
	}

	public Agent getLoggedAgent() {
		return loggedAgent;
	}

	public void setLoggedAgent(Agent loggedAgent) {
		this.loggedAgent = loggedAgent;
	}

	public String getCurrentPass() {
		return currentPass;
	}

	public void setCurrentPass(String currentPass) {
		this.currentPass = currentPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getReNewPass() {
		return reNewPass;
	}

	public void setReNewPass(String reNewPass) {
		this.reNewPass = reNewPass;
	}

	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;

	public ChangePasswordBean() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "managedBeanRepository");

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		loggedAgent = (Agent) sessionMap.get("loggedAgent");
		
		if(loggedAgent.getRole().equalsIgnoreCase("admin")) {
			closeUrl = "/faces/admin/home.xhtml";
		}
		else {
			closeUrl = "/faces/staff/home.xhtml";
		}
	}

	public String changePassword() {

		// Verifying is entered password is correct
		String currDBPassHash = loggedAgent.getPassword();
		String currPassHash = passwordEncrypt(currentPass);

		if (!currDBPassHash.equalsIgnoreCase(currPassHash)) {
			FacesContext.getCurrentInstance().addMessage("changePassForm:currentPass",
					new FacesMessage("Incorrect current password !!"));
		}
		else
		if (!newPass.equals(reNewPass)) {
			FacesContext.getCurrentInstance().addMessage("changePassForm:reNewPass",
					new FacesMessage("	Passwords do not match !!"));
		}
		else 
		{
			loggedAgent.setPassword(passwordEncrypt(newPass));
			managedBeanRepository.editAgent(loggedAgent);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password changed successfully. Login Again to continue"));
			return "logout";
		}

		return "changePassword";
		
	}

	public String passwordEncrypt(String pass) {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pass.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
