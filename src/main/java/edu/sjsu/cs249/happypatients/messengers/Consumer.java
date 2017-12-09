package edu.sjsu.cs249.happypatients.messengers;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import edu.sjsu.cs249.happypatients.connectors.ActiveMQConnector;

public class Consumer implements MessageListener {
	private Session session;
	private ActiveMQConnector conn;
	private MessageConsumer consumer;
	private String topic;
	
	public Consumer(ActiveMQConnector conn, String topic) {
		this.conn = conn;
		this.topic = topic;
	}
	
	public void initialize() throws JMSException {
		session = this.conn.getSession();
		Destination d = session.createTopic(this.topic);
		consumer = session.createConsumer(d);
	}
	
	public void startListening() throws JMSException {
		consumer.setMessageListener(this);
	}
	

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println(((TextMessage) message).getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Stops Listening as well
	private void close() throws JMSException {
		consumer.close();
	}
	
}
