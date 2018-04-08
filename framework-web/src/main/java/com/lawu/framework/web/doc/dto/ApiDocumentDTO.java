package com.lawu.framework.web.doc.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiDocumentDTO {
	
	private String name;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	private String apiName;
	
	private String path;
	
	private String httpMethod;
	
	private String notes;
	
	private String reviewer;
	
	private Boolean isDeprecated;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public Boolean getIsDeprecated() {
		return isDeprecated;
	}

	public void setIsDeprecated(Boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}
	
}
