package io.atlasmap.qe.test.atlas;

import java.io.InputStream;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.atlasmap.api.AtlasException;
import io.atlasmap.java.service.JavaService;
import io.atlasmap.java.v2.MavenClasspathResponse;
import io.atlasmap.json.service.JsonService;
import io.atlasmap.service.AtlasService;
import io.atlasmap.xml.service.XmlService;
import io.swagger.annotations.Api;


@Api
@Component
@ApplicationPath("/v2/atlas/")
public class JaxrsApplication extends Application {

    @Bean
    public JavaService javaService() {
        return new JavaServiceEmptyClasspath();
    }

    @Bean
    public JsonService jsonService() {
        return new JsonService();
    }

    @Bean
    public XmlService xmlService() {
        return new XmlService();
    }

    @Bean
    public AtlasService atlasService() throws AtlasException {
        return new AtlasService();
    }

    // =====================================================================

    public static class JavaServiceEmptyClasspath extends JavaService {

        /**
         * Stub out mavenclasspath processing for now.
         *
         * @param request request body
         * @return {@link MavenClasspathResponse} with empty classpath
         */
        @Override
        @POST
        @Consumes({ MediaType.APPLICATION_JSON })
        @Produces({ MediaType.APPLICATION_JSON })
        @Path("/mavenclasspath")
        public Response generateClasspath(InputStream request) {
            MavenClasspathResponse response = new MavenClasspathResponse();
            response.setExecutionTime(0L);
            response.setClasspath("");
            return Response.ok().entity(toJson(response)).build();
        }
    }

}
