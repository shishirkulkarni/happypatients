package edu.sjsu.cs249.happypatients.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HTTPResponse {
	private String message;
	
	public HTTPResponse() {}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HTTPResponse(String Message) {
		this.message = message;
	}
}
