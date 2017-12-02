package edu.sjsu.cs249.happypatients.connectors;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

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
	
	public String executeQuery() {
		if(session == null)
			createSession();
		
		return session.execute("select release_version from system.local").one().getString("release_version");
	}
	
	public void insert(Patient p) {
		if(session == null)
			createSession();

		PreparedStatement statement =  session.prepare(
				"INSERT INTO patient (id, name, dob, address, phone, email)"
				+ " VALUES(now(), ?, ?, ?, ?, ?)");
		
		session.execute(statement.bind()
				.setString("name", p.getName())
				.setString("email", p.getEmail())
				.setString("dob", p.getDob())
				.setString("address", p.getAddress())
				.setInt("phone", p.getPhone()));
	}
}
