package edu.sjsu.cs249.happypatients.messengers;

import javax.jms.Destination;
import javax.jms.JMSException;import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import edu.sjsu.cs249.happypatients.connectors.ActiveMQConnector;

public class Producer {
	private Session session;
	private ActiveMQConnector conn;
	private MessageProducer producer;
	private String topic;
	
	public Producer(ActiveMQConnector conn, String topic) {
		this.conn = conn;
		this.topic = topic;
	}
	
	// Creates a producer and a topic
	public void initialize() throws JMSException {
		session = this.conn.getSession();
		Destination d = session.createTopic(this.topic);
		producer = session.createProducer(d);
	}
	
	public void sendMessage(String message) throws JMSException {
		producer.send(session.createTextMessage(message));
	};

}
