package incidencias.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import repositorio.Identificable;

/**
 * Clase que representa una incidencia reportada en el sistema.
 */
@SuppressWarnings("serial")
public class Incidencia implements Identificable, Serializable {
	
	/**
	 * Identificador único de la incidencia.
	 */
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
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
	 * Estado actual de la incidencia (pendiente, asignada, o resuelta).
	 */
	private EstadoIncidencia estado;
	
	/**
	 * Fecha y hora en que se reportó la incidencia.
	 */
	private LocalDateTime fecha;
	
	/**
	 * Técnico asignado para resolver la incidencia.
	 */
	private Tecnico tecnico;

	/**
	 * Constructor de la clase Incidencia.
	 * 
	 * @param descripcion Descripción de la incidencia reportada.
	 * @param ubicacion   Ubicación donde se ha reportado la incidencia.
	 * @param tecnico     Técnico asignado para resolver la incidencia.
	 */
	public Incidencia(String descripcion, String ubicacion) {
		this.descripcion = descripcion;
		this.ubicacion = ubicacion;
		this.estado = EstadoIncidencia.PENDIENTE;
		this.fecha = LocalDateTime.now();
	}
	
	/**
	 * Constructor vacío de la clase Incidencia.
	 */
	protected Incidencia() {
	}

	/**
	 * Obtiene el identificador único de la incidencia.
	 * @return El identificador único de la incidencia.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador único de la incidencia.
	 * @param id El identificador único de la incidencia a establecer.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene la descripción de la incidencia reportada.
	 * @return La descripción de la incidencia reportada.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción detallada de la incidencia reportada.
	 * @param descripcion La descripción de la incidencia reportada a establecer.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene la ubicación donde se ha reportado la incidencia.
	 * @return La ubicación donde se ha reportado la incidencia.
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * Establece la ubicación donde se ha reportado la incidencia.
	 * @param ubicacion La ubicación donde se ha reportado la incidencia a establecer.
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * Obtiene el estado actual de la incidencia.
	 * @return El estado actual de la incidencia.
	 */
	public EstadoIncidencia getEstado() {
		return estado;
	}

	/**
	 * Establece el estado actual de la incidencia.
	 * @param estado El estado actual de la incidencia a establecer.
	 */
	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene la fecha y hora en que se reportó la incidencia.
	 * @return La fecha y hora en que se reportó la incidencia.
	 */
	public LocalDateTime getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha y hora en que se reportó la incidencia.
	 * @param fecha La fecha y hora en que se reportó la incidencia a establecer.
	 */
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el técnico asignado para resolver la incidencia.
	 * @return El técnico asignado para resolver la incidencia.
	 */
	public Tecnico getTecnico() {
		return tecnico;
	}

	/**
	 * Establece el técnico asignado para resolver la incidencia.
	 * @param tecnico El técnico asignado para resolver la incidencia a establecer.
	 */
	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}
	
}
