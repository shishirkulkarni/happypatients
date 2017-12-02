package edu.sjsu.cs249.happypatients.resources;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.sjsu.cs249.happypatients.connectors.CassandraConnector;
import edu.sjsu.cs249.happypatients.connectors.RedisConnector;
import edu.sjsu.cs249.happypatients.models.HTTPResponse;
import edu.sjsu.cs249.happypatients.models.Patient;
import edu.sjsu.cs249.happypatients.services.PatientService;

@Path("/patients")
public class PatientResource {

    private PatientService service = new PatientService();
    private RedisConnector redisConnector = new RedisConnector("localhost", 6379);
		
	@GET
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Patient getPatient(@PathParam("id") int id) {
//        return service.getPatient(id);
		Patient p = new Patient();
		p.setId(1);
		p.setName("Shishir Kulkarni");
//		p.setName(connector.executeQuery());
		
//		try {
//			redisConnector.set("name", p.toJSON());
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			String patient = redisConnector.get("name");
			if(patient == null) 
				throw new NotFoundException("Patient not found");
			else
				return Patient.fromJSON(patient);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    }
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Patient> getPatients() {
        return service.getPatients();
    }
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Patient putPatient(Patient p) {
		if(service.putPatient(p) == null) {
			throw new BadRequestException();
		}
		
		return p;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public HTTPResponse postPatient(Patient p) {
		service.createPatient(p);
		return new HTTPResponse("User Inserted Successfully!!!");
	}


}
