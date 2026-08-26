package incidencias.servicio;

import java.time.LocalDateTime;

/**
 * Clase que representa un resumen de una incidencia, con información básica
 * como el identificador, la descripción y la fecha de reporte.
 */
public class IncidenciaResumen {
	
	/**
	 * Identificador único de la incidencia.
	 */
	private String id;
	
	/**
	 * Descripción de la incidencia reportada.
	 */
	private String descripcion;
	
	/**
	 * Fecha y hora en que se reportó la incidencia.
	 */
	private String fecha;
	
	/**
	 * Constructor de la clase IncidenciaResumen.
	 * 
	 * @param id          Identificador único de la incidencia.
	 * @param descripcion Descripción de la incidencia reportada.
	 * @param fecha       Fecha y hora en que se reportó la incidencia.
	 */
	public IncidenciaResumen(String id, String descripcion, LocalDateTime fecha) {
		this.id = id;
		this.descripcion = descripcion;
		this.fecha = fecha.toString();
	}
	
	/**
	 * Obtiene el identificador único de la incidencia.
	 * @return El identificador único de la incidencia.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Obtiene la descripción de la incidencia reportada.
	 * @return La descripción de la incidencia reportada.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Obtiene la fecha y hora en que se reportó la incidencia.
	 * @return La fecha y hora en que se reportó la incidencia.
	 */
	public String getFecha() {
		return fecha;
	}

}
