package incidencias.rest.excepciones;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import repositorio.RepositorioException;

/**
 * Manejador de excepciones para RepositorioException.
 * Devuelve una respuesta HTTP 500 Internal Server Error con el mensaje de la excepción.
 */
@Provider
public class TratamientoRepositorio implements ExceptionMapper<RepositorioException> {
	@Override
	public Response toResponse(RepositorioException arg0) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(arg0.getMessage()).build();
	}
}
