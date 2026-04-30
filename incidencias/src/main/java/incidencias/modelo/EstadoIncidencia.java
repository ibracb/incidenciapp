package incidencias.modelo;

/**
 * Enum que representa los posibles estados de una incidencia.
 */
public enum EstadoIncidencia {
	
	/**
	 * Indica que la incidencia está pendiente de ser asignada a un técnico.
	 */
	PENDIENTE,
	
	/**
	 * Indica que la incidencia ha sido asignada a un técnico y está en proceso de resolución.
	 */
	ASIGNADA,
	
	/**
	 * Indica que la incidencia ha sido resuelta y se ha cerrado el caso.
	 */
	RESUELTA
	
}
