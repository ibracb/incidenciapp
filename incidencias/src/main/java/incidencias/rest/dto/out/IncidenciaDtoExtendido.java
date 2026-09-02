package incidencias.rest.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Clase que representa un resumen extendido de una incidencia, que incluye
 * la URL para acceder al recurso de la incidencia y un dto con
 * información de la incidencia.
 */
@Schema(description = "Resumen extendido de una incidencia con URL de acceso")
public class IncidenciaDtoExtendido {
	
	/**
	 * URL para acceder al recurso de la incidencia.
	 */
	@Schema(description = "URL para acceder al recurso de la incidencia", example = "/incidencias/64a1b2c3d4e5f6a7b8c9d0e1")
	private String url;
	
	/**
	 * Dto con información de la incidencia.
	 */
	@Schema(description = "Dto de incidencia con información básica de la incidencia")
	private IncidenciaDto dto;

	/**
	 * Obtiene la URL para acceder al recurso de la incidencia.
	 * @return La URL para acceder al recurso de la incidencia.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Establece la URL para acceder al recurso de la incidencia.
	 * @param url La URL para acceder al recurso de la incidencia.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Obtiene el dto con información de la incidencia.
	 * @return El resumen con información básica de la incidencia.
	 */
	public IncidenciaDto getDto() {
		return dto;
	}

	/**
	 * Establece el dto con información de la incidencia.
	 * @param dto El dto con información de la incidencia.
	 */
	public void setDto(IncidenciaDto dto) {
		this.dto = dto;
	}

}
