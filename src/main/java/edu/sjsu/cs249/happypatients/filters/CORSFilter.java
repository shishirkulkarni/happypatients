package edu.sjsu.cs249.happypatients.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
		res.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
		res.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		res.getHeaders().add("Access-Control-Allow-Credentials", "true");
		res.getHeaders().add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE");
	}

}
