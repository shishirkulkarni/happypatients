package edu.sjsu.cs249.happypatients.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

import javax.jms.JMSException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import edu.sjsu.cs249.happypatients.config.Config;
import edu.sjsu.cs249.happypatients.connectors.ActiveMQConnector;
import edu.sjsu.cs249.happypatients.connectors.CassandraConnector;
import edu.sjsu.cs249.happypatients.connectors.RedisConnector;
import edu.sjsu.cs249.happypatients.messengers.Consumer;
import edu.sjsu.cs249.happypatients.messengers.Producer;
import edu.sjsu.cs249.happypatients.models.Patient;

public class PatientService {
	private static RedisConnector redisConnector;
	private static CassandraConnector cassandraConnector;
	private static PolicyServer policyServer;
	private static ActiveMQConnector mqConnector;
	private static Producer producer;
	private static Consumer emailConsumer, analyticsConsumer;
	
	
	public PatientService() {
		try {
			Config config = new ObjectMapper(new YAMLFactory())
					.readValue(new File(getClass().getClassLoader().getResource("config.yml").getFile()), Config.class);
			redisConnector = new RedisConnector(config.getRedisHost(), config.getRedisPort());
			cassandraConnector = new CassandraConnector(config.getCassandraHost(), config.getCassandraPort(), config.getCassandraKeyspace());
			policyServer = new PolicyServer(config.getPolicyServerHost(), config.getPolicyServerPort(), config.getPolicyServerProtocol(), config.getPolicyServerPath());
			mqConnector = new ActiveMQConnector(config.getActiveMQProtocol(), config.getActiveMQHost(), config.getActiveMQPort());
			producer = new Producer(mqConnector, config.getActiveMQTopic());
			producer.initialize();
			emailConsumer = new Consumer(mqConnector, config.getActiveMQTopic());
			emailConsumer.initialize();
			emailConsumer.startListening();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JMSException e) {
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
		testAndCache(p);
		return p;
	}
	
	public Patient putPatient(Patient p) throws IOException, JMSException {
		Patient old = cassandraConnector.findOne(p.getUuid());
		
		cassandraConnector.update(p);
		testAndCache(p);
		
		if(old.personalDataChanged(p)) {
			producer.sendMessage("Personal Info Changed \n " + p.getPersonalInfoToString());
		}
		
		if(old.treatmentCompleted(p)) {
			producer.sendMessage("Treatment Completed \n " + p.getMedicalInfoToString());
		} else if(old.diagnosisCompleted(p)) {
			producer.sendMessage("Diagnosis Completed \n" + p.getMedicalInfoToString());
		}
		
		return p;
	}
	
	public Patient postPatient(Patient p) throws IOException {
		cassandraConnector.insert(p);
		testAndCache(p);
		return p;
	}
	
	public void deletePatient(Patient p) throws JMSException {
		cassandraConnector.delete(p);
		redisConnector.del(p.getUuid().toString());
		producer.sendMessage("User Info Deleted\n " +
					"Id: " + p.getUuid());
	}
	
	public Collection<Patient> getPatients() {
		//TODO: decide if you want to cache the patients
		return cassandraConnector.findAll();
	}
	
	private void testAndCache(Patient p) throws IOException {
		Calendar c = Calendar.getInstance();
		c.setTime(p.getDiagnosisDate());
		
		if(c.get(Calendar.YEAR) < 2000) {
			redisConnector.del(p.getUuid().toString());
			return;
		}
		
		String policy = policyServer.getPolicy();
		
		if(policy == null || policy.equals("")) {

			return;
		}
				
		switch (p.getTreatment()) {
		case "URGENTCARE":
		case "ICU":
			if(policy.equals(policy))
				redisConnector.set(p.getUuid().toString(), p.toJSON());
			break;
		case "ONGOING":
		default:
			redisConnector.del(p.getUuid().toString());
		}
	}
	
	public void sendMessage(String message) {
		try {
			producer.sendMessage(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
