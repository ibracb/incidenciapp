package incidencias.repositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import incidencias.modelo.EstadoIncidencia;
import incidencias.modelo.Incidencia;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

/**
 * Implementación MongoDB del repositorio de incidencias con métodos ad-hoc.
 */
@Singleton(name = "RepositorioIncidencias")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Startup
@Lock(LockType.READ)
public class RepositorioIncidenciasAdHocMongoDB extends RepositorioIncidenciasMongoDB
		implements RepositorioIncidenciasAdHoc {
	
	
	public RepositorioIncidenciasAdHocMongoDB() throws IOException {
		super();
	}
	
	@Lock(LockType.WRITE)
	@Override
	public String add(Incidencia incidencia) throws RepositorioException {
		return super.add(incidencia);
	}
	
	@Lock(LockType.WRITE)
	@Override
	public void update(Incidencia incidencia) throws RepositorioException, EntidadNoEncontrada {
		super.update(incidencia);
	}
	
	@Lock(LockType.WRITE)
	@Override
	public void delete(Incidencia incidencia) throws RepositorioException, EntidadNoEncontrada {
		super.delete(incidencia);
	}
	
	@Override
	public List<Incidencia> findByEstado(EstadoIncidencia estado) throws RepositorioException {
		try {
			Bson filtro = estado != null ? Filters.eq("estado", estado.name()) : new Document();
			Bson sort = Sorts.descending("fecha");
			return getCollection().find(filtro).sort(sort).into(new ArrayList<>());
		} catch (MongoException ex) {
			throw new RepositorioException("error findByEstado", ex);
		}
	}

}
