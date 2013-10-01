/**
 * 
 */
package com.organization.app.beans;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author Ramakrishna Sharvirala
 *
 */
public class BACCRestService {

	/**
	 * The REST service's single method that receives a JSON request and returns a JSON response.
	 * @param data
	 * @return
	 */
	@POST
	@Path("/info")
	@Produces("application/json")
	//@Consumes("application/json")
	public Response process(String data) {
		return null;
	}
	
}
