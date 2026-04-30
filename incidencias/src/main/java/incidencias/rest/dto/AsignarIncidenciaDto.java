package incidencias.rest.dto;

import java.io.Serializable;

/**
 * DTO para asignar una incidencia a un técnico.
 */
@SuppressWarnings("serial")
public class AsignarIncidenciaDto implements Serializable {
	
	/**
	 * Nombre del técnico asignado a la incidencia.
	 */
	private String nombreTecnico;
	
	/**
	 * Teléfono del técnico asignado a la incidencia.
	 */
	private String telefonoTecnico;

	/**
	 * Constructor para crear un DTO de asignación de incidencia.
	 * @param descripcion La descripción de la incidencia.
	 * @param ubicacion La ubicación de la incidencia.
	 */
	public AsignarIncidenciaDto(String nombreTecnico, String telefonoTecnico) {
		this.nombreTecnico = nombreTecnico;
		this.telefonoTecnico = telefonoTecnico;
	}
	
	/**
	 * Constructor vacío de AsignarIncidenciaDto.
	 */
	public AsignarIncidenciaDto() {
	}

	/**
	 * Obtiene el nombre del técnico asignado a la incidencia.
	 * @return El nombre del técnico asignado a la incidencia.
	 */
	public String getNombreTecnico() {
		return nombreTecnico;
	}
	
	/**
	 * Establece el nombre del técnico asignado a la incidencia.
	 * @param nombreTecnico El nombre del técnico asignado a la incidencia.
	 */
	public void setNombreTecnico(String nombreTecnico) {
		this.nombreTecnico = nombreTecnico;
	}

	/**
	 * Obtiene el teléfono del técnico asignado a la incidencia.
	 * @return El teléfono del técnico asignado a la incidencia.
	 */
	public String getTelefonoTecnico() {
		return telefonoTecnico;
	}
	
	/**
	 * Establece el teléfono del técnico asignado a la incidencia.
	 * @param telefonoTecnico El teléfono del técnico asignado a la incidencia.
	 */
	public void setTelefonoTecnico(String telefonoTecnico) {
		this.telefonoTecnico = telefonoTecnico;
	}
	
}
