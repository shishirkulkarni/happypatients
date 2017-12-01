package edu.sjsu.cs249.happypatients.connectors;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;

public class CassandraConnector {
	private Cluster cluster;
	private Session session;
	private String node;
	private int port;
	
	public CassandraConnector(String node, int port) {
		this.node = node;
		this.port = port;
	}
	
	private void createSession() {
		Builder builder = Cluster.builder().addContactPoint(this.node);
		builder.withPort(this.port);
		cluster = builder.build();
		session = cluster.connect();
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
}
