package incidencias.repositorio;

import javax.annotation.PostConstruct;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import incidencias.modelo.Incidencia;
import repositorio.Repositorio;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

/**
 * Repositorio MondoDB para gestionar las incidencias reportadas en el sistema.
 */
public class RepositorioIncidenciasMongoDB extends RepositorioMongoDB<Incidencia>
		implements Repositorio<Incidencia, String> {
	
	private MongoCollection<Incidencia> incidencias;
	
	@PostConstruct
	public void init() {
		PropertiesReader properties;
		try {
			properties = new PropertiesReader("mongo.properties");
			String connectionString = properties.getProperty("mongouri");
			MongoClient mongoClient = MongoClients.create(connectionString);
			String mongoDatabase = properties.getProperty("mongodatabase");
			MongoDatabase database = mongoClient.getDatabase(mongoDatabase);
			CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(
					MongoClientSettings.getDefaultCodecRegistry(),
					CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
			incidencias = database.getCollection("incidencias", Incidencia.class).withCodecRegistry(defaultCodecRegistry);

		} catch (Exception e) {

		}
	}
	
	@Override
	public MongoCollection<Incidencia> getCollection() {
		return incidencias;
	}

}
