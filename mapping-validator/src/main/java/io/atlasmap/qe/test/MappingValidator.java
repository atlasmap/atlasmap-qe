package io.atlasmap.qe.test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.atlasmap.AtlasComponent;
import org.apache.camel.component.atlasmap.AtlasConstants;
import org.apache.camel.component.mock.MockEndpoint;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Assert;

import io.atlasmap.qe.resources.ResourcesGenerator;

/**
 * Created by mmelko on 15/11/2017.
 */
public class MappingValidator {
    private static final Logger LOG = LogManager.getLogger(MappingValidator.class);

    private String mappingLocation;
    private SourceMappingTestClass source;
    private TargetMappingTestClass target;
    private Map<String, Object> expectedMap;
    private Map<String, Object> sourceMap;
    public MappingValidator() {
        source = new SourceMappingTestClass();
        target = new TargetMappingTestClass();
        expectedMap = new HashMap<>();
        sourceMap = new HashMap<>();
        try {
            initValidator();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public TargetMappingTestClass processMapping(SourceMappingTestClass input) throws Exception {
        sourceMap = new HashMap<>();
        sourceMap.put(input.getClass().getName(), input);
        Map<String, Object> targetMap = processMappingInputMap(sourceMap);
        return (TargetMappingTestClass) targetMap.get("io.atlasmap.qe.test.TargetMappingTestClass");
    }

    public TargetMappingTestClass processMapping() throws Exception {
        return processMapping(this.source);
    }

    public Object processSingleObjectMapping(Object input, String expected) throws Exception {
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put(input.getClass().getName(), input);
        Map<String, Object> processed = processMappingInputMap(sourceMap);

        return processed.get(expected);
    }

    public Object processMapping(String expected) throws Exception {
        sourceMap.put(source.getClass().getName(), source);
        Map<String, Object> processed = processMappingInputMap(sourceMap);
        return processed.get(expected);
    }

    public Map<String, Object> processMappingInputMap(Map<String, Object> input) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addComponent("atlas", new AtlasComponent());
        context.addRoutes(new RouteBuilder() {
                              public void configure() {
                                  from("direct:start").to("atlas:" + mappingLocation).to("mock:result");
                              }
                          }
        );

        MockEndpoint resultEndpoint = context.getEndpoint("mock:result", MockEndpoint.class);
        ProducerTemplate template = context.createProducerTemplate();
        context.start();
        Map<String, Message> atlasSourceMap = new HashMap<>();

        input.forEach((k, v) -> {
            Message msg = new DefaultMessage(context);
            msg.setBody(v);
            atlasSourceMap.put(k, msg);
        });

        template.sendBodyAndProperty("direct:start", null, AtlasConstants.ATLAS_SOURCE_MAP, atlasSourceMap);
        Map<String, Object> targetMap = resultEndpoint.getExchanges().get(0).getProperty(AtlasConstants.ATLAS_TARGET_MAP, Map.class);
        context.stop();

        return targetMap;
    }

    public boolean verifyMapping(SourceMappingTestClass source, TargetMappingTestClass target, boolean equals) throws Exception {
        this.sourceMap.put(source.getClass().getName(), source);
        TargetMappingTestClass processedTarget = (TargetMappingTestClass) processMapping(TargetMappingTestClass.class.getName());
        LOG.info("cleaning");

        this.target = new TargetMappingTestClass();
        this.source = new SourceMappingTestClass();
        if (equals) {
            Assert.assertEquals(target, processedTarget);
        }
        return target.equals(processedTarget);
    }

    public boolean verifyMultiObjectMapping() throws Exception {
        this.sourceMap.put(this.source.getClass().getName(), this.source);
        this.expectedMap.put(this.target.getClass().getName(), this.target);
        final boolean res = verifyMultiObjectMapping(this.sourceMap);
        this.clear();
        this.initValidator();
        return res;
    }

    public boolean verifyMultiObjectMapping(Map<String, Object> input) throws Exception {
        final Boolean res = verifyMappingInputExpected(input, this.expectedMap);
        clear();
        return res;
    }

    public boolean verifyMappingInputExpected(Map<String, Object> input, Map<String, Object> expected) throws Exception {
        Map<String, Object> processed = processMappingInputMap(input);
        Assert.assertTrue(processed.size() > 0);
        if (processed.isEmpty()) {
            return false;
        }

        expected.forEach((k, v) -> {
            Object actual = processed.get(k);
            Assert.assertEquals(v, actual);
        });
        return true;
    }


    public boolean verifyMapping() throws Exception {
        return this.verifyMapping(this.source, this.target, true);
    }

    public boolean verifyMapping(boolean check) throws Exception {
        return this.verifyMapping(this.source, this.target, check);
    }

    public boolean verifyMapping(TargetMappingTestClass target) throws Exception {
        return this.verifyMapping(this.source, this.target, true);
    }

    public void map(String source, String target) throws ParseException {
        setTargetValue(target, getSourceValue(source));

    }

    public void setTargetValue(String field, Object value) throws ParseException {
        this.target.setAndConvertValue(field, value);
    }

    public void setSourceValue(String field, Object value) throws ParseException {
        source.setAndConvertValue(field, value);
    }

    public Object getSourceValue(String field) {
        return this.source.getValue(field);
    }

    public Object getTargetValue(String field) {
        return this.target.getValue(field);
    }


    public TargetMappingTestClass getTarget() {
        return target;
    }

    public void setTarget(TargetMappingTestClass target) {
        this.target = target;
    }

    public void setMappingLocation(String mappingLocation) {
        this.mappingLocation = mappingLocation;
    }

    public SourceMappingTestClass getSource() {
        return source;
    }

    public Object getSource(String name) {
        return this.sourceMap.get(name);
    }

    public void setSource(SourceMappingTestClass source) {
        this.source = source;
    }

    public Map<String, Object> getExpectedMap() {
        return expectedMap;
    }

    public void setExpectedMap(Map<String, Object> expectedMap) {
        this.expectedMap = expectedMap;
    }


    public void addSource(String name, Object s) {
        this.sourceMap.put(name, s);
    }

    private void clear() {
        this.sourceMap.clear();
        this.expectedMap.clear();
        this.source = new SourceMappingTestClass();
        this.target = new TargetMappingTestClass();
    }

    public void initValidator() throws ParseException {
        sourceMap.put(source.getClass().getName(), source);
        sourceMap.put(SourceListsClass.class.getName(), new SourceListsClass());
        sourceMap.put(SmallMappingTestClass.class.getName(), new SmallMappingTestClass());
        sourceMap.put(DatesObject.class.getName(), new DatesObject("22-12-2012"));
        sourceMap.put("sourceJson", ResourcesGenerator.getJsonInstance());
        sourceMap.put("sourceXmlInstance", ResourcesGenerator.getXMLInstance());
        sourceMap.put("sourceXmlSchema", ResourcesGenerator.getXmlSchemaInstance(null));
    }
}
