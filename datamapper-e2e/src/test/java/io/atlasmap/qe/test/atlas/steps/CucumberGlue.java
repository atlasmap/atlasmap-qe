package io.atlasmap.qe.test.atlas.steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.atlasmap.qe.test.MappingValidator;
import io.atlasmap.qe.test.atlas.AtlasmapPage;

/**
 * Created by mmelko on 02/11/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public abstract class CucumberGlue {

    protected static AtlasmapPage atlasmapPage = new AtlasmapPage();
    protected static MappingValidator validator = new MappingValidator();
    protected static boolean internalMapping = true;
    protected static final Logger LOG = LogManager.getLogger(CucumberGlue.class);
    public static boolean isMac = (System.getProperty("os.name").toLowerCase().indexOf("darwin") >=0);

    @BeforeClass
    public void beforeMethod() {
        System.setProperty("selenide.browser", "Chrome");
    }
}
