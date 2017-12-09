package edu.sjsu.cs249.happypatients.connectors;

import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;

public class ActiveMQConnector {
	private String address;
	private Connection conn;
	private Session session;
	
	// Constructor is heavy since we are creating static instances of these connectors
	public ActiveMQConnector(String protocol, String host, int port) {
		StringBuilder builder = new StringBuilder();
		builder.append(protocol + "://");
		builder.append(host);
		builder.append(":" + port);
		this.address = builder.toString();
	}
	
	private Connection getConnection() throws JMSException {
		if(conn == null) {
			conn = new ActiveMQConnectionFactory(this.address).createConnection();
			conn.start();
		}
		return conn;
	}
	
	public Session getSession() throws JMSException {
		if(session == null)
			session = getConnection().createSession(false, Session.CLIENT_ACKNOWLEDGE);
		return session;
	}
	
	public void closeSession() throws JMSException { //Avoiding leakage of resources
		if(session != null) {
			if(conn != null)
				conn.close();
			session.close();
			session = null;
		}
	}
	
//	private Connection startConnection() throws JMSException {
//		if(conn == null) {
//			conn = new ActiveMQConnectionFactory(this.address)
//					.createConnection();
//		}
//		conn.start();
//		return conn;
//	}
}
