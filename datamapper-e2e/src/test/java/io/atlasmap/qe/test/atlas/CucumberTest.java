package io.atlasmap.qe.test.atlas;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "io.atlasmap.qe.test.atlas.steps",
        plugin = {"io.atlasmap.qe.test.atlas.AtlasmapInit"},
        format = {"pretty", "junit:target/cucumber/junit.xml", "html:target/cucumber/html/"},
        tags = {"not @Ignore"})
public class CucumberTest {
}
