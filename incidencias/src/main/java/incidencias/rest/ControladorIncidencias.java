package incidencias.rest;

import java.net.URI;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import incidencias.rest.dto.AsignarIncidenciaDto;
import incidencias.rest.dto.RegistrarIncidenciaDto;
import incidencias.servicio.IServicioIncidencias;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

/**
 * Controlador REST para gestionar las incidencias.
 */
@Path("incidencias")
@Stateless
public class ControladorIncidencias {
	
	/**
	 * Servicio para gestionar las incidencias.
	 */
	@EJB(beanName="ServicioIncidencias")
	private IServicioIncidencias servicioIncidencias;

	/**
	 * Contexto para obtener información de la URI.
	 */
	@Context
	private UriInfo uriInfo;
	
	/**
	 * Contexto para obtener información de la solicitud HTTP.
	 */
	@Context
	private HttpServletRequest servletRequest;
	
	/**
	 * Endpoint para registrar una nueva incidencia.
	 * Recibe un objeto JSON con la descripción y ubicación de la incidencia,
	 * y devuelve una respuesta con el ID de la incidencia registrada.
	 * @param dto	 Objeto DTO que contiene la descripción y ubicación de la incidencia.
	 * @return	Una respuesta HTTP con el ID de la incidencia registrada en la cabecera "Location".
	 * @throws RepositorioException Si ocurre un error al acceder al repositorio de incidencias.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registrarIncidencia(RegistrarIncidenciaDto dto) throws RepositorioException {
		try{
			String id = servicioIncidencias.registrarIncidencia(dto.getDescripcion(), dto.getUbicacion());
			URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
			return Response.created(nuevaURL).build();
		}
		catch (EJBTransactionRolledbackException e) {
	    	if (e.getCause() instanceof IllegalArgumentException) {
		        return Response.status(Response.Status.BAD_REQUEST)
		                       .entity(e.getCause().getMessage())
		                       .build();
		    }
	    	if (e.getCause() instanceof RepositorioException) {
		        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		                       .entity("Error al acceder al repositorio de incidencias")
		                       .build();
		    }
		    throw new RuntimeException(e);
		}
	}
	
	/**
	 * Endpoint para asignar un técnico a una incidencia existente.
	 * Recibe el ID de la incidencia en la URL y un objeto JSON con el nombre y teléfono del técnico.
	 * Devuelve una respuesta sin contenido si la asignación se realiza correctamente.
	 * @param idIncidencia El ID de la incidencia a la que se asignará el técnico.
	 * @param dto		 Objeto DTO que contiene el nombre y teléfono del técnico a asignar.
	 * @return			 Una respuesta HTTP sin contenido si la asignación se realiza correctamente.
	 * @throws RepositorioException Si ocurre un error al acceder al repositorio de incidencias.
	 * @throws EntidadNoEncontrada  Si no se encuentra la incidencia con el ID proporcionado.
	 */
	@PATCH
	@Path("/{id}/asignar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response asignarIncidencia(@PathParam("id") String idIncidencia, AsignarIncidenciaDto dto) 
	        throws RepositorioException, EntidadNoEncontrada {
	    try {
	        servicioIncidencias.asignarIncidencia(idIncidencia, dto.getNombreTecnico(), dto.getTelefonoTecnico());
	        return Response.noContent().build();
	    }
	    catch (EJBTransactionRolledbackException e) {
	    	if (e.getCause() instanceof EntidadNoEncontrada) {
		        return Response.status(Response.Status.NOT_FOUND)
		                       .entity(e.getCause().getMessage())
		                       .build();
		    }
	    	if (e.getCause() instanceof IllegalArgumentException) {
		        return Response.status(Response.Status.BAD_REQUEST)
		                       .entity(e.getCause().getMessage())
		                       .build();
		    }
	    	if (e.getCause() instanceof RepositorioException) {
		        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		                       .entity("Error al acceder al repositorio de incidencias")
		                       .build();
		    }
		    throw new RuntimeException(e);
		}
	}
	
	/**
	 * Endpoint para resolver una incidencia existente.
	 * Recibe el ID de la incidencia en la URL y marca la incidencia como resuelta.
	 * Devuelve una respuesta sin contenido si la resolución se realiza correctamente.
	 * @param idIncidencia El ID de la incidencia que se resolverá.
	 * @return			 Una respuesta HTTP sin contenido si la resolución se realiza correctamente.
	 * @throws RepositorioException Si ocurre un error al acceder al repositorio de incidencias.
	 * @throws EntidadNoEncontrada  Si no se encuentra la incidencia con el ID proporcionado.
	 */
	@PATCH
	@Path("/{id}/resolver")
	public Response resolverIncidencia(@PathParam("id") String idIncidencia) throws RepositorioException, EntidadNoEncontrada {
		try{
			servicioIncidencias.resolverIncidencia(idIncidencia);
			return Response.noContent().build();
		}
		catch (EJBTransactionRolledbackException e) {
	    	if (e.getCause() instanceof EntidadNoEncontrada) {
		        return Response.status(Response.Status.NOT_FOUND)
		                       .entity(e.getCause().getMessage())
		                       .build();
		    }
	    	if (e.getCause() instanceof IllegalArgumentException) {
		        return Response.status(Response.Status.BAD_REQUEST)
		                       .entity(e.getCause().getMessage())
		                       .build();
		    }
	    	if (e.getCause() instanceof RepositorioException) {
		        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		                       .entity("Error al acceder al repositorio de incidencias")
		                       .build();
		    }
		    throw new RuntimeException(e);
		}
	}

}
