package edu.sjsu.cs249.happypatients.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.sjsu.cs249.happypatients.connectors.CassandraConnector;
import edu.sjsu.cs249.happypatients.models.Patient;

public class PatientService {
	private Map<Integer, Patient> patients = new HashMap<>();// dummy data for now
	private CassandraConnector connector = new CassandraConnector("localhost", 9042, "happypatients");
	
	public PatientService() {
		patients.put(1, new Patient());
		patients.put(2, new Patient());
		patients.put(3, new Patient());
	}
	
	public Patient getPatient(int id) {
		return patients.get(id);
	}
	
	public Patient putPatient(Patient p) {
		if (patients.get(p.getId()) == null)
			return null;
		patients.put(p.getId(), p);
		return p;
		
	}
	
	public boolean createPatient(Patient p) {
		connector.insert(p);
		return true;
	}
	
	public Collection<Patient> getPatients() {
		return patients.values();
	}
}
