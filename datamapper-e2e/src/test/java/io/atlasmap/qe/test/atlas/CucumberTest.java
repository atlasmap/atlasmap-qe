package io.atlasmap.qe.test.atlas;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.atlasmap.qe.test.atlas.utils.TestConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "io.atlasmap.qe.test.atlas.steps",
        plugin = {"io.atlasmap.qe.test.atlas.AtlasmapInit",
                "pretty",
                "html:target/cucumber/cucumber-html",
                "junit:target/cucumber/cucumber-junit.xml",
                "json:target/cucumber/cucumber-report.json",
                "io.atlasmap.qe.test.atlas.utils.MailFormatter:target/cucumber/cucumber-mail/"
        },
        tags = {"not @Ignore"})
public class CucumberTest {

        @BeforeClass
        public static void setupCucumber() {
                //set up Selenide
                Configuration.timeout = TestConfiguration.getConfigTimeout() * 1000;
                Configuration.collectionsTimeout = Configuration.timeout;
                //We will now use custom web driver
                //Configuration.browser = TestConfiguration.syndesisBrowser();
                Configuration.browser = "io.atlasmap.qe.test.atlas.CustomWebDriverProvider";
                Configuration.browserSize = "1920x1080";
                Selenide.open(TestConfiguration.getUiIndexPath());
                //Logging selectors is disabled by default, enable it in test properties if you wish
        }
}
