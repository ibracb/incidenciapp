package repositorio;

/**
 * Interfaz que define un objeto identificable por un ID único.
 */
public interface Identificable {

	/**
	 * Obtiene el ID único del objeto.
	 * 
	 * @return El ID del objeto.
	 */
	String getId();
	
	/**
	 * Establece el ID único del objeto.
	 * 
	 * @param id El ID a asignar al objeto.
	 */
	void setId(String id);
}
