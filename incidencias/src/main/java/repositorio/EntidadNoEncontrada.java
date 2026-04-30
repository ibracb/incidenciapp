package repositorio;

/**
 * Excepción notificada si no existe el identificador de la entidad.
 */
@SuppressWarnings("serial")
public class EntidadNoEncontrada extends Exception {

	/**
	 * Constructor que recibe un mensaje de error y una causa.
	 * @param msg El mensaje de error que describe la excepción de entidad no encontrada.
	 * @param causa La causa interna que produce el error de entidad no encontrada.
	 */
	public EntidadNoEncontrada(String msg, Throwable causa) {		
		super(msg, causa);
	}
	
	/**
	 * Constructor que recibe un mensaje de error.
	 * @param msg El mensaje de error que describe la excepción de entidad no encontrada.
	 */
	public EntidadNoEncontrada(String msg) {
		super(msg);		
	}
	
		
}
