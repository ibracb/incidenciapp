package incidencias.excepciones;

@SuppressWarnings("serial")
public class IncidenciaNoAsignable extends RuntimeException {
	
	public IncidenciaNoAsignable(String mensaje) {
		super(mensaje);
	}

}
