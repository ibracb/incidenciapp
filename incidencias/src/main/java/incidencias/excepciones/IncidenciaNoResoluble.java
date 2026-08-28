package incidencias.excepciones;

@SuppressWarnings("serial")
public class IncidenciaNoResoluble extends RuntimeException {
	
	public IncidenciaNoResoluble(String mensaje) {
		super(mensaje);
	}

}
