package io.atlasmap.qe.test.utils;

import io.atlasmap.qe.data.DatesObject;
import io.atlasmap.qe.data.SmallMappingTestClass;
import io.atlasmap.qe.data.StringObject;
import io.atlasmap.qe.data.source.SourceListsClass;
import io.atlasmap.qe.data.source.SourceNestedCollectionClass;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


/*
 * TODO rewrite this class
 * - read all strings from files
 * - create connection between this class and AtlasMapInit (same resources should be loaded in both cases)
 * - extract file paths into final static variables
 */
@Component
public class ResourcesGenerator {

    private final String jsonInstance;
    private final String csvInstance;
    private final String xmlInstance;
    private final String xmlSchemaInstance;
    private final String jsonArrays;
    private final String rootJsonArray;

    @SneakyThrows
    public ResourcesGenerator() {
        String documentsFolderPath = TestConfiguration.getDocumentsFolderPath();

        jsonInstance = new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceJsonSchemaInstance.json")));
        csvInstance = new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceCsv.csv")));
        xmlInstance = new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceXMLInstance.xml")));
        xmlSchemaInstance = new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceXMLSchemaInstance.xml")));
        jsonArrays = new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceArrays.json")));
        rootJsonArray = new String(Files.readAllBytes(Paths.get(documentsFolderPath + "sourceJsonArray.json")));
    }

    public String getJsonInstance() {
        return jsonInstance;
    }

    public String getCsvInstance() {
        return csvInstance;
    }

    public String getXmlInstance() {
        return xmlInstance;
    }

    public String getXmlSchemaInstance() {
        return xmlSchemaInstance;
    }

    public String getJsonArrays() {
        return jsonArrays;
    }

    public String getRootJsonArray() {
        return rootJsonArray;
    }

    public List<Object> getJsonArrays(String field) {
        switch (field) {
            case "jsonIntegers": {
                return new ArrayList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
            }
            case "jsonStrings": {
                return new ArrayList(Arrays.asList("json0", "json1", "json2", "json3", "json4", "json5", "json6", "json7", "json8", "json9"));
            }

            case "jsonObjects": {
                return new ArrayList(Arrays.asList(
                    new StringObject("0", "v0"),
                    new StringObject("1", "v1"),
                    new StringObject("2", "v2"),
                    new StringObject("3", "v3"),
                    new StringObject("4", "v4"),
                    new StringObject("5", "v5"),
                    new StringObject("6", "v6"),
                    new StringObject("7", "v7"),
                    new StringObject("8", "v8"),
                    new StringObject("9", "v9")
                ));
            }
            default: {
                return null;
            }
        }
    }

    public List<Object> getCsvArrays(String field) {
        switch (field) {
            case "csvIntegers": {
                return new ArrayList(Arrays.asList(0, 1, 2, 3, 4));
            }
            case "csvDoubles": {
                return new ArrayList(Arrays.asList(0.0, 10.0, 20.0, 30.0, 40.0));
            }
            case "csvStrings": {
                return new ArrayList(Arrays.asList("csv0", "csv1", "csv2", "csv3", "csv4"));
            }
            default: {
                return null;
            }
        }
    }

    public HashMap<String, Object> generateResourceMap() {
        HashMap<String, Object> resourceMap = new HashMap<>();

        resourceMap.put(SourceListsClass.class.getName(), new SourceListsClass());
        resourceMap.put(SmallMappingTestClass.class.getName(), new SmallMappingTestClass());
        resourceMap.put(SourceNestedCollectionClass.class.getName(), new SourceNestedCollectionClass());
        resourceMap.put(DatesObject.class.getName(), new DatesObject("22-12-2012"));
        resourceMap.put("sourceJson.schema", getJsonInstance());
        resourceMap.put("sourceArrays", getJsonArrays());
        resourceMap.put("sourceXMLInstance", getXmlInstance());
        resourceMap.put("sourceXMLSchema", getXmlSchemaInstance());
        resourceMap.put("sourceJsonArray", getRootJsonArray());

        // FIXME do not use same CSV instance for every CSV resource type
        Stream.of("sourceCsv", "sourceCsvCustomDelimiter", "sourceCsvMissingColumnNames", "sourceCsvCommentMarker",
            "sourceCsvCustomEscapeCharacter", "sourceCsvHeaders", "sourceCsvIgnoreEmptyLines",
            "sourceCsvIgnoreHeaderCase", "sourceCsvIgnoreSurroundingSpaces", "sourceCsvCustomQuoteCharacter",
            "sourceCsvTdfFormat").forEach(csvDocumentName ->
            resourceMap.put(csvDocumentName, getCsvInstance())
        );

        return resourceMap;
    }
}
