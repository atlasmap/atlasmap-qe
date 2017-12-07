package io.atlasmap.qe.test.atlas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by mmelko on 02/11/2017.
 */

@SpringBootApplication
@ComponentScan(basePackageClasses = {AtlasRuntime.class,})
public class AtlasRuntime extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AtlasRuntime.class, args);
    }

}
