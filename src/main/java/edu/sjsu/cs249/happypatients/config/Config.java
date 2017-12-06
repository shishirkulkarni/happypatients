package edu.sjsu.cs249.happypatients.config;

import java.util.Map;

public class Config {
	Map<String, String> cassandra;
	Map<String, String> redis;
	Map<String, String> policyserver;
	
	public Map<String, String> getPolicyserver() {
		return policyserver;
	}

	public void setPolicyserver(Map<String, String> policyserver) {
		this.policyserver = policyserver;
	}

	public Map<String, String> getCassandra() {
		return cassandra;
	}

	public void setCassandra(Map<String, String> cassandra) {
		this.cassandra = cassandra;
	}

	public Map<String, String> getRedis() {
		return redis;
	}

	public void setRedis(Map<String, String> redis) {
		this.redis = redis;
	}
	
	public String getRedisHost() {
		return redis.get("hostname");
	}
	
	public String getCassandraHost() {
		return cassandra.get("hostname");
	}
	
	public int getCassandraPort() {
		return Integer.parseInt(cassandra.get("port"));
	}
	
	public String getCassandraKeyspace() {
		return cassandra.get("keyspace");
	}
	
	
	public int getRedisPort() {
		return Integer.parseInt(redis.get("port"));
	}
	
	public String getPolicyServerHost() {
		return policyserver.get("hostname");
	}
	
	public int getPolicyServerPort() {
		return Integer.parseInt(policyserver.get("port"));
	}
	
	public String getPolicyServerProtocol() {
		return policyserver.get("protocol");
	}
	
	public String getPolicyServerPath() {
		return policyserver.get("path");
	}
}
