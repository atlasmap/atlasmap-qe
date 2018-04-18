package io.atlasmap.qe.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mmelko on 30/10/2017.
 */
public class MappingTest {

    private String mappingPath;
    private static final Logger LOG = LogManager.getLogger(MappingTest.class);

    // @Test
    public void testMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("fromInteger.xml");
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

    //    @Test
    public void testMappingValidator() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("javaToJavaMapping.xml");
        mv.map("sourceAnotherString", "targetCombineString");
        Assert.assertTrue(mv.verifyMapping());
    }

    @Test
    public void testJSonToJavaMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        Map<String, Object> input = new HashMap<>();
        mv.setMappingLocation("jsonToJava.xml");
        String json = "{\"sourceJsonInteger\": 10000}";
        input.put("sourceJSON", json);
        mv.addSource("sourceJSON", json);
        //  Map<String,Object> output = mv.processMappingInputMap(input);
        TargetMappingTestClass body = (TargetMappingTestClass) mv.processMapping(TargetMappingTestClass.class.getName());
        Assert.assertEquals(10000, body.getTargetInteger());
    }
}

