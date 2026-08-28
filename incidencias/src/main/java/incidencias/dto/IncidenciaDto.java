package incidencias.dto;

import java.time.LocalDateTime;

import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Tecnico;

/**
 * Clase que representa un resumen de una incidencia, con la información
 * mostrada en el listado: identificador, descripción, ubicación, fecha de
 * reporte, estado y el técnico asignado (si lo tuviera).
 */
public class IncidenciaDto {
	
	/**
	 * Identificador único de la incidencia.
	 */
	private String id;
	
	/**
	 * Descripción de la incidencia reportada.
	 */
	private String descripcion;
	
	/**
	 * Ubicación donde se ha reportado la incidencia.
	 */
	private String ubicacion;
	
	/**
	 * Fecha y hora en que se reportó la incidencia.
	 */
	private String fecha;
	
	/**
	 * Estado actual de la incidencia (pendiente, asignada, o resuelta).
	 */
	private String estado;
	
	/**
	 * Técnico asignado a la incidencia (null si aún no está asignada).
	 */
	private TecnicoDto tecnico;
	
	/**
	 * Constructor de la clase IncidenciaResumen.
	 * 
	 * @param id          Identificador único de la incidencia.
	 * @param descripcion Descripción de la incidencia reportada.
	 * @param ubicacion   Ubicación donde se reportó la incidencia.
	 * @param fecha       Fecha y hora en que se reportó la incidencia.
	 * @param estado      Estado actual de la incidencia.
	 * @param tecnico     Técnico asignado (puede ser null).
	 */
	public IncidenciaDto(String id, String descripcion, String ubicacion, LocalDateTime fecha, EstadoIncidencia estado, Tecnico tecnico) {
		this.id = id;
		this.descripcion = descripcion;
		this.ubicacion = ubicacion;
		this.fecha = fecha.toString();
		this.estado = estado.toString();
		if (tecnico != null) {
			this.tecnico = new TecnicoDto(tecnico.getNombre(), tecnico.getTelefono());
		}
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
	 * Obtiene la ubicación donde se reportó la incidencia.
	 * @return La ubicación donde se reportó la incidencia.
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * Obtiene la fecha y hora en que se reportó la incidencia.
	 * @return La fecha y hora en que se reportó la incidencia.
	 */
	public String getFecha() {
		return fecha;
	}
	
	/**
	 * Obtiene el estado de la incidencia.
	 * @return El estado de la incidencia.
	 */
	public String getEstado() {
		return estado;
	}
	
	/**
	 * Obtiene el técnico asignado a la incidencia.
	 * @return El técnico asignado, o null si no está asignada.
	 */
	public TecnicoDto getTecnico() {
		return tecnico;
	}

	/**
	 * Clase que representa un resumen del técnico asignado a una incidencia.
	 */
	public static class TecnicoDto {
		
		/**
		 * Nombre del técnico.
		 */
		private String nombre;
		
		/**
		 * Teléfono de contacto del técnico.
		 */
		private String telefono;
		
		/**
		 * Constructor de la clase TecnicoResumen.
		 * @param nombre   Nombre del técnico.
		 * @param telefono Teléfono de contacto del técnico.
		 */
		public TecnicoDto(String nombre, String telefono) {
			this.nombre = nombre;
			this.telefono = telefono;
		}
		
		/**
		 * Obtiene el nombre del técnico.
		 * @return El nombre del técnico.
		 */
		public String getNombre() {
			return nombre;
		}
		
		/**
		 * Obtiene el teléfono de contacto del técnico.
		 * @return El teléfono de contacto del técnico.
		 */
		public String getTelefono() {
			return telefono;
		}
	}
}
