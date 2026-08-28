package incidencias.servicio;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import incidencias.dto.IncidenciaDto;
import incidencias.excepciones.IncidenciaNoAsignable;
import incidencias.excepciones.IncidenciaNoResoluble;
import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Incidencia;
import incidencias.modelo.Tecnico;
import incidencias.repositorio.RepositorioIncidenciasAdHoc;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

/**
 * Implementación del servicio de gestión de incidencias.
 */
@Stateless
public class ServicioIncidencias implements IServicioIncidencias {
	
	/**
	 * Repositorio de incidencias para almacenar y gestionar las incidencias registradas.
	 */
	@EJB(beanName="RepositorioIncidencias")
	private RepositorioIncidenciasAdHoc repositorioIncidencias;
	
	@Override
	public String registrarIncidencia(String descripcion, String ubicacion) throws RepositorioException {
		if(descripcion == null || descripcion.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado ninguna descripción de la incidencia");
		}
		if(ubicacion == null || ubicacion.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado la ubicación de la incidencia");
		}
		Incidencia incidencia = new Incidencia(descripcion, ubicacion);
		repositorioIncidencias.add(incidencia);
		return incidencia.getId();
	}

	@Override
	public void asignarTecnicoIncidencia(String idIncidencia, String nombre, String telefono) throws RepositorioException, EntidadNoEncontrada {
		if(idIncidencia == null || idIncidencia.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado ninguna incidencia");
		}
		if(nombre == null || nombre.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado ningún nombre del técnico");
		}
		if(telefono == null || telefono.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado ningún teléfono del técnico");
		}
		if(!telefono.matches("\\d{9}")) {
			throw new IllegalArgumentException("Formato del teléfono especificado inválido. Solo 9 dígitos debe ser");
		}
		Incidencia incidencia = repositorioIncidencias.getById(idIncidencia);
		if(!incidencia.getEstado().equals(EstadoIncidencia.PENDIENTE)) {
			throw new IncidenciaNoAsignable("La incidencia debe estar en estado PENDIENTE para poder asignar un técnico");
		}
		Tecnico tecnico = new Tecnico(nombre, telefono);
		incidencia.setTecnico(tecnico);
		incidencia.setEstado(EstadoIncidencia.ASIGNADA);
		repositorioIncidencias.update(incidencia);
	}

	@Override
	public void resolverIncidencia(String idIncidencia) throws RepositorioException, EntidadNoEncontrada {
		if(idIncidencia == null || idIncidencia.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado ninguna incidencia");
		}
		Incidencia incidencia = repositorioIncidencias.getById(idIncidencia);
		if(!incidencia.getEstado().equals(EstadoIncidencia.ASIGNADA)) {
			throw new IncidenciaNoResoluble("La incidencia debe estar en estado ASIGNADA para poder ser resuelta");
		}
		incidencia.setEstado(EstadoIncidencia.RESUELTA);
		repositorioIncidencias.update(incidencia);
	}

	@Override
	public List<IncidenciaDto> consultarIncidencias(EstadoIncidencia estado) throws RepositorioException {
		return repositorioIncidencias.findByEstado(estado).stream()
				.map(incidencia -> toDto(incidencia))
				.collect(Collectors.toList());
	}
	
	private IncidenciaDto toDto(Incidencia incidencia) {
		return new IncidenciaDto(incidencia.getId(), incidencia.getDescripcion(),
				incidencia.getUbicacion(), incidencia.getFecha(), incidencia.getEstado(), incidencia.getTecnico());
	}

}
