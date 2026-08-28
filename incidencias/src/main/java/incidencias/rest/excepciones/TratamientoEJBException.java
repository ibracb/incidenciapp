package incidencias.rest.excepciones;

import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import incidencias.excepciones.IncidenciaNoAsignable;
import incidencias.excepciones.IncidenciaNoResoluble;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

/**
 * Manejador de excepciones para EJBException.
 * Desenrolla la excepción y delega al mapper correspondiente según la causa real.
 */
@Provider
public class TratamientoEJBException implements ExceptionMapper<EJBException> {

	@Override
	public Response toResponse(EJBException ex) {
		Throwable cause = ex.getCause();
		
		if(cause instanceof IncidenciaNoResoluble) {
			return Response.status(Response.Status.CONFLICT)
					.entity(cause.getMessage()).build();
		}
		
		if(cause instanceof IncidenciaNoAsignable) {
			return Response.status(Response.Status.CONFLICT)
					.entity(cause.getMessage()).build();
		}
		
		if (cause instanceof IllegalArgumentException) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(cause.getMessage()).build();
		}
		if (cause instanceof EntidadNoEncontrada) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity(cause.getMessage()).build();
		}
		if (cause instanceof RepositorioException) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error en el repositorio: " + cause.getMessage()).build();
		}
		
		// Desconocida -> 500
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("Error interno del servidor").build();
	}

}