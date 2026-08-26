package incidencias.repositorio;

import incidencias.modelo.Incidencia;
import repositorio.Repositorio;
import repositorio.RepositorioMemoria;

/**
 * Repositorio en memoria para gestionar las incidencias reportadas en el sistema.
 */
public class RepositorioIncidenciasMemoria extends RepositorioMemoria<Incidencia>
		implements Repositorio<Incidencia, String> {

}
