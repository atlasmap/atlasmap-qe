package io.atlasmap.qe.test;

import io.atlasmap.qe.data.DatesObject;
import io.atlasmap.qe.data.SmallMappingTestClass;
import io.atlasmap.qe.data.StringObject;
import io.atlasmap.qe.data.source.SourceListsClass;
import io.atlasmap.qe.data.source.SourceMappingTestClass;
import io.atlasmap.qe.data.source.SourceNestedCollectionClass;
import io.atlasmap.qe.data.target.TargetListsClass;
import io.atlasmap.qe.data.target.TargetMappingTestClass;
import io.atlasmap.qe.data.target.TargetNestedCollectionClass;
import io.atlasmap.qe.test.utils.MappingUtils;
import io.atlasmap.qe.test.utils.TestConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

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
        Optional<File> jarFile = FileUtils.listFiles(new File(TestConfiguration.getJarFolderPath()),
            new WildcardFileFilter("*.jar"), TrueFileFilter.TRUE)
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

        // Source classes:
        page.enableSourceClass(SourceMappingTestClass.class.getName());
        page.enableSourceClass(DatesObject.class.getName());
        page.enableSourceClass(SourceListsClass.class.getName());
        page.enableSourceClass(SmallMappingTestClass.class.getName());
        page.enableSourceClass(SourceNestedCollectionClass.class.getName());

        // Target classes:
        page.enableTargetClass(TargetMappingTestClass.class.getName());
        page.enableTargetClass(StringObject.class.getName());
        page.enableTargetClass(TargetListsClass.class.getName());
        page.enableTargetClass(TargetNestedCollectionClass.class.getName());

        // Source documents:
        page.enableSourceDocument(TestConfiguration.getDocumentsFolderPath() + "sourceArrays.json");
        page.enableSourceDocument(TestConfiguration.getDocumentsFolderPath() + "sourceJsonArray.json");
        page.enableSourceDocument(TestConfiguration.getDocumentsFolderPath() + "sourceJson.schema.json");
        page.enableSourceDocument(TestConfiguration.getDocumentsFolderPath() + "sourceXMLInstance.xml");
        page.enableSourceDocument(TestConfiguration.getDocumentsFolderPath() + "sourceXMLSchema.xsd");
        page.enableCsvSourceDocument(TestConfiguration.getDocumentsFolderPath() + "sourceCsv.csv", "Default",
            new HashMap<String, String>() {{
                put("First Record As Header", "true");
            }}
        );

        // Target documents:
        page.enableTargetDocument(TestConfiguration.getDocumentsFolderPath() + "targetArrays.json");
        page.enableTargetDocument(TestConfiguration.getDocumentsFolderPath() + "targetJsonArray.json");
        page.enableTargetDocument(TestConfiguration.getDocumentsFolderPath() + "targetJson.schema.json");
        page.enableTargetDocument(TestConfiguration.getDocumentsFolderPath() + "targetXMLSchema.xsd");
        page.enableTargetDocument(TestConfiguration.getDocumentsFolderPath() + "targetXMLInstance.xml");
        page.enableCsvTargetDocument(TestConfiguration.getDocumentsFolderPath() + "targetCsv.csv", "Default",
            new HashMap<String, String>() {{
                put("First Record As Header", "true");
            }}
        );

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
