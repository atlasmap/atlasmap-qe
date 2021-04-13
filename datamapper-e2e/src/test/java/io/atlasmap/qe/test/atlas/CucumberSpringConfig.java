package io.atlasmap.qe.test.atlas;

import io.atlasmap.qe.test.ValidatorSpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberSpringConfig.class)
@ComponentScan(basePackageClasses = {ValidatorSpringConfig.class, CucumberTest.class})
public class CucumberSpringConfig {
}
