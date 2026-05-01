package utils;

import java.util.UUID;

/**
 * Clase de utilidades generales.
 */
public class Utils {
	
	/**
	 * Constructor privado para evitar la instanciación de la clase Utils.
	 */
	private Utils() {
	}
	
	/**
	 * Genera un ID único utilizando UUID.
	 * @return Un ID único como cadena.
	 */
	public static String createId() {
		return UUID.randomUUID().toString();
	}
}
