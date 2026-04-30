package repositorio;

/**
 * Excepción que representa una excepción del repositorio.
 * Al instanciarla, se establece la excepción interna que produce el error (causa).
 */
@SuppressWarnings("serial")
public class RepositorioException extends Exception {

	/**
	 * Constructor que recibe un mensaje de error y una causa.
	 * @param msg El mensaje de error que describe la excepción del repositorio.
	 * @param causa La causa interna que produce el error en el repositorio.
	 */
	public RepositorioException(String msg, Throwable causa) {		
		super(msg, causa);
	}
	
	/**
	 * Constructor que recibe un mensaje de error.
	 * @param msg El mensaje de error que describe la excepción del repositorio.
	 */
	public RepositorioException(String msg) {
		super(msg);		
	}
	
		
}
