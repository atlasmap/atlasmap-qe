package io.atlasmap.qe.test.utils;

import static com.codeborne.selenide.Condition.visible;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.http.client.fluent.Request;

import com.codeborne.selenide.SelenideElement;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by mmelko on 16/11/2017.
 */
@Slf4j
public class MappingUtils {

    private static final String LIBRARY_SERVICE = TestConfiguration.getBackendUrl() + "/v2/atlas/library/list/";

    public static String requestClass(String className) throws IOException {
        String resp = Request.Get(LIBRARY_SERVICE).execute().returnContent().toString();
        return resp;
    }

    /**
     * Copies mappings from TestConfiguration.getMappingsRootDirectory() to a new file with {@code newName}.
     *
     * @param newName of mappings file
     * @return path of the new file
     * @throws IOException if there is a problem with copying or mapping doesn't exist
     */
    public static String moveMapping(String newName) throws IOException {
        File mappings = new File(TestConfiguration.getMappingsRootDirectory());
        if (!mappings.exists()) {
            throw new FileNotFoundException("Directory with mappings doesn't exist: " + TestConfiguration.getMappingsRootDirectory());
        }

        // Finds file from mappings path that was last modified and that ends with ".json".
        Optional<File> oldMapping = FileUtils.listFiles(mappings, new WildcardFileFilter("*.json"), TrueFileFilter.TRUE)
            .stream().max(Comparator.comparingLong(File::lastModified));

        File newMapping = new File(System.getProperty("user.dir") + "/" + TestConfiguration.getMappingsPath() + "/" + newName);

        newMapping.getParentFile().mkdirs();
        newMapping.createNewFile();

        if (oldMapping.isPresent()) {
            Files.copy(oldMapping.get(), newMapping);
            log.debug("Mapping copied to " + newMapping.getAbsolutePath() + " from " + oldMapping.get().getAbsolutePath());
        } else {
            throw new FileNotFoundException("Mapping is not present in " + TestConfiguration.getMappingsRootDirectory());
        }

        return newMapping.getAbsolutePath();
    }

    /**
     * Deletes all mappings from TestConfiguration.getMappingsRootDirectory().
     *
     * @throws IOException if directory with mappings doesn't exist
     */
    public static void deleteMappingsFromFolder() throws IOException {
        deleteFromMappingsFolder("*.json");
    }

    public static void restoreAdmFile() throws IOException {
        deleteFromMappingsFolder("*.gz");
        File mappings = new File(TestConfiguration.getMappingsRootDirectory());
        if (mappings.exists()) {
            FileUtils.listFiles(mappings, new WildcardFileFilter("*.gz_backup"), TrueFileFilter.TRUE).forEach(f -> {
                File newAdmFile = new File(f.getAbsoluteFile().getAbsolutePath().replace("_backup", ""));
                try {
                    FileUtils.copyFile(f.getAbsoluteFile(), newAdmFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new FileNotFoundException("Directory with mappings doesn't exist: " + TestConfiguration.getMappingsRootDirectory());
        }
    }

    public static void backupAdmFile() throws IOException {
        File mappings = new File(TestConfiguration.getMappingsRootDirectory());
        if (mappings.exists()) {
            FileUtils.listFiles(mappings, new WildcardFileFilter("*.gz"), TrueFileFilter.TRUE).forEach(f -> {
                File backupAdmFile = new File(f.getAbsoluteFile().getAbsolutePath() + "_backup");
                try {
                    FileUtils.copyFile(f.getAbsoluteFile(), backupAdmFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new FileNotFoundException("Directory with mappings doesn't exist: " + TestConfiguration.getMappingsRootDirectory());
        }
    }

    private static void deleteFromMappingsFolder(String wildcard) throws IOException {
        File mappings = new File(TestConfiguration.getMappingsRootDirectory());
        if (mappings.exists()) {
            FileUtils.listFiles(mappings, new WildcardFileFilter(wildcard), TrueFileFilter.TRUE).forEach(f -> {
                if (f.delete()) {
                    log.debug("Deleted from mappings folder: " + f.getAbsolutePath());
                } else {
                    log.error("Cannot delete from mappings folder: " + f.getAbsolutePath());
                }
            });
        } else {
            throw new FileNotFoundException("Directory with mappings doesn't exist: " + TestConfiguration.getMappingsRootDirectory());
        }
    }

    public static void insertCharByChar(String inputValue, SelenideElement inputSelector) {
        for (int i = 0; i < inputValue.length(); i++) {
            char c = inputValue.charAt(i);
            String s = new StringBuilder().append(c).toString();
            inputSelector.shouldBe(visible).sendKeys(s);
        }
    }

    public static void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            log.error("Interruption during thread sleep : " + e.getMessage());
        }
    }
}
