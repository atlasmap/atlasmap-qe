package io.atlasmap.qe.test.atlas.utils;

/**
 * Created by mmelko on 16/11/2017.
 */
public final class Constants {
    public static final String UI_PATH = System.getProperty("ui.path", "target/classes/atlasmap-host/");
    public static final String MAPPINGS_PATH = System.getProperty("mapping.path", "target/test-classes/");
    public static final String UI_INDEX_PATH = System.getProperty("ui.path", "file:" + System.getProperty("user.dir") + "/src/main/resources/atlasmap-datamapper-wrapper/dist/atlasmap-datamapper-wrapper/index.html");
    public static final String BACKEND_URL = System.getProperty("ui.url", "http://localhost:8585");

    //timeout for open browser etc.
    public static final long WAIT_TIMEOUT = Long.valueOf(System.getProperty("test.timeout",5000+""));
}
