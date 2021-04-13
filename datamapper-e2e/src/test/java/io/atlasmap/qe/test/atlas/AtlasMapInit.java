package io.atlasmap.qe.test.atlas;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

import io.atlasmap.qe.test.atlas.utils.TestConfiguration;
import io.atlasmap.qe.test.atlas.utils.MappingUtils;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;


/**
 * Loads test resources into AtlasMap.
 * It is plugin for {@link CucumberTest} runner.
 *
 * @author Ondřej Kuhejda
 */
public class AtlasMapInit implements EventListener {

    // directory with test-resources JAR
    private static final String TARGET_FOLDER = System.getProperty("user.dir") + "/../test-resources/target/";
    public static final String DOCUMENTS_FOLDER = System.getProperty("user.dir") + "/src/test/resources/documents/";

    /**
     * {@link AtlasMapPage} needed for resources loading.
     */
    private final AtlasMapPage atlasMapPage = new AtlasMapPage(); // TODO: figure out how to use dependency injection

    /**
     * {@link EventHandler} that is started before all tests.
     * Resets all data in AtlasMap.
     * Loads JAR file into AtlasMap and then enables classes and imports files.
     */
    private final EventHandler<TestRunStarted> setup = event -> runOnPage((page) -> {
        page.resetAll();

        // Finds file from target folder that was last modified and that ends with ".jar".
        Optional<File> jarFile = FileUtils.listFiles(new File(TARGET_FOLDER), new WildcardFileFilter("*.jar"), TrueFileFilter.TRUE)
            .stream().max(Comparator.comparingLong(File::lastModified));

        // Imports JAR file.
        if (jarFile.isPresent()) {
            try {
                page.importJAR(jarFile.get().getCanonicalPath());
            } catch (IOException e) {
                throw new IllegalStateException("Error when getting canonical path of JAR file.", e);
            }
        } else {
            throw new IllegalStateException("Cannot find JAR file with test classes!");
        }

        // TODO: obtain classes names programmatically
        // Source classes:
        page.enableSourceClass("io.atlasmap.qe.test.SourceMappingTestClass");
        page.enableSourceClass("io.atlasmap.qe.test.DatesObject");
        page.enableSourceClass("io.atlasmap.qe.test.SourceListsClass");
        page.enableSourceClass("io.atlasmap.qe.test.SmallMappingTestClass");
        page.enableSourceClass("io.atlasmap.qe.test.SourceNestedCollectionClass");

        // Target classes:
        page.enableTargetClass("io.atlasmap.qe.test.TargetMappingTestClass");
        page.enableTargetClass("io.atlasmap.qe.test.StringObject");
        page.enableTargetClass("io.atlasmap.qe.test.TargetListsClass");
        page.enableTargetClass("io.atlasmap.qe.test.TargetNestedCollectionClass");

        // Source documents:
        page.enableSourceDocument(DOCUMENTS_FOLDER + "sourceArrays.json");
        page.enableSourceDocument(DOCUMENTS_FOLDER + "sourceJsonArray.json");
        page.enableSourceDocument(DOCUMENTS_FOLDER + "sourceJson.schema.json");
        page.enableSourceDocument(DOCUMENTS_FOLDER + "sourceXmlInstance.xml");
        page.enableSourceDocument(DOCUMENTS_FOLDER + "sourceXMLSchema.xsd");
        page.enableCsvSourceDocument(DOCUMENTS_FOLDER + "sourceCsv.csv", "Default", new HashMap<String, String>() {{
            put("First Record As Header", "true");
        }});

        // Target documents:
        page.enableTargetDocument(DOCUMENTS_FOLDER + "targetArrays.json");
        page.enableTargetDocument(DOCUMENTS_FOLDER + "targetJsonArray.json");
        page.enableTargetDocument(DOCUMENTS_FOLDER + "targetJson.schema.json");
        page.enableTargetDocument(DOCUMENTS_FOLDER + "targetXMLSchema.xsd");
        page.enableTargetDocument(DOCUMENTS_FOLDER + "targetXMLInstance.xml");
        page.enableCsvTargetDocument(DOCUMENTS_FOLDER + "targetCsv.csv", "Default", new HashMap<String, String>() {{
            put("First Record As Header", "true");
        }});

        // TODO: find more dynamic way for initialization check
        MappingUtils.sleep(1000);
        try {
            MappingUtils.backupAdmFile();
        } catch (IOException e) {
            throw new IllegalStateException("Error when backing up initial ADM file.", e);
        }
    });

    /**
     * Alternative setup {@link EventHandler} that imports required resources into the AtlasMap using ADM file.
     */
    private final EventHandler<TestRunStarted> setupFast = event -> runOnPage((page) -> {
        page.resetAll();

        File admFile = new File(TestConfiguration.getAdmFile());

        // Imports ADM file.
        try {
            page.importAdmFile(admFile.getCanonicalPath());
            MappingUtils.backupAdmFile();
        } catch (IOException e) {
            throw new IllegalStateException("Error when getting canonical path of ADM file.", e);
        }
    });

    /**
     * {@link EventHandler} that is started after all tests.
     * Resets all data in AtlasMap.
     */
    private final EventHandler<TestRunFinished> teardown = event -> runOnPage(AtlasMapPage::resetAll);

    /**
     * Opens browser and executes steps defined in {@code pageConsumer}.
     *
     * @param pageConsumer that will be executed inside web browser on {@link AtlasMapInit#atlasMapPage}.
     */
    private void runOnPage(Consumer<AtlasMapPage> pageConsumer) {
        pageConsumer.accept(atlasMapPage);
    }

    /**
     * Registers handlers for {@link TestRunStarted} and {@link TestRunFinished}.
     *
     * @param eventPublisher that handles registration.
     */
    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        if (TestConfiguration.getFastInit()) {
            eventPublisher.registerHandlerFor(TestRunStarted.class, setupFast);
        } else {
            eventPublisher.registerHandlerFor(TestRunStarted.class, setup);
        }
        eventPublisher.registerHandlerFor(TestRunFinished.class, teardown);
    }
}
