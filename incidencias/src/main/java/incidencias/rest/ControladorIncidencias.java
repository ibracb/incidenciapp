package incidencias.rest;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import incidencias.dto.IncidenciaDto;
import incidencias.modelo.EstadoIncidencia;
import incidencias.rest.dto.in.AsignarTecnicoIncidenciaDto;
import incidencias.rest.dto.in.RegistrarIncidenciaDto;
import incidencias.rest.dto.out.DtoExtendido;
import incidencias.servicio.IServicioIncidencias;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

/**
 * Controlador REST para gestionar las incidencias.
 * Implementa {@link IncidenciasApi}, que concentra las anotaciones JAX-RS y Swagger.
 * Se mantiene @Path en la clase para que el escaneo de RESTEasy descubra el recurso.
 */
@Path("/incidencias")
@Stateless
public class ControladorIncidencias implements IncidenciasApi {

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

	@Override
	public Response registrarIncidencia(RegistrarIncidenciaDto dto) throws RepositorioException {
		String id = servicioIncidencias.registrarIncidencia(dto.getDescripcion(), dto.getUbicacion());
		URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(nuevaURL).build();
	}

	@Override
	public Response asignarTecnicoIncidencia(String idIncidencia, AsignarTecnicoIncidenciaDto dto)
	        throws RepositorioException, EntidadNoEncontrada {
		servicioIncidencias.asignarTecnicoIncidencia(idIncidencia, dto.getNombre(), dto.getTelefono());
		return Response.noContent().build();
	}

	@Override
	public Response resolverIncidencia(String idIncidencia) throws RepositorioException, EntidadNoEncontrada {
		servicioIncidencias.resolverIncidencia(idIncidencia);
		return Response.noContent().build();
	}

	@Override
	public Response consultarIncidencias(String estado) throws RepositorioException {
		EstadoIncidencia estadoEnum = null;
		if (estado != null && !estado.trim().isEmpty()) {
			try {
				estadoEnum = EstadoIncidencia.valueOf(estado.trim().toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Estado inválido: " + estado + ". Valores permitidos: PENDIENTE, ASIGNADA, RESUELTA");
			}
		}
		List<IncidenciaDto> resultado = servicioIncidencias.consultarIncidencias(estadoEnum);
		List<DtoExtendido> extendidos = new LinkedList<>();
		resultado.forEach(incidenciaResumen -> {
			DtoExtendido resumenExtendido = new DtoExtendido();
			resumenExtendido.setResumen(incidenciaResumen);
			String id = incidenciaResumen.getId();
			URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
			resumenExtendido.setUrl(nuevaURL.toString());
			extendidos.add(resumenExtendido);
		});
		return Response.ok(extendidos).build();
	}
}
