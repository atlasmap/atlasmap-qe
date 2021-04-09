package io.atlasmap.qe.test;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for mapping validator.
 *
 * @author Ondřej Kuhejda
 */
@Slf4j
public class MappingValidatorTest {

    @Test
    public void javaToJavaMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("javaToJava.json");
        mv.setTargetValue("targetCombineString", "sourceString sourceAnotherString 1");
        Assert.assertTrue(mv.verifyMapping());
    }

    @Test
    public void jsonToJavaMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("jsonToJava.json");
        mv.setTargetValue("targetString", "sourceJsonString");
        mv.setTargetValue("targetInteger", "10");
        mv.setTargetValue("targetDouble", "-50");
        mv.setTargetValue("targetFloat", "40.0");
        Assert.assertTrue(mv.verifyMapping());
    }

    @Test
    public void jsonToXmlMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("jsonToXml.json");
        String result = (String) mv.processMapping("targetXMLSchema");
        assertThat(result).contains("<targetXmlString>sourceJsonString</targetXmlString>");
        assertThat(result).contains("<targetXmlInteger>10</targetXmlInteger>");
        assertThat(result).contains("<targetXmlDouble>-50.0</targetXmlDouble>");
    }

    @Test
    public void xmlSchemaToJavaMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("xmlSchemaToJava.json");
        mv.setTargetValue("targetString", "XmlString");
        mv.setTargetValue("targetInteger", "300");
        mv.setTargetValue("targetDouble", "500");
        mv.setTargetValue("targetFloat", "100.1");
        Assert.assertTrue(mv.verifyMapping());
    }

    @Test
    public void xmlToJsonMapping() throws Exception {
        MappingValidator mv = new MappingValidator();
        mv.setMappingLocation("xmlToJson.json");
        String result = (String) mv.processMapping("targetJson.schema");
        assertThat(result).contains("\"targetJsonInteger\":300");
        assertThat(result).contains("\"targetJsonInteger\":300");
    }
}


