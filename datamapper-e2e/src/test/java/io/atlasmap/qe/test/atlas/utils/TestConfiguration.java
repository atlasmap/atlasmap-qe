package io.atlasmap.qe.test.atlas.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConfiguration {

    public static final String MAPPINGS_PATH = "mapping.path";
    public static final String UI_INDEX_PATH = "ui.url";
    public static final String BACKEND_URL = "backend.url";

    //timeout for open browser etc.
    public static final String WAIT_TIMEOUT = "test.timeout";

    public static final String TEST_PROPERTIES_FILE = "atlasmap.config.test.properties";

    public static final String TESTSUITE_TIMEOUT = "atlasmap.config.timeout";

    public static final String ATLASMAP_SUITE_FAST_INIT = "atlasmap.fast.init";

    public static final String ADM_RESOUCES_FOLDER = "atlasmap.adm.resource";

    public static final String ATLASMAP_UI_BROWSER = "atlasmap.config.ui.browser";

    public static final String MAPPINGS_ROOT_DIRECTORY = "atlasmap.mappings.root.directory";

    public static final String SELENIDE_HEADLESS = "selenide.headless";

    private static final TestConfiguration INSTANCE = new TestConfiguration();

    private final Properties properties = new Properties();

    private TestConfiguration() {
        // first let's try properties in module dir
        copyValues(fromPath("test.properties"), true);

        // then properties in repo root
        copyValues(fromPath("../" + System.getProperty(TEST_PROPERTIES_FILE, "test.properties")));

        // then system variables
        copyValues(System.getProperties());

        // then environment variables
        // TODO: copyValues(fromEnvironment());

        // then defaults
        copyValues(defaultValues());
    }

    private Properties defaultValues() {
        final Properties props = new Properties();
        props.setProperty(MAPPINGS_PATH, "target/test-classes/");
        props.setProperty(UI_INDEX_PATH, "http://localhost:8585");
        props.setProperty(BACKEND_URL, "http://localhost:8585");

        props.setProperty(ATLASMAP_UI_BROWSER, "chrome");

        return props;
    }

    public static TestConfiguration get() {
        return INSTANCE;
    }

    public static String getMappingsPath() {
        return get().readValue(MAPPINGS_PATH);
    }

    public static String getUiIndexPath() {
        return get().readValue(UI_INDEX_PATH);
    }

    public static String getBackendUrl() {
        return get().readValue(BACKEND_URL);
    }

    public static long getWaitTimeout() {
        return Long.parseLong(get().readValue(WAIT_TIMEOUT, "5000"));
    }

    public static boolean getSelenideHeadless() {
        return Boolean.parseBoolean(get().readValue(SELENIDE_HEADLESS, "false"));
    }

    public static boolean getFastInit() {
        return Boolean.parseBoolean(get().readValue(ATLASMAP_SUITE_FAST_INIT, "false"));
    }

    public static int getConfigTimeout() {
        return Integer.parseInt(get().readValue(TESTSUITE_TIMEOUT, "10"));
    }

    public static String getMappingsRootDirectory() {
        return get().readValue(MAPPINGS_ROOT_DIRECTORY, System.getProperty("user.dir") + "/target/target/mappings/");
    }

    public static String getAdmFile() {
        return get().readValue(ADM_RESOUCES_FOLDER, System.getProperty("user.dir") + "/../test-resources/src/main/resources/atlasmap-qe.adm");
    }

    public static String syndesisBrowser() {
        return get().readValue(ATLASMAP_UI_BROWSER);
    }

    private void copyValues(final Properties source) {
        copyValues(source, false);
    }

    private void copyValues(final Properties source, final boolean overwrite) {
        source.stringPropertyNames().stream()
            .filter(key -> overwrite || !this.properties.containsKey(key))
            .forEach(key -> this.properties.setProperty(key, source.getProperty(key)));
    }

    public String readValue(final String key) {
        return readValue(key, null);
    }

    public String readValue(final String key, final String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }

    private Properties fromPath(final String path) {
        final Properties props = new Properties();

        final Path propsPath = Paths.get(path)
            .toAbsolutePath();
        if (Files.isReadable(propsPath)) {
            try (InputStream is = Files.newInputStream(propsPath)) {
                props.load(is);
            } catch (final IOException ex) {
                log.warn("Unable to read properties from '{}'", propsPath);
                log.debug("Exception", ex);
            }
        }

        return props;
    }
}
