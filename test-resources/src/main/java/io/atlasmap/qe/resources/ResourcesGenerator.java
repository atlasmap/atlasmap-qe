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

    private static String getXmlSchema(Class c) {
        return "";
    }

    public static void main(String[] args) {
        System.out.println(getJsonInstance());
    }
}
