package edu.sjsu.cs249.happypatients.resources;

import java.util.Collection;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.sjsu.cs249.happypatients.models.Patient;
import edu.sjsu.cs249.happypatients.services.PatientService;

@Path("/patients")
public class PatientResource {

    private PatientService service = new PatientService();
		
	@GET
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Patient getPatient(@PathParam("id") int id) {
        return service.getPatient(id);
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


}
