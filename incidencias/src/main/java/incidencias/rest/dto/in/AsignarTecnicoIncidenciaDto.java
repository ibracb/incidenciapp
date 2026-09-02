package incidencias.rest.dto.in;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para asignar una incidencia a un técnico.
 */
@Schema(description = "Datos de entrada para asignar un técnico a una incidencia")
@SuppressWarnings("serial")
public class AsignarTecnicoIncidenciaDto implements Serializable {
	
	/**
	 * Nombre del técnico asignado a la incidencia.
	 */
	@Schema(description = "Nombre del técnico asignado a la incidencia", example = "Juan García")
	@NotBlank
	private String nombre;
	
	/**
	 * Teléfono del técnico asignado a la incidencia.
	 */
	@Schema(description = "Teléfono del técnico asignado a la incidencia", example = "612345678")
	@NotBlank
	private String telefono;

	/**
	 * Constructor para crear un DTO de asignación de incidencia.
	 * 
	 * @param nombre El nombre del técnico asignado a la incidencia.
	 * @param telefono El teléfono del técnico asignado a la incidencia.
	 */
	public AsignarTecnicoIncidenciaDto(String nombre, String telefono) {
		this.nombre = nombre;
		this.telefono = telefono;
	}
	
	/**
	 * Constructor vacío de AsignarIncidenciaDto.
	 */
	public AsignarTecnicoIncidenciaDto() {
	}

	/**
	 * Obtiene el nombre del técnico asignado a la incidencia.
	 * @return El nombre del técnico asignado a la incidencia.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Establece el nombre del técnico asignado a la incidencia.
	 * @param nombre El nombre del técnico asignado a la incidencia.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el teléfono del técnico asignado a la incidencia.
	 * @return El teléfono del técnico asignado a la incidencia.
	 */
	public String getTelefono() {
		return telefono;
	}
	
	/**
	 * Establece el teléfono del técnico asignado a la incidencia.
	 * @param telefono El teléfono del técnico asignado a la incidencia.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
