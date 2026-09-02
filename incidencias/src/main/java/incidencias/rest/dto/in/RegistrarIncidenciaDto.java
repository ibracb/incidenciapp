package incidencias.rest.dto.in;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para registrar una nueva incidencia.
 */
@Schema(description = "Datos de entrada para registrar una nueva incidencia")
@SuppressWarnings("serial")
public class RegistrarIncidenciaDto implements Serializable {
	
	/**
	 * Descripción de la incidencia.
	 */
	@Schema(description = "Descripción de la incidencia", example = "Fuga de agua en el baño principal")
	@NotBlank
	private String descripcion;
	
	/**
	 * Ubicación de la incidencia.
	 */
	@Schema(description = "Ubicación de la incidencia", example = "Edificio A, Planta 2, Baño 201")
	@NotBlank
	private String ubicacion;

	/**
	 * Constructor para crear un DTO de registro de incidencia.
	 * @param descripcion La descripción de la incidencia.
	 * @param ubicacion La ubicación de la incidencia.
	 */
	public RegistrarIncidenciaDto(String descripcion, String ubicacion) {
		this.descripcion = descripcion;
		this.ubicacion = ubicacion;
	}
	
	/**
	 * Constructor vacío de RegistrarIncidenciaDto.
	 */
	public RegistrarIncidenciaDto() {
	}

	/**
	 * Obtiene la descripción de la incidencia.
	 * @return La descripción de la incidencia.
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Establece la descripción de la incidencia.
	 * @param descripcion La descripción de la incidencia.
	 */
	public void setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	}

	/**
	 * Obtiene la ubicación de la incidencia.
	 * @return La ubicación de la incidencia.
	 */
	public String getUbicacion() {
		return ubicacion;
	}
	
	/**
	 * Establece la ubicación de la incidencia.
	 * @param ubicacion La ubicación de la incidencia.
	 */
	public void setUbicacion(String ubicacion) {
	    this.ubicacion = ubicacion;
	}

}
