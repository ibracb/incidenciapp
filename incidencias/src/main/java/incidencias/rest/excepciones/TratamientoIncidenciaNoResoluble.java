package incidencias.rest.excepciones;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import incidencias.excepciones.IncidenciaNoResoluble;

/**
 * Manejador de excepciones para IncidenciaNoResoluble.
 * Devuelve una respuesta HTTP 409 Conflict con el mensaje de la excepción.
 */
@Provider
public class TratamientoIncidenciaNoResoluble implements ExceptionMapper<IncidenciaNoResoluble> {
	
	@Override
	public Response toResponse(IncidenciaNoResoluble arg0) {
		return Response.status(Response.Status.CONFLICT).entity(arg0.getMessage()).build();
	}
	
}
