package io.syndesis.qe.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

;

/**
 * Created by mmelko on 30/10/2017.
 */
public class MappingTest {

    private String mappingPath;
    private static final Logger LOG = LogManager.getLogger(MappingTest.class);

    @Test
    public void testMapping() throws Exception {
        MappingValidator mv = new MappingValidator("combine.xml");
        MappingTestClass source = new MappingTestClass();
        source.setMappingString("source string");
        source.setIntNumber(new Integer(1));
        source.setDoubleNumber(2);
        source.setFloatNumber(3);

        MappingTestClass body = mv.processMapping(source);
        LOG.info(body.getCombineString());
        LOG.info(body.getTempString());
    }
}

