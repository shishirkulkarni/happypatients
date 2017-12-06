package edu.sjsu.cs249.happypatients.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PolicyServer {
	private String host, path, protocol;
	private int port;
	
	public PolicyServer() {}
	
	public PolicyServer(String host, int port, String protocol, String path) {
		this.host = host;
		this.port = port;
		this.path = path;
		this.protocol = protocol;
	}
		
	public String getPolicy() throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append(this.protocol + "://");
		builder.append(this.host);
		builder.append(":" + this.port);
		builder.append(this.path);
		
		URL url = new URL(builder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "text/plain");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		return reader.readLine();
	}
}
