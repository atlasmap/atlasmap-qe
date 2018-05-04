package io.atlasmap.qe.resources;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.schema.JsonSchema;

public class ResourcesGenerator {

    private static JsonSchema getJsonSchema(Class clazz) throws IOException {
        org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, true);
        JsonSchema schema = mapper.generateJsonSchema(clazz);
        return schema;
    }

    public static String getJsonInstance() {
//        try {
//            System.out.println(System.getProperty("user.dir")+"/sourceJson.json");
//            Stream<String> stream = Files.lines(Paths.get( System.getProperty("user.dir")+"/test-resources/src/main/resources/sourceJson.json"));
//            StringBuilder json= new StringBuilder();
//           stream.forEach( s -> {
//               json.append(s+"");
//           });
//           return  json.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "null";
//    }
    return "{" +
            "\"sourceJsonString\": \"sourceJsonString\"," +
            "\"sourceJsonInteger\": 10," +
            "\"sourceJsonLong\": 20.0," +
            "\"sourceJsonFloat\": 30.4," +
            "\"sourceJsonDouble\": 40.0," +
            "\"sourceJsonDate\": \"1989-05-05\"," +
            "\"sourceJsonBoolean\": true," +
            "\"sourceJsonShort\": -50," +
            "\"sourceJsonByte\": 127," +
            "\"sourceJsonChar\": 3," +
            "\"smallMappingTestClass\": {" +
            "    \"objectField1\": \"object1\"," +
            "    \"objectField2\": \"object2\"," +
            "    \"listOfStrings\": [" +
            "      \"sdf\"," +
            "      \"sdf\"" +
            "    ]," +
            "    \"listOfIntegers\": [" +
            "      10," +
            "      20," +
            "      30," +
            "      40," +
            "      50" +
            "    ]," +
            "    \"listOfDoubles\": [" +
            "      10.1," +
            "      10.2," +
            "      10.3," +
            "      10.4" +
            "    ]" +
            "  }" +
            "}";
    }

    public static String getXMLInstance() {
        return "<sourceMappingTestClass>\n" +
                "    <sourceXmlBoolean>true</sourceXmlBoolean>\n" +
                "    <sourceXmlByte>A</sourceXmlByte>\n" +
                "    <sourceXmlChar>A</sourceXmlChar>\n" +
                "    <sourceXmlDate></sourceXmlDate>\n" +
                "    <sourceXmlDouble>100.100</sourceXmlDouble>\n" +
                "    <sourceXmlFloat>200.200</sourceXmlFloat>\n" +
                "    <sourceXmlInteger>300</sourceXmlInteger>\n" +
                "    <sourceXmlLong>400</sourceXmlLong>\n" +
                "    <sourceXmlShort>500</sourceXmlShort>\n" +
                "    <sourceXmlString>XmlString</sourceXmlString>\n" +
                "</sourceMappingTestClass>";
    }

    public static String getXmlSchemaInstance(Class c) {
        return
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><SourceXmlMappingSchema>\n" +
                        "    <sourceXMLBoolean>true</sourceXMLBoolean>\n" +
                        "    <sourceXMLByte>A</sourceXMLByte>\n" +
                        "    <sourceXMLChar>A</sourceXMLChar>\n" +
                        "    <sourceXMLDate></sourceXMLDate>\n" +
                        "    <sourceXMLDouble>100.100</sourceXMLDouble>\n" +
                        "    <sourceXMLFloat>200.200</sourceXMLFloat>\n" +
                        "    <sourceXMLInteger>300</sourceXMLInteger>\n" +
                        "    <sourceXMLLong>400</sourceXMLLong>\n" +
                        "    <sourceXMLShort>500</sourceXMLShort>\n" +
                        "    <sourceXMLString>XmlString</sourceXMLString>\n" +
                        "</SourceXmlMappingSchema>";
    }
}
