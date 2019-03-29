package io.atlasmap.qe.test.atlas.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
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

    // directory with AtlasMap mappings
    private static final String MAPPINGS_PATH = System.getProperty("workspace.dir") + "/atlasmap/standalone/target/mappings/";

    public static String requestClass(String className) throws IOException {
        final String requestURL = JAVA_SERVICE + "class?className=" + className;
        LOG.debug("requesting " + requestURL);
        String resp = Request.Get(requestURL).execute().returnContent().toString();
        return resp;
    }

    /**
     * Copies mappings from {@link Utils#MAPPINGS_PATH} to a new file with {@code newName}.
     * @param newName of mappings file
     * @return path of the new file
     * @throws IOException if there is a problem with copying or mapping doesn't exist
     */
    public static String moveMapping(String newName) throws IOException {
        File mappings = new File(MAPPINGS_PATH);
        if (!mappings.exists()) {
            throw new FileNotFoundException("Directory with mappings doesn't exist: " + MAPPINGS_PATH);
        }

        // Finds file from mappings path that was last modified and that ends with ".xml".
        Optional<File> oldMapping = FileUtils.listFiles(mappings, new WildcardFileFilter("*.xml"), TrueFileFilter.TRUE)
                .stream().max(Comparator.comparingLong(File::lastModified));

        File newMapping = new File(System.getProperty("user.dir") + "/" + Constants.MAPPINGS_PATH + "/" + newName);

        newMapping.getParentFile().mkdirs();
        newMapping.createNewFile();

        if (oldMapping.isPresent()) {
            Files.copy(oldMapping.get(), newMapping);
            LOG.debug("Mapping copied to " + newMapping.getAbsolutePath() + " from " + oldMapping.get().getAbsolutePath());
        } else {
            throw new FileNotFoundException("Mapping is not present in " + MAPPINGS_PATH);
        }

        return newMapping.getAbsolutePath();
    }

    /**
     * Deletes all mappings from {@link Utils#MAPPINGS_PATH}.
     * @throws IOException if directory with mappings doesn't exist
     */
    public static void cleanMappingFolder() throws IOException {
        File mappings = new File(MAPPINGS_PATH);
        if (mappings.exists()) {
            FileUtils.listFiles(mappings, new WildcardFileFilter("*.xml"), TrueFileFilter.TRUE).forEach(f -> {
                if (f.delete()) {
                    LOG.debug("Mapping deleted: " + f.getAbsolutePath());
                } else {
                    LOG.error("Cannot delete mapping: " + f.getAbsolutePath());
                }
            });
        } else {
            throw new FileNotFoundException("Directory with mappings doesn't exist: " + MAPPINGS_PATH);
        }
    }
}
