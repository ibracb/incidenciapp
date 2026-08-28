package incidencias.rest.excepciones;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Manejador de excepciones para IllegalArgumentException.
 * Devuelve una respuesta HTTP 400 Bad Request con el mensaje de error.
 */
@Provider
public class TratamientoIllegalArgument implements ExceptionMapper<IllegalArgumentException> {
	@Override
	public Response toResponse(IllegalArgumentException arg0) {
		return Response.status(Response.Status.BAD_REQUEST).entity(arg0.getMessage()).build();
	}
}
