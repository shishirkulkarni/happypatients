package edu.sjsu.cs249.happypatients.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import edu.sjsu.cs249.happypatients.config.Config;
import edu.sjsu.cs249.happypatients.connectors.CassandraConnector;
import edu.sjsu.cs249.happypatients.connectors.RedisConnector;
import edu.sjsu.cs249.happypatients.models.Patient;

public class PatientService {
	private static RedisConnector redisConnector;
	private static CassandraConnector cassandraConnector;
	//A policy server instance would come here
	
	public PatientService() {
		try {
			Config config = new ObjectMapper(new YAMLFactory())
					.readValue(new File(getClass().getClassLoader().getResource("config.yml").getFile()), Config.class);
			redisConnector = new RedisConnector(config.getRedisHost(), config.getRedisPort());
			cassandraConnector = new CassandraConnector(config.getCassandraHost(), config.getCassandraPort(), config.getCassandraKeyspace());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Patient getPatient(UUID id) throws IOException {
		String patient = redisConnector.get(id.toString());
		if(patient != null) {
			return Patient.fromJSON(patient);
		}
		
		
		Patient p = cassandraConnector.findOne(id);
		
		if(p == null)
			return p;
		//TODO: Make a request to policy server and cache the results based on policy
		redisConnector.set(p.getUuid().toString(), p.toJSON());
		return p;
	}
	
	public Patient putPatient(Patient p) throws JsonProcessingException {
		cassandraConnector.update(p);
		redisConnector.set(p.getUuid().toString(), p.toJSON());
		return p;
	}
	
	public Patient postPatient(Patient p) throws IOException {
		cassandraConnector.insert(p);
		redisConnector.set(p.getUuid().toString(), p.toJSON());
		return p;
	}
	
	public void deletePatient(Patient p) {
		cassandraConnector.delete(p);
		redisConnector.del(p.getUuid().toString());
	}
	
	public Collection<Patient> getPatients() {
		return new ArrayList<>();
	}
}
