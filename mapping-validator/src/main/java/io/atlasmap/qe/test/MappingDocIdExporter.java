package io.atlasmap.qe.test;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MappingDocIdExporter {

    public static String extractDataSourceIdByName(String atlasMappingLocation, String dataSourceName) {
        String dataSourceId = dataSourceName;
        JSONObject atlasMappingJson = exportAtlasJson(atlasMappingLocation);
        JSONArray dataSourceList = atlasMappingJson.getJSONObject("AtlasMapping").getJSONArray("dataSource");

        for(int i = 0; i < dataSourceList.length(); i++) {
            if(dataSourceList.getJSONObject(i).getString("name").contentEquals(dataSourceName)) {
                dataSourceId = dataSourceList.getJSONObject(i).getString("id");
                break;
            }
        }
        return dataSourceId;
    }

    private static JSONObject exportAtlasJson(String mappingLocation) {
        String jsonTxt = "";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream atlasFileStream = classloader.getResourceAsStream(mappingLocation);
        try {
            jsonTxt = IOUtils.toString( atlasFileStream );
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject atlasJson = new JSONObject(jsonTxt);

        return atlasJson;
    }


}
