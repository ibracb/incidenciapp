package incidencias.rest;

import incidencias.servicio.IncidenciaResumen;

/**
 * Clase que representa un resumen extendido de una incidencia, que incluye
 * la URL para acceder a los detalles de la incidencia y un resumen con
 * información básica de la incidencia.
 */
public class ResumenExtendido {
	
	/**
	 * URL para acceder a los detalles de la incidencia.
	 */
	private String url;
	
	/**
	 * Resumen con información básica de la incidencia.
	 */
	private IncidenciaResumen resumen;

	/**
	 * Obtiene la URL para acceder a los detalles de la incidencia.
	 * @return La URL para acceder a los detalles de la incidencia.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Establece la URL para acceder a los detalles de la incidencia.
	 * @param url La URL para acceder a los detalles de la incidencia.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Obtiene el resumen con información básica de la incidencia.
	 * @return El resumen con información básica de la incidencia.
	 */
	public IncidenciaResumen getResumen() {
		return resumen;
	}

	/**
	 * Establece el resumen con información básica de la incidencia.
	 * @param resumen El resumen con información básica de la incidencia.
	 */
	public void setResumen(IncidenciaResumen resumen) {
		this.resumen = resumen;
	}

}
