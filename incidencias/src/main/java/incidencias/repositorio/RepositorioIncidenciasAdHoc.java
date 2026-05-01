package incidencias.repositorio;

import java.util.List;

import incidencias.modelo.Incidencia;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

/**
 * Interfaz que define métodos adicionales para el repositorio de incidencias,
 */
public interface RepositorioIncidenciasAdHoc extends RepositorioString<Incidencia> {
	
	/**
	 * Obtiene una lista de todas las incidencias que están en estado pendiente.
	 * 
	 * @return Lista de incidencias pendientes.
	 * @throws RepositorioException Si ocurre un error al acceder al repositorio.
	 */
	List<Incidencia> getPendientes() throws RepositorioException;
	
}
