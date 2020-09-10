package com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = IndustryType.GET_ALL_QUERY_NAME, query = "SELECT i FROM IndustryType i order by i.typeId desc")})

public class IndustryType implements Serializable {

	public static final String GET_ALL_QUERY_NAME = "IndustryType.getAll";
	
	private int typeId;
	private String industryType;
	private String description;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
