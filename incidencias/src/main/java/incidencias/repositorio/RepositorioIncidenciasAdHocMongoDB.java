package incidencias.repositorio;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Incidencia;
import repositorio.RepositorioException;

/**
 * Implementación en memoria del repositorio de incidencias con métodos adicionales.
 */
@Singleton(name = "RepositorioIncidencias")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Startup
@Lock(LockType.READ)
public class RepositorioIncidenciasAdHocMemoria extends RepositorioIncidenciasMemoria
		implements RepositorioIncidenciasAdHoc {

	@Override
	public List<Incidencia> getPendientes() throws RepositorioException {
		return getAll().stream()
				.filter(incidencia -> incidencia.getEstado().equals(EstadoIncidencia.PENDIENTE))
				.collect(Collectors.toList());
	}

}
