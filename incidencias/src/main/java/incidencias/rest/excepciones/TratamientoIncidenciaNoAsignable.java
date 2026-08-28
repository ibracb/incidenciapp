package incidencias.rest.excepciones;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import incidencias.excepciones.IncidenciaNoAsignable;

/**
 * Manejador de excepciones para IncidenciaNoAsignable.
 * Devuelve una respuesta HTTP 409 Conflict con el mensaje de la excepción.
 */
@Provider
public class TratamientoIncidenciaNoAsignable implements ExceptionMapper<IncidenciaNoAsignable> {
	
	@Override
	public Response toResponse(IncidenciaNoAsignable arg0) {
		return Response.status(Response.Status.CONFLICT).entity(arg0.getMessage()).build();
	}
	
}
