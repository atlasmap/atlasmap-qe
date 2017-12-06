package io.syndesis.qe.test.atlas;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "io.syndesis.qe.test.atlas.steps", format = {"pretty"})
public class CucumberTest {
}
