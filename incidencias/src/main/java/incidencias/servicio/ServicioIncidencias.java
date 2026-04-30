package incidencias.servicio;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Incidencia;
import repositorio.EntidadNoEncontrada;
import repositorio.Repositorio;
import repositorio.RepositorioException;

@Stateless
public class ServicioIncidencias implements IServicioIncidencias {
	
	@EJB(beanName="RepositorioIncidencias")
	private Repositorio<Incidencia, String> repositorioIncidencias;
	
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
		if(incidencia.getEstado() != EstadoIncidencia.PENDIENTE) {
			throw new IllegalArgumentException("La incidencia debe encontrarse en estado PENDIENTE para asignarla");
		}
		incidencia.setEstado(EstadoIncidencia.ASIGNADA);
		repositorioIncidencias.update(incidencia);
	}

	@Override
	public void resolverIncidencia(String idIncidencia) throws RepositorioException, EntidadNoEncontrada {
		if(idIncidencia == null || idIncidencia.matches("\\s*")) {
			throw new IllegalArgumentException("No se ha especificado ninguna incidencia");
		}
		Incidencia incidencia = repositorioIncidencias.getById(idIncidencia);
		if(incidencia.getEstado() != EstadoIncidencia.ASIGNADA) {
			throw new IllegalArgumentException("La incidencia debe encontrarse en estado ASIGNADA para poder ser resuelta");
		}
		incidencia.setEstado(EstadoIncidencia.RESUELTA);
		repositorioIncidencias.update(incidencia);
	}

}
