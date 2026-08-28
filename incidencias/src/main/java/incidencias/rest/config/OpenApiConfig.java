package incidencias.rest.config;

import java.io.InputStream;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import incidencias.rest.ControladorIncidencias;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.jaxrs2.Reader;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;

@Path("/")
@OpenAPIDefinition(info = @Info(
    title = "IncidenciApp REST API",
    description = "API REST para la gestión de incidencias",
    version = "1.0.0"))
public class OpenApiConfig {

    private static final Set<Class<?>> RECURSOS = Set.of(ControladorIncidencias.class);

    private static final OpenAPI OPENAPI = initOpenApi();

    private static OpenAPI initOpenApi() {
        SwaggerConfiguration config = new SwaggerConfiguration()
                .openAPI(new OpenAPI()
                        .info(new io.swagger.v3.oas.models.info.Info()
                                .title("IncidenciApp REST API")
                                .description("API REST para la gestión de incidencias")
                                .version("1.0.0")))
                .prettyPrint(true);
        return new Reader(config).read(RECURSOS);
    }

    @GET
    @Path("openapi.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOpenApiJson() {
        return Response.ok(Json.pretty(OPENAPI)).build();
    }

    @GET
    @Path("swagger-ui")
    @Produces(MediaType.TEXT_HTML)
    public Response swaggerUi() {
        InputStream html = getClass().getClassLoader()
                .getResourceAsStream("swagger-ui/index.html");
        if (html == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(html).build();
    }
}
