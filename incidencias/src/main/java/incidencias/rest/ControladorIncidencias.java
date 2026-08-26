package incidencias.rest;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import incidencias.modelo.EstadoIncidencia;
import incidencias.rest.dto.AsignarIncidenciaDto;
import incidencias.rest.dto.RegistrarIncidenciaDto;
import incidencias.servicio.IServicioIncidencias;
import incidencias.servicio.IncidenciaResumen;
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
			String id = servicioIncidencias.registrarIncidencia(dto.getDescripcion(), dto.getUbicacion());
			URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
			return Response.created(nuevaURL).build();
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
	        servicioIncidencias.asignarIncidencia(idIncidencia, dto.getNombreTecnico(), dto.getTelefonoTecnico());
	        return Response.noContent().build();
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
			servicioIncidencias.resolverIncidencia(idIncidencia);
			return Response.noContent().build();
	}
	
	/**
	 * Endpoint para consultar incidencias filtradas por estado, ordenadas por fecha descendente (más recientes primero).
	 * @param estado Estado por el que filtrar: PENDIENTE, ASIGNADA, RESUELTA (opcional, si no se especifica devuelve todas).
	 * @return Una respuesta HTTP con una lista de resúmenes extendidos de las incidencias en formato JSON.
	 * @throws RepositorioException Si ocurre un error al acceder al repositorio de incidencias.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarIncidencias(@QueryParam("estado") String estado) throws RepositorioException {
		EstadoIncidencia estadoEnum = null;
		if (estado != null && !estado.trim().isEmpty()) {
			try {
				estadoEnum = EstadoIncidencia.valueOf(estado.trim().toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Estado inválido: " + estado + ". Valores permitidos: PENDIENTE, ASIGNADA, RESUELTA");
			}
		}
		
		List<IncidenciaResumen> resultado = servicioIncidencias.consultarIncidencias(estadoEnum);
		List<ResumenExtendido> extendidos = new LinkedList<>();
		resultado.forEach(incidenciaResumen -> {
			ResumenExtendido resumenExtendido = new ResumenExtendido();
			resumenExtendido.setResumen(incidenciaResumen);
			String id = incidenciaResumen.getId();
			URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
			resumenExtendido.setUrl(nuevaURL.toString());
			extendidos.add(resumenExtendido);
		});
		return Response.ok(extendidos).build();
	}

}
