package edu.sjsu.cs249.happypatients.models;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sjsu.cs249.happypatients.adapters.*;


@XmlRootElement
public class Patient {
	@XmlJavaTypeAdapter(UUIDAdapter.class)
	private UUID uuid;
	private long phone;
	private String name, email, address, treatment, diagnosis;
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dob;
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date diagnosisDate;

	public String getTreatment() {
		return treatment.toUpperCase();
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment.toUpperCase();
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Date getDiagnosisDate() {
		return diagnosisDate;
	}

	public void setDiagnosisDate(Date diagnosisDate) {
		this.diagnosisDate = diagnosisDate;
	}
		
	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	public Patient() {}
	
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toJSON() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
	
	public static Patient fromJSON(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, Patient.class);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public boolean equals(Object obj) {
		Patient p = (Patient)obj;
		if(name == p.getName() &&
				email == p.getEmail() &&
				address == p.getAddress() &&
				dob.equals(p.getDob()) &&
				treatment == p.getDiagnosis() &&
				phone == p.getPhone() &&
				diagnosis == p.getDiagnosis()) {
			return true;
		}
		return false;
	}
	
	//checks if personal data of this patient and p is changed
	public boolean personalDataChanged(Patient p) {
		if(name.equals(p.getName()) &&
				email.equals(p.getEmail()) &&
				address.equals(p.getAddress()) &&
				dob.equals(p.getDob()) &&
				phone == p.getPhone())
			return false;
		return true;
	}
	
	public boolean treatmentCompleted(Patient p) {
		if(!getTreatment().equals("COMPLETED") && p.getTreatment().equals("COMPLETED")) {
			return true;
		}
		
		return false;
	}
	
	public boolean diagnosisCompleted(Patient p) {
		if(getTreatment().equals("ONGOING") && (!p.getTreatment().equals("ONGOING") || !p.getTreatment().equals("COMPLETED")))
			return true;
		return false;
	}
	
	@JsonIgnore
	public String getPersonalInfoToString() {
		return "\nID: " + getUuid() + 
				"\nName: " + getName() +
				"\nAddress: " + getAddress() +
				"\nPhone: " + getPhone() +
				"\nDob: " + getDob() +
				"\nEmail: " + getEmail();
	}
	
	@JsonIgnore
	public String getMedicalInfoToString() {
		return "\nID: " + getUuid() + 
				"\nName: " + getName() +
				"\nDiagnosis: " + getDiagnosis() +
				"\nTreatment" + getTreatment();
	}
}

