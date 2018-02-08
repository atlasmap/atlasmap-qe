package io.atlasmap.qe.test.atlas;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("cors")
public class CorsConfiguration {

    private List<String> allowedOrigins = Arrays.asList(org.springframework.web.cors.CorsConfiguration.ALL);

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(request -> {

            // in case you want to allow access to some resource From any origin:
//            String pathInfo = request.getPathInfo();
//            if (pathInfo != null &&
//                (pathInfo.endsWith("/swagger.json") ||
//                 pathInfo.endsWith("/swagger.yaml"))) {
//                return new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues();
//            }

            org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedOrigins(allowedOrigins);
            config.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
            config.applyPermitDefaultValues();
            return config;
        });
    }

}
