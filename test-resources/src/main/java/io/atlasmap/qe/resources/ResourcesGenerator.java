package io.atlasmap.qe.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.atlasmap.qe.test.StringObject;

public class ResourcesGenerator {

    public static String getJsonInstance() {
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

    public static String getJsonArrays() {
        return "  {\n" +
                "        \"jsonIntegers\": [0,1,2,3,4,5,6,7,8,9],\n" +
                "        \"jsonStrings\": [\"json0\",\"json1\",\"json2\",\"json3\",\"json4\",\"json5\",\"json6\",\"json7\",\"json8\",\"json9\"],\n" +
                "        \"jsonObjects\": [\n" +
                "            {\"key\":0,\"value\":\"v0\"},\n" +
                "            {\"key\":1,\"value\":\"v1\"},\n" +
                "            {\"key\":2,\"value\":\"v2\"},\n" +
                "            {\"key\":3,\"value\":\"v3\"},\n" +
                "            {\"key\":4,\"value\":\"v4\"},\n" +
                "            {\"key\":5,\"value\":\"v5\"},\n" +
                "            {\"key\":6,\"value\":\"v6\"},\n" +
                "            {\"key\":7,\"value\":\"v7\"},\n" +
                "            {\"key\":8,\"value\":\"v8\"},\n" +
                "            {\"key\":9,\"value\":\"v9\"}\n" +
                "        ]\n" +
                "    }";
    }

    public static List<Object> getJsonArrays(String field) {
        switch (field) {
            case "jsonIntegers": {
                return new ArrayList(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
            }
            case "jsonStrings": {
                return new ArrayList(Arrays.asList("json0","json1","json2","json3","json4","json5","json6","json7","json8","json9"));
            }

            case "jsonObjects": {
                return new ArrayList(Arrays.asList(
                        new StringObject("0","v0"),
                        new StringObject("1","v1"),
                        new StringObject("2","v2"),
                        new StringObject("3","v3"),
                        new StringObject("4","v4"),
                        new StringObject("5","v5"),
                        new StringObject("6","v6"),
                        new StringObject("7","v7"),
                        new StringObject("8","v8"),
                        new StringObject("9","v9")
                ));
            }
        }
        return null;
    }
}
