package incidencias.servicio;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

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
	public void asignarIncidencia(String idIncidencia, String nombreTecnico, String telefonoTecnico) throws RepositorioException, EntidadNoEncontrada {
		if(idIncidencia == null || idIncidencia.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado ninguna incidencia");
		}
		if(nombreTecnico == null || nombreTecnico.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado ningún nombre del técnico");
		}
		if(telefonoTecnico == null || telefonoTecnico.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado ningún teléfono del técnico");
		}
		if(!telefonoTecnico.matches("\\d{9}")) {
			throw new IllegalArgumentException("Formato del teléfono especificado inválido. Solo 9 dígitos debe ser");
		}
		Incidencia incidencia = repositorioIncidencias.getById(idIncidencia);
		if(incidencia.getEstado() == EstadoIncidencia.ASIGNADA) {
			throw new IllegalArgumentException("La incidencia ya se encuentra asignada a un técnico");
		}
		if(incidencia.getEstado() == EstadoIncidencia.RESUELTA) {
			throw new IllegalArgumentException("La incidencia ya se encuentra resuelta, no se puede asignar a un técnico");
		}
		Tecnico tecnico = new Tecnico(nombreTecnico, telefonoTecnico);
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
		if(incidencia.getEstado() == EstadoIncidencia.PENDIENTE) {
			throw new IllegalArgumentException("La incidencia no se encuentra asignada a ningún técnico, no se puede resolver");
		}
		if(incidencia.getEstado() == EstadoIncidencia.RESUELTA) {
			throw new IllegalArgumentException("La incidencia ya se encuentra resuelta");
		}
		incidencia.setEstado(EstadoIncidencia.RESUELTA);
		repositorioIncidencias.update(incidencia);
	}

	@Override
	public List<IncidenciaResumen> consultarIncidenciasPendientes() throws RepositorioException {
		return repositorioIncidencias.getPendientes().stream()
				.map(incidencia -> new IncidenciaResumen(incidencia.getId(), incidencia.getDescripcion(),
						incidencia.getFecha()))
				.collect(Collectors.toList());
	}

}
