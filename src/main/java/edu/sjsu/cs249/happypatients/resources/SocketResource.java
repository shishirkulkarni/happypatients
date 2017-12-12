package edu.sjsu.cs249.happypatients.resources;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.jms.JMSException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import edu.sjsu.cs249.happypatients.config.Config;
import edu.sjsu.cs249.happypatients.connectors.ActiveMQConnector;
import edu.sjsu.cs249.happypatients.messengers.Consumer;

@ServerEndpoint(value = "/messages")
public class SocketResource implements Observer {
	
	private static ActiveMQConnector mqConnector;
	private static Consumer emailConsumer;
	private static Map<String, Session> sessions = new HashMap<>();
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("[INFO] Client connected: " + session.getId());
		sessions.put(session.getId(), session);
		Config config;
		try {
			config = new ObjectMapper(new YAMLFactory())
					.readValue(new File(getClass().getClassLoader().getResource("config.yml").getFile()), Config.class);
			mqConnector = new ActiveMQConnector(config.getActiveMQProtocol(), config.getActiveMQHost(), config.getActiveMQPort());
			emailConsumer = new Consumer(mqConnector, config.getActiveMQTopic());
			emailConsumer.initialize();
			emailConsumer.startListening();
			emailConsumer.addObserver(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Iterator<Session> sessionIterator = sessions.values().iterator();
		while(sessionIterator.hasNext()) {
			Session s = sessionIterator.next();
			
			try {
				synchronized (s) {
					s.getBasicRemote().sendText((String)arg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@OnClose
	public void onClose(Session session) {
		sessions.remove(session.getId());
		try {
			session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
