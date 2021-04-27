package io.atlasmap.qe.mapper;

import io.atlasmap.qe.data.DatesObject;
import io.atlasmap.qe.data.SimpleEnum;
import io.atlasmap.qe.data.SmallMappingTestClass;
import io.atlasmap.qe.data.source.SourceListsClass;
import io.atlasmap.qe.data.source.SourceNestedCollectionClass;
import io.atlasmap.qe.data.target.TargetMappingTestClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests for mapping validator.
 *
 * @author Ondřej Kuhejda
 */
@Slf4j
@ContextConfiguration(classes = ValidatorSpringConfig.class)
public class MappingValidatorTest extends AbstractTestNGSpringContextTests {

    private MappingValidator mappingValidator;
    private HashMap<String, Object> resourceMap;

    @Inject
    public void initializeResources(MappingValidator mappingValidator) throws IOException {
        this.mappingValidator = mappingValidator;

        resourceMap = new HashMap<>();

        resourceMap.put(SourceListsClass.class.getName(), new SourceListsClass());
        resourceMap.put(SmallMappingTestClass.class.getName(), new SmallMappingTestClass());
        resourceMap.put(SourceNestedCollectionClass.class.getName(), new SourceNestedCollectionClass());
        resourceMap.put(DatesObject.class.getName(), new DatesObject("22-12-2012"));

        String documentsFolderPath = System.getProperty("user.dir") + "/../test-resources/src/main/resources/documents/";

        resourceMap.put("sourceJson.schema", new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceJsonSchemaInstance.json"))));
        resourceMap.put("sourceArrays", new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceArrays.json"))));
        resourceMap.put("sourceXMLSchema", new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceXMLSchemaInstance.xml"))));
        resourceMap.put("sourceJsonArray", new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceJsonArray.json"))));

        mappingValidator.initializeValues(new HashMap<>(resourceMap));
    }

    @Test
    public void javaToJavaMapping() {
        System.out.println(resourceMap.get("sourceXMLSchema"));
        mappingValidator.initValidator(new HashMap<>(resourceMap));
        mappingValidator.setMappingLocation("javaToJava.json");
        mappingValidator.setTargetValue("targetCombineString", "sourceString sourceAnotherString 1");
        assertThat(mappingValidator.verifyMapping()).isTrue();
    }

    @Test
    public void jsonToJavaMapping() {
        mappingValidator.initValidator(new HashMap<>(resourceMap));
        mappingValidator.setMappingLocation("jsonToJava.json");
        mappingValidator.setTargetValue("targetString", "sourceJsonString");
        mappingValidator.setTargetValue("targetInteger", "10");
        mappingValidator.setTargetValue("targetDouble", "-50");
        mappingValidator.setTargetValue("targetFloat", "40.0");
        assertThat(mappingValidator.verifyMapping()).isTrue();
    }

    @Test
    public void jsonToXmlMapping() {
        mappingValidator.initValidator(new HashMap<>(resourceMap));
        mappingValidator.setMappingLocation("jsonToXml.json");
        String result = (String) mappingValidator.processMapping("targetXMLSchema");
        assertThat(result).contains("<targetXmlString>sourceJsonString</targetXmlString>");
        assertThat(result).contains("<targetXmlInteger>10</targetXmlInteger>");
        assertThat(result).contains("<targetXmlDouble>-50.0</targetXmlDouble>");
    }

    @Test
    public void xmlSchemaToJavaMapping() {
        mappingValidator.initValidator(new HashMap<>(resourceMap));
        mappingValidator.setMappingLocation("xmlSchemaToJava.json");
        mappingValidator.setTargetValue("targetString", "XmlString");
        mappingValidator.setTargetValue("targetInteger", "300");
        mappingValidator.setTargetValue("targetDouble", "500");
        mappingValidator.setTargetValue("targetFloat", "100.1");
        assertThat(mappingValidator.verifyMapping()).isTrue();
    }

    @Test
    public void xmlToJsonMapping() {
        mappingValidator.initValidator(new HashMap<>(resourceMap));
        mappingValidator.setMappingLocation("xmlToJson.json");
        String result = (String) mappingValidator.processMapping("targetJson.schema");
        assertThat(result).contains("\"targetJsonInteger\":300");
        assertThat(result).contains("\"targetJsonInteger\":300");
    }

    @Test
    public void setValuesOfBeanProperties() {
        mappingValidator.initValidator(new HashMap<>(resourceMap));
        TargetMappingTestClass bean = new TargetMappingTestClass();

        mappingValidator.setValueOfBeanProperty(bean, "targetString", "targetString");
        mappingValidator.setValueOfBeanProperty(bean, "targetCombineString", "targetCombineString");
        mappingValidator.setValueOfBeanProperty(bean, "targetInteger", 5);
        mappingValidator.setValueOfBeanProperty(bean, "targetLong", 4L);
        mappingValidator.setValueOfBeanProperty(bean, "targetFloat", 3f);
        mappingValidator.setValueOfBeanProperty(bean, "targetDouble", 2d);
        mappingValidator.setValueOfBeanProperty(bean, "targetDate", new Date(0));
        mappingValidator.setValueOfBeanProperty(bean, "targetAnotherString", "targetAnotherString");
        mappingValidator.setValueOfBeanProperty(bean, "targetBoolean", false);
        mappingValidator.setValueOfBeanProperty(bean, "targetShort", 1);
        mappingValidator.setValueOfBeanProperty(bean, "targetByte", Byte.MIN_VALUE);
        mappingValidator.setValueOfBeanProperty(bean, "targetChar", 'x');
        mappingValidator.setValueOfBeanProperty(bean, "targetBigInteger", new BigInteger("0"));
        mappingValidator.setValueOfBeanProperty(bean, "targetBigDecimal", new BigDecimal("0"));
        mappingValidator.setValueOfBeanProperty(bean, "targetSmallMappingTestClass", new SmallMappingTestClass());
        mappingValidator.setValueOfBeanProperty(bean, "dateObjectVariable", new DatesObject("01-01-1989"));
        mappingValidator.setValueOfBeanProperty(bean, "targetEnum", SimpleEnum.VALUE2);

        TargetMappingTestClass targetMappingTestClass = new TargetMappingTestClass();
        assertThat(bean).isEqualTo(targetMappingTestClass);

        mappingValidator.setValueOfBeanProperty(bean, "targetInteger", "5");
        mappingValidator.setValueOfBeanProperty(bean, "targetLong", "4");
        mappingValidator.setValueOfBeanProperty(bean, "targetFloat", "3");
        mappingValidator.setValueOfBeanProperty(bean, "targetDouble", "2");
        mappingValidator.setValueOfBeanProperty(bean, "targetDate", "1970-01-01-00");
        mappingValidator.setValueOfBeanProperty(bean, "targetBoolean", "false");
        mappingValidator.setValueOfBeanProperty(bean, "targetShort", "1");
        mappingValidator.setValueOfBeanProperty(bean, "targetByte", "-128");
        mappingValidator.setValueOfBeanProperty(bean, "targetChar", "x");
        mappingValidator.setValueOfBeanProperty(bean, "targetBigInteger", "0");
        mappingValidator.setValueOfBeanProperty(bean, "targetBigDecimal", "0");
        mappingValidator.setValueOfBeanProperty(bean, "targetEnum", "VALUE2");

        assertThat(bean).isEqualTo(targetMappingTestClass);

        mappingValidator.setValueOfBeanProperty(bean, "targetDate", 0);
        mappingValidator.setValueOfBeanProperty(bean, "targetBoolean", new BigDecimal("0"));

        assertThat(bean).isEqualTo(targetMappingTestClass);

        mappingValidator.setValueOfBeanProperty(bean, "targetInteger", '\005');
        mappingValidator.setValueOfBeanProperty(bean, "targetLong", '\004');
        mappingValidator.setValueOfBeanProperty(bean, "targetFloat", '\003');
        mappingValidator.setValueOfBeanProperty(bean, "targetDouble", '\002');
        mappingValidator.setValueOfBeanProperty(bean, "targetBoolean", '\000');
        mappingValidator.setValueOfBeanProperty(bean, "targetShort", '\001');
        mappingValidator.setValueOfBeanProperty(bean, "targetBigInteger", '\000');
        mappingValidator.setValueOfBeanProperty(bean, "targetBigDecimal", '\000');

        assertThat(bean).isEqualTo(targetMappingTestClass);
    }

    @Test
    public void dateObjectsMapping() {
        mappingValidator.initValidator(new HashMap<>(resourceMap));

        DatesObject sourceDate = new DatesObject("22-12-2012");
        mappingValidator.addSource(sourceDate.getClass().getName(), sourceDate);

        mappingValidator.setMappingLocation("fromDateObjectToDateObject.json");

        TargetMappingTestClass targetMappingTestClass = (TargetMappingTestClass) mappingValidator.processMapping(TargetMappingTestClass.class.getName());
        DatesObject dateObjectVariable = targetMappingTestClass.getDateObjectVariable();

        assertThat(sourceDate.getStandardJavaDate()).isEqualTo(dateObjectVariable.getStandardJavaDate());
        assertThat(sourceDate.getCalendar().getTimeInMillis()).isEqualTo(dateObjectVariable.getCalendar().getTimeInMillis());
        assertThat(sourceDate.getLocalDate()).isEqualTo(dateObjectVariable.getLocalDate());
        assertThat(sourceDate.getLocalDateTime()).isEqualTo(dateObjectVariable.getLocalDateTime());
        assertThat(sourceDate.getLocalTime()).isEqualTo(dateObjectVariable.getLocalTime());
        assertThat(sourceDate.getTime()).isEqualTo(dateObjectVariable.getTime());
        assertThat(sourceDate.getTimestamp()).isEqualTo(dateObjectVariable.getTimestamp());
        assertThat(sourceDate.getZonedDateTime()).isEqualTo(dateObjectVariable.getZonedDateTime());
        assertThat(sourceDate.getSqlDate().toString()).isEqualTo(dateObjectVariable.getSqlDate().toString());
        assertThat(sourceDate.getGregorianCalendar()).isEqualTo(dateObjectVariable.getGregorianCalendar());
    }
}


