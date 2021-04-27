package io.atlasmap.qe.test;

import io.atlasmap.qe.mapper.ValidatorSpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberSpringConfig.class)
@ComponentScan(basePackageClasses = {ValidatorSpringConfig.class, CucumberTest.class})
public class CucumberSpringConfig {
}
