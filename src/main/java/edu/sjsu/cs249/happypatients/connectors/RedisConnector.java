package edu.sjsu.cs249.happypatients.connectors;

import redis.clients.jedis.Jedis;

public class RedisConnector {
	private Jedis jedis;
	private String hostname;
	private int port;

	public RedisConnector(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	
	public void createConnection() {
		jedis = new Jedis(this.hostname, this.port);
	}
	
	public String get(String key) {
		if(jedis == null) {
			createConnection();
		}
		return jedis.get(key);
	}
	
	public void set(String key, String value) {
		if(jedis == null) {
			createConnection();
		}
		jedis.set(key, value);
	}
}
