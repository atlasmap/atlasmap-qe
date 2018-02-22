package io.atlasmap.qe.test.atlas;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "io.atlasmap.qe.test.atlas.steps", format = {"pretty", "html:target/cucumber"}, tags = {"not @Ignore"})
public class CucumberTest {
}
