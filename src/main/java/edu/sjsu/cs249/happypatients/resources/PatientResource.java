package edu.sjsu.cs249.happypatients.resources;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import edu.sjsu.cs249.happypatients.models.Patient;
import edu.sjsu.cs249.happypatients.services.PatientService;

@Path("/patients")
public class PatientResource {

    private static PatientService service = new PatientService();
		
	@GET
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Patient getPatient(@PathParam("id") UUID id) throws JsonParseException, JsonMappingException, IOException {
		try {
			Patient p = service.getPatient(id);
			if(p == null)
				throw new NotFoundException();
			return p;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerErrorException();
		}
    }
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Patient> getPatients() {
        return service.getPatients();
    }
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Patient postPatient(Patient p) {
		try {
			service.postPatient(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new InternalServerErrorException();
		}
		return p;
	}

	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Patient putPatient(@PathParam("id") UUID id, Patient p) {
		try {
			p.setUuid(id);
			service.putPatient(p);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new InternalServerErrorException();
		}
		return p;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePatient(@PathParam("id") UUID id) {
		Patient p = new Patient();
		p.setUuid(id);
		service.deletePatient(p);
		return "Patient deleted successfully";
	}

}
