package io.atlasmap.qe.test.atlas.steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.atlasmap.qe.test.MappingValidator;
import io.atlasmap.qe.test.atlas.AtlasmapPage;

/**
 * Created by mmelko on 02/11/2017.
 */

public abstract class CucumberGlue {

    protected static AtlasmapPage atlasmapPage = new AtlasmapPage();
    protected static MappingValidator validator = new MappingValidator();
    protected static boolean internalMapping = true;
    protected static final Logger LOG = LogManager.getLogger(CucumberGlue.class);
    public static boolean isMac = (System.getProperty("os.name").toLowerCase().indexOf("mac") >=0);

}
