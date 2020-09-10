package com.managedBeans.customer;

import java.io.Serializable;
import java.util.Map;

import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.entities.Address;
import com.entities.Agent;
import com.entities.Customer;
import com.entities.IndustryType;
import com.managedBeans.common.ManagedBeanRepository;

@Named
@SessionScoped
public class CustomerApplication implements Serializable{
	
	@ManagedProperty(value = "#{managedBeanRepository}")
	private ManagedBeanRepository managedBeanRepository;
	
	public CustomerApplication() {
		 ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		 managedBeanRepository = (ManagedBeanRepository) FacesContext.getCurrentInstance().getApplication()
	                .getELResolver().getValue(elContext, null, "managedBeanRepository");
	}

	public void addCustomer(CustomerBean customerBean) {
		Customer customer = convertManagedBeantoEntity(customerBean);
		managedBeanRepository.addCustomer(customer);
	}
	
	
	public Customer convertManagedBeantoEntity(CustomerBean customerManagedBean) {
		Customer customer = new Customer(); // Entity
			customer.setFirstName(customerManagedBean.getFirstName());
			customer.setLastName(customerManagedBean.getLastName());
			customer.setPhoneNo(customerManagedBean.getPhoneNo());
			customer.setEmail(customerManagedBean.getEmail());
			
		Address address = new Address();
			address.setStreetNumber(customerManagedBean.getStreetNumber());
			address.setStreetAddress(customerManagedBean.getStreetAddress());
			address.setSuburb(customerManagedBean.getSuburb());
			address.setCity(customerManagedBean.getCity());
			address.setPostcode(customerManagedBean.getPostcode());
			address.setState(customerManagedBean.getState());;
		customer.setAddress(address);
		
		//Getting agentID details
		//Getting logged in agent details from session
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		Agent agent = new Agent();
		agent = (Agent) sessionMap.get("loggedAgent");
		customer.setAgent(agent);
		/*IndustryType industry = new IndustryType();
			industry.setTypeId(customerManagedBean.getTypeId());
			industry.setIndustryType(customerManagedBean.getIndustryType());
			industry.setDescription(customerManagedBean.getDescription());
			
		customer.setIndustryType(industry);*/
		
		return customer;
	}
	
}
