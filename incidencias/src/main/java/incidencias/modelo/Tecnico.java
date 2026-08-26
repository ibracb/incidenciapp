package incidencias.modelo;

import java.io.Serializable;

/**
 * Clase que representa a un técnico encargado de resolver incidencias.
 */
@SuppressWarnings("serial")
public class Tecnico implements Serializable {
	
	/**
	 * Nombre del técnico.
	 */
	private String nombre;
	
	/**
	 * Teléfono de contacto del técnico.
	 */
	private String telefono;

	/**
	 * Constructor de la clase Tecnico.
	 * 
	 * @param nombre   El nombre del técnico.
	 * @param telefono El teléfono de contacto del técnico.
	 */
	public Tecnico(String nombre, String telefono) {
		this.nombre = nombre;
		this.telefono = telefono;
	}
	
	/**
	 * Constructor vacío de la clase Tecnico.
	 */
	protected Tecnico() {
	}

	/**
	 * Obtiene el nombre del técnico.
	 * @return El nombre del técnico.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del técnico.
	 * @param nombre El nombre del técnico a establecer.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el teléfono de contacto del técnico.
	 * @return El teléfono de contacto del técnico.
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el teléfono de contacto del técnico.
	 * @param telefono El teléfono de contacto del técnico a establecer.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
