package io.atlasmap.qe.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import io.atlasmap.qe.resources.ResourcesGenerator;

/**
 * Created by mmelko on 30/10/2017.
 */
public class MappingTest {

    private String mappingPath;
    private static final Logger LOG = LogManager.getLogger(MappingTest.class);

    @Test
    public void testMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("fromInteger.xml");
        SourceMappingTestClass source = new SourceMappingTestClass();
        source.setSourceString("source string");
        source.setSourceInteger(new Integer(1));
        source.setSourceDouble(2);
        source.setSourceFloat(3);
        mv.addSource(source.getClass().getName(),source);
        TargetMappingTestClass body = mv.processMapping(source);

        Assert.assertEquals("1",body.getTargetString());
        Assert.assertEquals(1,body.getTargetInteger());
        Assert.assertEquals(1,body.getTargetLong());
        Assert.assertEquals(1.0,body.getTargetFloat(),0);
        Assert.assertEquals(1.0,body.getTargetDouble(),0);
    }

    @Ignore
    @Test
    public void testJSonToJavaMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        Map<String, Object> input = new HashMap<>();
        mv.setMappingLocation("jsonToJava.xml");
        String json = "{\"sourceJsonInteger\": 10000}";
        input.put("sourceJSON", json);
        mv.addSource("sourceJSON", json);
        TargetMappingTestClass body = (TargetMappingTestClass) mv.processMapping(TargetMappingTestClass.class.getName());
        Assert.assertEquals(10000, body.getTargetInteger());
    }

    @Ignore
    @Test
    public void testXmlToJavaMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("flatXmlToJava.xml");
        TargetMappingTestClass body = (TargetMappingTestClass) mv.processMapping(TargetMappingTestClass.class.getName());
        Assert.assertEquals(300, body.getTargetInteger());
    }

    @Ignore
    @Test
    public void testXmlSchemaToJavaMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("xmlSchemaToJava.xml");
        mv.addSource("sourceXmlSchema", ResourcesGenerator.getXmlSchemaInstance(null));
        TargetMappingTestClass body = (TargetMappingTestClass) mv.processMapping(TargetMappingTestClass.class.getName());
        Assert.assertEquals(300, body.getTargetInteger());
    }

    @Ignore
    @Test
    public void testXmlSchemaToJsonMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("xmlSchemaToJson.xml");
        mv.addSource("sourceXmlSchema", ResourcesGenerator.getXmlSchemaInstance(null));
        String body = (String) mv.processMapping("targetJson");
        Assert.assertEquals("{\"targetJsonDouble\":100.1}", body);
    }


}


