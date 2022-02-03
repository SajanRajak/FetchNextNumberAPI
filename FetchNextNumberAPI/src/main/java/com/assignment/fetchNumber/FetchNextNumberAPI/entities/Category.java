package com.assignment.fetchNumber.FetchNextNumberAPI.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

//Entity class to make the category table

@Entity
public class Category {
	
	@Id
	private long id;
	private String name;
	
	public Category() {
		super();
	}

	public Category(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
