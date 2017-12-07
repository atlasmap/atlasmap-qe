package io.atlasmap.qe.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
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
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("javaToJavaMapping.xml");
        SourceMappingTestClass source = new SourceMappingTestClass();
      //  source.setSourceString("source string");
       // source.setSourceInteger(new Integer(1));
     //   source.setSourceDouble(2);
       // source.setSourceFloat(3);

        TargetMappingTestClass body = mv.processMapping(source);
        LOG.info(body.getTargetCombineString());
        LOG.info(body.getTargetAnotherString());
        LOG.info(source);
        LOG.info(body);
    }

 //   @Test
    public void testMappingValidator() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("javaToJavaMapping.xml");
        mv.map("sourceAnotherString","targetCombineString");
        Assert.assertTrue(mv.verifyMapping());
    }
}

