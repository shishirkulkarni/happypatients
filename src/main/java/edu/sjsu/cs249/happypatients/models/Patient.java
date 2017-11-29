package edu.sjsu.cs249.happypatients.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Patient {
	private int id;
	private String name;
	
	public Patient() {}
	
	public Patient(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
