package io.atlasmap.qe.test;

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
public class AtlasmapInit implements EventListener {

    /**
     * {@link AtlasmapPage} needed for resources loading.
     */
    private final AtlasmapPage atlasmapPage = new AtlasmapPage();

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
        page.enableSourceClass("io.atlasmap.qe.data.source", "SourceMappingTestClass");
        page.enableSourceClass("io.atlasmap.qe.data", "DatesObject");
        page.enableSourceClass("io.atlasmap.qe.data.source", "SourceListsClass");
        page.enableSourceClass("io.atlasmap.qe.data", "SmallMappingTestClass");
        page.enableSourceClass("io.atlasmap.qe.data.source", "SourceNestedCollectionClass");

        // Target classes:
        page.enableTargetClass("io.atlasmap.qe.data.target", "TargetMappingTestClass");
        page.enableTargetClass("io.atlasmap.qe.data", "StringObject");
        page.enableTargetClass("io.atlasmap.qe.data.target", "TargetListsClass");
        page.enableTargetClass("io.atlasmap.qe.data.target", "TargetNestedCollectionClass");

        // Source documents:
        page.enableSourceDocumentInstance(TestConfiguration.getDocumentsFolderPath() + "sourceArrays.json");
        page.enableSourceDocumentInstance(TestConfiguration.getDocumentsFolderPath() + "sourceJsonArray.json");
        page.enableSourceDocumentInstance(TestConfiguration.getDocumentsFolderPath() + "sourceXMLInstance.xml");

        page.enableSourceDocumentSchema(TestConfiguration.getDocumentsFolderPath() + "sourceJson.schema.json");
        page.enableSourceDocumentSchema(TestConfiguration.getDocumentsFolderPath() + "sourceXMLSchema.xsd");

        page.enableCsvSourceDocument(TestConfiguration.getDocumentsFolderPath() + "sourceCsv.csv", "Default",
            new HashMap<String, String>() {{
                put("First Record As Header", "true");
            }}
        );

        // Target documents:
        page.enableTargetDocumentInstance(TestConfiguration.getDocumentsFolderPath() + "targetArrays.json");
        page.enableTargetDocumentInstance(TestConfiguration.getDocumentsFolderPath() + "targetJsonArray.json");
        page.enableTargetDocumentInstance(TestConfiguration.getDocumentsFolderPath() + "targetXMLInstance.xml");

        page.enableTargetDocumentSchema(TestConfiguration.getDocumentsFolderPath() + "targetJson.schema.json");
        page.enableTargetDocumentSchema(TestConfiguration.getDocumentsFolderPath() + "targetXMLSchema.xsd");

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
    private final EventHandler<TestRunFinished> teardown = event -> runOnPage(AtlasmapPage::resetAll);

    /**
     * Opens browser and executes steps defined in {@code pageConsumer}.
     *
     * @param pageConsumer that will be executed inside web browser on {@link AtlasmapInit#atlasmapPage}.
     */
    private void runOnPage(Consumer<AtlasmapPage> pageConsumer) {
        pageConsumer.accept(atlasmapPage);
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
