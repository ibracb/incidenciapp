package repositorio;

import java.util.List;

/**
 *  Repositorio para entidades gestionadas con identificador.
 *  El parámetro T representa al tipo de datos de la entidad.
 *  El parámetro K es el tipo del identificador.
 */
public interface Repositorio <T, K> {
    
	/**
	 * Agrega una nueva entidad al repositorio y devuelve su identificador.
	 * @param entity La entidad a agregar.
	 * @return El identificador de la entidad agregada.
	 * @throws RepositorioException Si ocurre un error al agregar la entidad al repositorio.
	 */
    K add(T entity) throws RepositorioException;
    
    /**
     * Actualiza una entidad existente en el repositorio.
     * @param entity La entidad con los datos actualizados. Se asume que la entidad tiene un identificador válido.
     * @throws RepositorioException Si ocurre un error al actualizar la entidad en el repositorio.
     * @throws EntidadNoEncontrada Si la entidad a actualizar no se encuentra en el repositorio.
     */
    void update(T entity) throws RepositorioException, EntidadNoEncontrada;
    
    /**
	 * Elimina una entidad del repositorio.
	 * @param entity La entidad a eliminar. Se asume que la entidad tiene un identificador válido.
	 * @throws RepositorioException Si ocurre un error al eliminar la entidad del repositorio.
	 * @throws EntidadNoEncontrada Si la entidad a eliminar no se encuentra en el repositorio.
	 */
    void delete(T entity) throws RepositorioException, EntidadNoEncontrada;

    /**
	 * Obtiene una entidad por su identificador.
	 * @param id El identificador de la entidad a obtener.
	 * @return La entidad correspondiente al identificador proporcionado.
	 * @throws RepositorioException Si ocurre un error al obtener la entidad del repositorio.
	 * @throws EntidadNoEncontrada Si no se encuentra una entidad con el identificador proporcionado en el repositorio.
	 */
    T getById(K id) throws RepositorioException, EntidadNoEncontrada;
    
    /**
     * Obtiene todas las entidades almacenadas en el repositorio.
     * @return Una lista con todas las entidades del repositorio.
     * @throws RepositorioException Si ocurre un error al obtener las entidades del repositorio.
     */
	List<T> getAll() throws RepositorioException;

	/**
	 * Obtiene una lista de los identificadores de todas las entidades almacenadas en el repositorio.
	 * @return Una lista con los identificadores de todas las entidades del repositorio.
	 * @throws RepositorioException Si ocurre un error al obtener los identificadores del repositorio.
	 */
	List<K> getIds()throws RepositorioException;
}
