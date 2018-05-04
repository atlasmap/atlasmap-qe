package io.atlasmap.qe.test.atlas.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.fluent.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.io.Files;

/**
 * Created by mmelko on 16/11/2017.
 */
public class Utils {

    private static final String JAVA_SERVICE = Constants.BACKEND_URL + "/v2/atlas/java/";
    private static final Logger LOG = LogManager.getLogger(Utils.class);

    public static String requestClass(String className) throws IOException {
        final String requestURL = JAVA_SERVICE + "class?className=" + className;
        LOG.debug("requesting " + requestURL);
        String resp = Request.Get(requestURL).execute().returnContent().toString();
        return resp;
    }

    public static String moveMappping(String newName) throws IOException {
        final String mappingsPath = System.getProperty("user.dir") + "/target/mappings";
        File mapping = new File(mappingsPath).listFiles()[0];
        File newMapping = new File(System.getProperty("user.dir") + "/" + Constants.MAPPINGS_PATH + "/" + newName);
        newMapping.getParentFile().mkdirs();
        newMapping.createNewFile();

        if (mapping.isFile()) {
            Files.copy(mapping, newMapping);
        }
        return newMapping.getAbsolutePath();
    }

    public static void cleanMappingFolder() throws IOException {
        final String mappingsPath = System.getProperty("user.dir") + "/target/mappings";
        File mappings = new File(mappingsPath);
        if (mappings.exists()) {
            FileUtils.cleanDirectory(mappings);
        }
    }
}
