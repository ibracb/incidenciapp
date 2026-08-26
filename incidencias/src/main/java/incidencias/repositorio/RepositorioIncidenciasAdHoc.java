package incidencias.repositorio;

import java.util.List;

import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Incidencia;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

/**
 * Interfaz que define métodos adicionales para el repositorio de incidencias,
 */
public interface RepositorioIncidenciasAdHoc extends RepositorioString<Incidencia> {
	
	/**
	 * Obtiene una lista de incidencias filtradas por estado, ordenadas por fecha descendente (más recientes primero).
	 * 
	 * @param estado Estado por el que filtrar (null para todos).
	 * @return Lista de incidencias.
	 * @throws RepositorioException Si ocurre un error al acceder al repositorio.
	 */
	List<Incidencia> findByEstado(EstadoIncidencia estado) throws RepositorioException;
	
}