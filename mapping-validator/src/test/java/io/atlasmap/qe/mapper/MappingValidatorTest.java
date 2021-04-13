package io.atlasmap.qe.mapper;

import io.atlasmap.qe.data.DatesObject;
import io.atlasmap.qe.data.SimpleEnum;
import io.atlasmap.qe.data.SmallMappingTestClass;
import io.atlasmap.qe.data.target.TargetMappingTestClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests for mapping validator.
 *
 * @author Ondřej Kuhejda
 */
@Slf4j
@ContextConfiguration(classes = ValidatorSpringConfig.class)
public class MappingValidatorTest extends AbstractTestNGSpringContextTests {

    @Inject
    MappingValidator mv;

    @Test
    public void javaToJavaMapping() {
        mv.initValidator();
        mv.setMappingLocation("javaToJava.json");
        mv.setTargetValue("targetCombineString", "sourceString sourceAnotherString 1");
        assertThat(mv.verifyMapping()).isTrue();
    }

    @Test
    public void jsonToJavaMapping() {
        mv.initValidator();
        mv.setMappingLocation("jsonToJava.json");
        mv.setTargetValue("targetString", "sourceJsonString");
        mv.setTargetValue("targetInteger", "10");
        mv.setTargetValue("targetDouble", "-50");
        mv.setTargetValue("targetFloat", "40.0");
        assertThat(mv.verifyMapping()).isTrue();
    }

    @Test
    public void jsonToXmlMapping() {
        mv.initValidator();
        mv.setMappingLocation("jsonToXml.json");
        String result = (String) mv.processMapping("targetXMLSchema");
        assertThat(result).contains("<targetXmlString>sourceJsonString</targetXmlString>");
        assertThat(result).contains("<targetXmlInteger>10</targetXmlInteger>");
        assertThat(result).contains("<targetXmlDouble>-50.0</targetXmlDouble>");
    }

    @Test
    public void xmlSchemaToJavaMapping() {
        mv.initValidator();
        mv.setMappingLocation("xmlSchemaToJava.json");
        mv.setTargetValue("targetString", "XmlString");
        mv.setTargetValue("targetInteger", "300");
        mv.setTargetValue("targetDouble", "500");
        mv.setTargetValue("targetFloat", "100.1");
        assertThat(mv.verifyMapping()).isTrue();
    }

    @Test
    public void xmlToJsonMapping() {
        mv.initValidator();
        mv.setMappingLocation("xmlToJson.json");
        String result = (String) mv.processMapping("targetJson.schema");
        assertThat(result).contains("\"targetJsonInteger\":300");
        assertThat(result).contains("\"targetJsonInteger\":300");
    }

    @Test
    public void setValuesOfBeanProperties() {
        mv.initValidator();
        TargetMappingTestClass bean = new TargetMappingTestClass();

        mv.setValueOfBeanProperty(bean, "targetString", "targetString");
        mv.setValueOfBeanProperty(bean, "targetCombineString", "targetCombineString");
        mv.setValueOfBeanProperty(bean, "targetInteger", 5);
        mv.setValueOfBeanProperty(bean, "targetLong", 4L);
        mv.setValueOfBeanProperty(bean, "targetFloat", 3f);
        mv.setValueOfBeanProperty(bean, "targetDouble", 2d);
        mv.setValueOfBeanProperty(bean, "targetDate", new Date(0));
        mv.setValueOfBeanProperty(bean, "targetAnotherString", "targetAnotherString");
        mv.setValueOfBeanProperty(bean, "targetBoolean", false);
        mv.setValueOfBeanProperty(bean, "targetShort", 1);
        mv.setValueOfBeanProperty(bean, "targetByte", Byte.MIN_VALUE);
        mv.setValueOfBeanProperty(bean, "targetChar", 'x');
        mv.setValueOfBeanProperty(bean, "targetBigInteger", new BigInteger("0"));
        mv.setValueOfBeanProperty(bean, "targetBigDecimal", new BigDecimal("0"));
        mv.setValueOfBeanProperty(bean, "targetSmallMappingTestClass", new SmallMappingTestClass());
        mv.setValueOfBeanProperty(bean, "dateObjectVariable", new DatesObject("01-01-1989"));
        mv.setValueOfBeanProperty(bean, "targetEnum", SimpleEnum.VALUE2);

        TargetMappingTestClass targetMappingTestClass = new TargetMappingTestClass();
        assertThat(bean).isEqualTo(targetMappingTestClass);

        mv.setValueOfBeanProperty(bean, "targetInteger", "5");
        mv.setValueOfBeanProperty(bean, "targetLong", "4");
        mv.setValueOfBeanProperty(bean, "targetFloat", "3");
        mv.setValueOfBeanProperty(bean, "targetDouble", "2");
        mv.setValueOfBeanProperty(bean, "targetDate", "1970-01-01-00");
        mv.setValueOfBeanProperty(bean, "targetBoolean", "false");
        mv.setValueOfBeanProperty(bean, "targetShort", "1");
        mv.setValueOfBeanProperty(bean, "targetByte", "-128");
        mv.setValueOfBeanProperty(bean, "targetChar", "x");
        mv.setValueOfBeanProperty(bean, "targetBigInteger", "0");
        mv.setValueOfBeanProperty(bean, "targetBigDecimal", "0");
        mv.setValueOfBeanProperty(bean, "targetEnum", "VALUE2");

        assertThat(bean).isEqualTo(targetMappingTestClass);

        mv.setValueOfBeanProperty(bean, "targetDate", 0);
        mv.setValueOfBeanProperty(bean, "targetBoolean", new BigDecimal("0"));

        assertThat(bean).isEqualTo(targetMappingTestClass);

        mv.setValueOfBeanProperty(bean, "targetInteger", '\005');
        mv.setValueOfBeanProperty(bean, "targetLong", '\004');
        mv.setValueOfBeanProperty(bean, "targetFloat", '\003');
        mv.setValueOfBeanProperty(bean, "targetDouble", '\002');
        mv.setValueOfBeanProperty(bean, "targetBoolean", '\000');
        mv.setValueOfBeanProperty(bean, "targetShort", '\001');
        mv.setValueOfBeanProperty(bean, "targetBigInteger", '\000');
        mv.setValueOfBeanProperty(bean, "targetBigDecimal", '\000');

        assertThat(bean).isEqualTo(targetMappingTestClass);
    }

    @Test
    public void dateObjectsMapping() {
        mv.initValidator();

        DatesObject sourceDate = new DatesObject("22-12-2012");
        mv.addSource(sourceDate.getClass().getName(), sourceDate);

        mv.setMappingLocation("fromDateObjectToDateObject.json");

        TargetMappingTestClass targetMappingTestClass = (TargetMappingTestClass) mv.processMapping(TargetMappingTestClass.class.getName());
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


