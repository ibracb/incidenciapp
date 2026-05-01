package incidencias.servicio;

import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

/**
 * Interfaz que define los métodos para gestionar las incidencias.
 */
public interface IServicioIncidencias {
	
	/**
	 * Registra una nueva incidencia con la descripción y ubicación proporcionadas.
	 * @param descripcion La descripción de la incidencia.
	 * @param ubicacion La ubicación de la incidencia.
	 * @return El ID único de la incidencia registrada.
	 * @throws RepositorioException Si ocurre un error al acceder al repositorio de incidencias.
	 */
	String registrarIncidencia(String descripcion, String ubicacion) throws RepositorioException;
	
	/**
	 * Asigna un técnico a una incidencia específica.
	 * @param idIncidencia El ID de la incidencia a la que se asignará el técnico.
	 * @param nombreTecnico El nombre del técnico asignado.
	 * @param telefonoTecnico El teléfono del técnico asignado.
	 * @throws RepositorioException	 Si ocurre un error al acceder al repositorio de incidencias.
	 * @throws EntidadNoEncontrada  Si no se encuentra la incidencia con el ID proporcionado.
	 */
	void asignarIncidencia(String idIncidencia, String nombreTecnico, String telefonoTecnico) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Marca una incidencia como resuelta.
	 * @param idIncidencia El ID de la incidencia que se resolverá.
	 * @throws RepositorioException	 Si ocurre un error al acceder al repositorio de incidencias.
	 * @throws EntidadNoEncontrada  Si no se encuentra la incidencia con el ID proporcionado.
	 */
	void resolverIncidencia(String idIncidencia) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Consulta todas las incidencias que están pendientes de resolución.
	 * @return Una lista de resúmenes de las incidencias pendientes.
	 * @throws RepositorioException	 Si ocurre un error al acceder al repositorio de incidencias.
	 */
	List<IncidenciaResumen> consultarIncidenciasPendientes() throws RepositorioException;
	
}
