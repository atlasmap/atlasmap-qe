package io.syndesis.qe.test.atlas.steps;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.syndesis.qe.test.MappingValidator;
import io.syndesis.qe.test.atlas.AtlasRuntime;
import io.syndesis.qe.test.atlas.AtlasmapPage;

/**
 * Created by mmelko on 02/11/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtlasRuntime.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public abstract class CucumberGlue {

    protected static AtlasmapPage atlasmapPage = new AtlasmapPage();
    protected static MappingValidator validator = new MappingValidator();

    @BeforeClass
    public void beforeMethod() {
        System.setProperty("webdriver.chrome.driver", "/Users/mmelko/Downloads/chromedriver");
        System.setProperty("selenide.browser", "Chrome");
    }
}
