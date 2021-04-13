package io.atlasmap.qe.test.atlas;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import io.atlasmap.qe.test.atlas.utils.TestConfiguration;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
public class CucumberTest {

    @BeforeClass
    public static void setupCucumber() {
        //set up Selenide
        Configuration.timeout = TestConfiguration.getConfigTimeout() * 1000L;
        //We will now use custom web driver
        //Configuration.browser = TestConfiguration.syndesisBrowser();
        Configuration.browser = CustomWebDriverProvider.class.getName();
        Configuration.browserSize = "1920x1080";
        Selenide.open(TestConfiguration.getUiIndexPath());
        //Logging selectors is disabled by default, enable it in test properties if you wish
    }
}
