package incidencias.repositorio;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import incidencias.modelo.Incidencia;
import repositorio.Repositorio;
import repositorio.RepositorioMemoria;

/**
 * Repositorio en memoria para gestionar las incidencias reportadas en el sistema.
 */
@Singleton(name = "RepositorioIncidencias")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Startup
@Lock(LockType.READ)
public class RepositorioIncidenciasMemoria extends RepositorioMemoria<Incidencia>
		implements Repositorio<Incidencia, String> {

}
