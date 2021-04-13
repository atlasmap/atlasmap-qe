package io.atlasmap.qe.mapper;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.atlasmap.AtlasComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.fail;


@Component
public class AtlasMapper {

    public Map<String, Object> processMapping(Map<String, Object> inputMap, String mappingFilePath) {
        CamelContext context = new DefaultCamelContext();
        context.addComponent("atlas", new AtlasComponent());
        try {
            context.addRoutes(new RouteBuilder() {
                                  public void configure() {
                                      from("direct:start").
                                          setHeader("string", constant("test_string")).
                                          setHeader("char", constant("A")).
                                          setHeader("decimal", constant(10)).
                                          setHeader("double", constant(1234.56)).
                                          setHeader("float", constant(4567.86)).
                                          setHeader("integer", constant(1234)).
                                          setHeader("long", constant(1234567890)).
                                          setHeader("short", constant(1234)).
                                          setHeader("boolean", constant(true)).
                                          setProperty("myProperty", constant("test property")).
                                          to("atlas:" + mappingFilePath).
                                          to("mock:result");
                                  }
                              }
            );

            adjustIDsForFormFiles(inputMap, mappingFilePath);

            MockEndpoint resultEndpoint = context.getEndpoint("mock:result", MockEndpoint.class);
            ProducerTemplate template = context.createProducerTemplate();
            context.start();

            template.sendBody("direct:start", inputMap);
            Map<String, Object> targetMap = resultEndpoint.getExchanges().get(0).getIn().getBody(Map.class);
            context.stop();
            targetMap.forEach((String s, Object o) ->
                System.out.println(s + " " + (o == null ? "null" : o.toString())));
            return targetMap;
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage()); // TODO
            return null;
        }
    }

    /**
     * Provides a way, to update the source map of input files, to correspond the ID's in the mapping file.
     * The ID in mapping file is in format: "name-uuid"
     */
    private void adjustIDsForFormFiles(Map<String, Object> inputMap, String mappingFilePath) {
        Set<String> keySet = new HashSet<>();
        keySet.addAll(inputMap.keySet());
        for (String key : keySet) {
            String newKey = extractDataSourceIdByName(mappingFilePath, key);
            if (!newKey.contentEquals(key)) {
                Object value = inputMap.get(key);
                inputMap.remove(key);
                inputMap.put(newKey, value);
            }
        }
    }

    private String extractDataSourceIdByName(String mappingFilePath, String dataSourceName) {
        String dataSourceId = dataSourceName;
        JSONObject atlasMappingJson = exportAtlasJson(mappingFilePath);
        JSONArray dataSourceList = atlasMappingJson.getJSONObject("AtlasMapping").getJSONArray("dataSource");

        for (int i = 0; i < dataSourceList.length(); i++) {
            if (dataSourceList.getJSONObject(i).getString("name").contentEquals(dataSourceName)) {
                dataSourceId = dataSourceList.getJSONObject(i).getString("id");
                break;
            }
        }
        return dataSourceId;
    }

    private JSONObject exportAtlasJson(String mappingFilePath) {
        String jsonTxt = "";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream atlasFileStream = classloader.getResourceAsStream(mappingFilePath);
        try {
            jsonTxt = IOUtils.toString(atlasFileStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject atlasJson = new JSONObject(jsonTxt);

        return atlasJson;
    }
}
