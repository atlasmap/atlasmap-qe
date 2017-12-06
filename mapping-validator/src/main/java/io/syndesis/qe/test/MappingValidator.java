package io.syndesis.qe.test;

import java.text.ParseException;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.atlasmap.AtlasComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by mmelko on 15/11/2017.
 */
public class MappingValidator {
    private static final Logger LOG = LogManager.getLogger(MappingValidator.class);

    private String mappingLocation;
    private SourceMappingTestClass source;
    private TargetMappingTestClass target;

    public MappingValidator() {
        source = new SourceMappingTestClass();
        target = new TargetMappingTestClass();
    }

    public TargetMappingTestClass processMapping(SourceMappingTestClass input) throws Exception {
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
        template.sendBody("direct:start", input);
        TargetMappingTestClass res = (TargetMappingTestClass) resultEndpoint.getExchanges().get(0).getIn().getBody();
        context.stop();

        return res;
    }

    public boolean verifyMapping(SourceMappingTestClass source, TargetMappingTestClass target) throws Exception {
        TargetMappingTestClass processedTarget = processMapping(source);
        LOG.info("source: " + source);
        LOG.info("expected target: " + target);
        LOG.info("actual target: " + processedTarget);
        LOG.info("cleaning");
        this.target = new TargetMappingTestClass();
        this.source = new SourceMappingTestClass();
        return target.equals(processedTarget);
    }

    public boolean verifyMapping() throws Exception {
        return this.verifyMapping(this.source, this.target);
    }

    public boolean verifyMapping(TargetMappingTestClass target) throws Exception {
        return this.verifyMapping(this.source, this.target);
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

    private Object getSourceValue(String field) {
        return this.source.getValue(field);
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

    public String getMappingLocation() {
        return mappingLocation;
    }

    public SourceMappingTestClass getSource() {
        return source;
    }

    public void setSource(SourceMappingTestClass source) {
        this.source = source;
    }

    public void initFromDataTable() {
    }
}
