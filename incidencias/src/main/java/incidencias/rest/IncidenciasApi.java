package incidencias.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import incidencias.rest.dto.in.AsignarTecnicoIncidenciaDto;
import incidencias.rest.dto.in.RegistrarIncidenciaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@Tag(name = "Incidencias", description = "Gestión de incidencias en IncidenciApp (registro, consulta, asignación y resolución)")
public interface IncidenciasApi {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Registrar una incidencia", description = "Crea una incidencia con descripción y ubicación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Incidencia creada. URL en cabecera Location."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    Response registrarIncidencia(@RequestBody(description = "Datos de la incidencia", required = true) RegistrarIncidenciaDto dto)
            throws RepositorioException;

    @PATCH
    @Path("/{id}/asignar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Asignar un técnico", description = "Asigna nombre y teléfono del técnico a una incidencia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asignación correcta."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Incidencia no encontrada."),
            @ApiResponse(responseCode = "409", description = "Incidencia no asignable (ya asignada, o resuelta)."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    Response asignarTecnicoIncidencia(@PathParam("id") String idIncidencia,
                               @RequestBody(description = "Datos del técnico", required = true) AsignarTecnicoIncidenciaDto dto)
            throws RepositorioException, EntidadNoEncontrada;

    @PATCH
    @Path("/{id}/resolver")
    @Operation(summary = "Resolver una incidencia", description = "Marca la incidencia como resuelta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resuelta correctamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Incidencia no encontrada."),
            @ApiResponse(responseCode = "409", description = "Incidencia no resoluble (ya resuelta, o pendiente)."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    Response resolverIncidencia(@PathParam("id") String idIncidencia)
            throws RepositorioException, EntidadNoEncontrada;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar incidencias", description = "Incidencias filtradas por estado, ordenadas por fecha descendente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de incidencias."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    Response consultarIncidencias(@QueryParam("estado") String estado) throws RepositorioException;
    
}
