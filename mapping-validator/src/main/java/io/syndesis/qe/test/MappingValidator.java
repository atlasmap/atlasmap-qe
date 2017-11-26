package io.syndesis.qe.test;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.atlasmap.AtlasComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by mmelko on 15/11/2017.
 */
public class MappingValidator {

    private String mappingLocation;

    public MappingValidator(String mappingLocation) {
        this.mappingLocation = mappingLocation;
    }

    public MappingTestClass processMapping(MappingTestClass input) throws Exception {
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
        MappingTestClass res = (MappingTestClass) resultEndpoint.getExchanges().get(0).getIn().getBody();
        context.stop();

        return res;
    }

    public boolean validateMappinng() {
        throw new NotImplementedException();
    }

    public static void main(String[] args) throws Exception {
        MappingValidator validator = new MappingValidator("combine.xml");
        MappingTestClass temp = validator.processMapping(new MappingTestClass());
        System.out.println(temp.getCombineString());
    }
}
