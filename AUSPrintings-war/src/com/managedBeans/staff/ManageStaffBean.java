package com.managedBeans.staff;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.List;

import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Agent;
import com.entities.Customer;
import com.managedBeans.common.ManagedBeanRepository;

@Named
@SessionScoped
public class ManageStaffBean implements Serializable{
	
	
	private List<Agent> staffs ;

	public List<Agent> getStaffs() {
		return staffs;
	}


	public void setStaffs(List<Agent> staffs) {
		this.staffs = staffs;
	}


	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;
	
	public ManageStaffBean() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(elContext, null, "managedBeanRepository");
		
		updateStaffFromDB();
	}
	
	
	public void updateStaffFromDB() {
		staffs= managedBeanRepository.getAllStaff();
	}
	
	

	public void addStaff(StaffBean staff) {
		Agent agent = convertManagedBeanToEntity(staff);
		agent.setRole("staff");
		managedBeanRepository.addStaff(agent);
		//To update the staffs after addition
		updateStaffFromDB();
		
	}
	
	
	public Agent convertManagedBeanToEntity(StaffBean staff) {
		
		Agent agent = new Agent();
		agent.setUserName(staff.getUserName());
		agent.setFirstName(staff.getFirstName());
		agent.setLastName(staff.getLastName());
		agent.setEmail(staff.getEmail());
		//password validation
		String passHash = staff.getPassword();
		if(!staff.getPassword().equals(staff.getRePassword())){
			FacesContext.getCurrentInstance().addMessage("addForm:rePassword", new FacesMessage("Passwords should match !!"));
		}else {
			passHash = passwordEncrypt(staff.getPassword());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("New Staff added succesfully"));
		}
		agent.setPassword(passHash);
		//To encrypt input password
		
		return agent;
		
	}
	
	
	public String passwordEncrypt(String pass) {
		
		try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(pass.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	
	public void editStaff(Agent agent) {
		managedBeanRepository.editAgent(agent);
		//To update the staffs after edit
		updateStaffFromDB();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Staff Details Updated Successfully succesfully"));
	}
	
	public void deleteAgent(Agent agent) {
		managedBeanRepository.deleteAgent(agent);
		//To update the staffs after deletion
		updateStaffFromDB();
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Staff deleted successfully"));
	}
	
}
