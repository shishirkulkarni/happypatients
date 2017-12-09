package edu.sjsu.cs249.happypatients.connectors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

import edu.sjsu.cs249.happypatients.models.Patient;

public class CassandraConnector {
	private Cluster cluster;
	private Session session;
	private String node, keyspace;
	private int port;
	
	public CassandraConnector(String node, int port, String keyspace) {
		this.node = node;
		this.port = port;
		this.keyspace = keyspace;
	}
	
	private void createSession() {
		Builder builder = Cluster.builder().addContactPoint(this.node);
		builder.withPort(this.port);
		cluster = builder.build();
		session = cluster.connect(this.keyspace);
	}
	
	private Session getSession() {
		if(session == null) {
			createSession();
		}
		
		return session;
	}
	
	public Patient findOne(UUID id) {
		
		PreparedStatement statement = getSession().prepare("SELECT * FROM patients WHERE id = ?");
		ResultSet results = getSession().execute(statement.bind()
				.setUUID("id", id));
		Patient p = new Patient();
		Row r = results.one();
		
		if(r == null)
			return null;
		
		p.setUuid(r.getUUID("id"));
		p.setName(r.getString("name"));
		p.setEmail(r.getString("email"));
		p.setDob(new Date(r.getDate("dob").getMillisSinceEpoch()));
		p.setPhone(r.getLong("phone"));
		p.setAddress(r.getString("address"));
		p.setDiagnosisDate(new Date(r.getDate("diagnosis_date").getMillisSinceEpoch()));
		p.setDiagnosis(r.getString("diagnosis"));
		p.setTreatment(r.getString("treatment"));
		
		return p;
	}
	
	public List<Patient> findAll() {
		List<Patient> patients = new ArrayList<>();
		PreparedStatement statement = getSession().prepare("SELECT * FROM patients");
		
		ResultSet results = getSession().execute(statement.bind());
		for(Row r: results) {
			Patient p = new Patient();
			p.setUuid(r.getUUID("id"));
			p.setName(r.getString("name"));
			p.setEmail(r.getString("email"));
			p.setDob(new Date(r.getDate("dob").getMillisSinceEpoch()));
			p.setPhone(r.getLong("phone"));
			p.setAddress(r.getString("address"));
			p.setDiagnosisDate(new Date(r.getDate("diagnosis_date").getMillisSinceEpoch()));
			p.setDiagnosis(r.getString("diagnosis"));
			p.setTreatment(r.getString("treatment"));
			patients.add(p);
		}
		
		return patients;
	}
	
	public Patient update(Patient p) {
		
		Patient oldData = findOne(p.getUuid());
		
		PreparedStatement statement = getSession().prepare(
				"UPDATE patients SET "
				+ "name = ?, "
				+ "dob = ?, "
				+ "address = ?, "
				+ "phone = ?, "
				+ "email = ?, "
				+ "diagnosis = ?, "
				+ "diagnosis_date = ?, "
				+ "treatment = ? "
				+ "WHERE id = ?");
		
		getSession().execute(statement.bind()
				.setString("name", p.getName())
				.setString("email", p.getEmail())
				.setDate("dob", LocalDate.fromMillisSinceEpoch(p.getDob().getTime()))
				.setString("address", p.getAddress())
				.setLong("phone", p.getPhone())
				.setUUID("id", p.getUuid())
				.setDate("diagnosis_date", LocalDate.fromMillisSinceEpoch(p.getDiagnosisDate().getTime()))
				.setString("diagnosis", p.getDiagnosis())
				.setString("treatment", p.getTreatment()));
		
		return p;
		
	}
	
	public Patient insert(Patient p) {
		
		PreparedStatement statement =  getSession().prepare(
				"INSERT INTO patients (id, name, dob, address, phone, email, diagnosis_date, diagnosis, treatment)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		p.setUuid(UUID.randomUUID());
		
		getSession().execute(statement.bind()
				.setString("name", p.getName())
				.setString("email", p.getEmail())
				.setDate("dob", LocalDate.fromMillisSinceEpoch(p.getDob().getTime()))
				.setString("address", p.getAddress())
				.setLong("phone", p.getPhone())
				.setUUID("id", p.getUuid())
				.setDate("diagnosis_date", LocalDate.fromMillisSinceEpoch(p.getDiagnosisDate().getTime()))
				.setString("diagnosis", p.getDiagnosis())
				.setString("treatment", p.getTreatment()));
		
		return p;
		
	}
	
	public void delete(Patient p) {
		PreparedStatement statement = getSession().prepare(
				"DELETE FROM patients"
				+ " WHERE id = ?");
		
		getSession().execute(
				statement.bind()
				.setUUID("id", p.getUuid()));
	}
}
