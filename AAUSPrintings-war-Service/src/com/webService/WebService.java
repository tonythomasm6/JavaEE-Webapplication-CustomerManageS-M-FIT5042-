package com.webService;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.simple.*;

import com.entities.Agent;
import com.entities.Customer;
import com.entities.IndustryType;
import com.managedBeans.common.ManagedBeanRepository;
import com.repository.AgentRepository;
import com.repository.CustomerRepository;

@Path("generic")

public class WebService {
    @SuppressWarnings("unused")
    @Context
    private UriInfo context;
    
    @EJB
	AgentRepository agentRepository;
    @EJB
	CustomerRepository customerRepository;

    /**
     * Default constructor. 
     */
    public WebService() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Retrieves representation of an instance of WebService
     * @return an instance of String
     */
    @GET
    @Produces("application/json")
    @Path("industryTypes")
    public JSONObject getJson() {
    	

    	List<IndustryType> allIndustryTypes =  agentRepository.getAllIndustryTypes();
    	
    	JSONObject obj = new JSONObject();
    	obj.put("allIndustryTypes", allIndustryTypes);
    	return obj;
    }
    
    @GET
    @Produces("application/json")
    @Path("allCustomers")
        public JSONObject getCustomers() {
    	List<Customer> customers = customerRepository.getCustomers(1, "admin");

    	JSONObject result = new JSONObject();
    	List<JSONObject> list = new ArrayList<JSONObject>();
    	
    	for(Customer c : customers) {
    		JSONObject obj = new JSONObject();
    		obj.put("custId", c.getCustomerId());
    		obj.put("name", c.getFirstName()+ " " + c.getLastName());
    		obj.put("email", c.getEmail());
    		obj.put("agent", (c.getAgent()).getFirstName() + " " + (c.getAgent()).getLastName() );
    		list.add(obj);
    	}
    	
    	result.put("result",list);
    	return result;
    }
    
    @GET
    @Produces("application/json")
    @Path("getStaffs")
        public JSONObject getStaffs() {
    	
    	List<Agent> agents =agentRepository.getAllStaff();

    	JSONObject result = new JSONObject();
    	List<JSONObject> list = new ArrayList<JSONObject>();
    	
    	for(Agent staff : agents) {
    		JSONObject obj = new JSONObject();
    		obj.put("agentId", staff.getAgentId());
    		obj.put("name", staff.getFirstName() + " " + staff.getLastName());
    		obj.put("email", staff.getEmail());
    		obj.put("username", staff.getUserName());
    		list.add(obj);
    	}
    	
    	result.put("result",list);
    	return result;
    }
    
    
    
    /**
     * PUT method for updating or creating an instance of WebService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

}