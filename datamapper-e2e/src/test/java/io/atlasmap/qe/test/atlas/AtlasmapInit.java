package io.atlasmap.qe.test.atlas;

import com.codeborne.selenide.WebDriverRunner;
import cucumber.api.event.EventHandler;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestRunFinished;
import cucumber.api.event.TestRunStarted;
import cucumber.api.formatter.Formatter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Loads test resources into AtlasMap.
 * It is plugin for {@link CucumberTest} runner.
 * @author Ondřej Kuhejda
 */
public class AtlasmapInit implements Formatter {
    private static final Logger LOG = LogManager.getLogger(AtlasmapInit.class);

    // directory with test-resources JAR
    private static final String TARGET_FOLDER = System.getProperty("user.dir") + "/../test-resources/target/";
    private static final String DOCUMENTS_FOLDER = System.getProperty("user.dir") + "/src/test/resources/documents/";

    /**
     * {@link AtlasmapPage} needed for resources loading.
     */
    private AtlasmapPage atlasMapPage = new AtlasmapPage();

    /**
     * {@link EventHandler} that is started before all tests.
     * Resets all data in AtlasMap.
     * Loads JAR file into AtlasMap and then enables classes and imports files.
     */
    private EventHandler<TestRunStarted> setup = event -> runOnPage((page) -> {
        page.resetAll();

        // Finds file from target folder that was last modified and that ends with ".jar".
        Optional<File> jarFile = FileUtils.listFiles(new File(TARGET_FOLDER), new WildcardFileFilter("*.jar"), TrueFileFilter.TRUE)
                .stream().max(Comparator.comparingLong(File::lastModified));

        if (jarFile.isPresent()) {
            try {
                page.importJAR(jarFile.get().getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LOG.error("Cannot find JAR file!");
            return;
        }

        page.enableSourceClass("io.atlasmap.qe.test.SourceMappingTestClass");
        page.enableSourceClass("io.atlasmap.qe.test.DatesObject");
        page.enableSourceClass("io.atlasmap.qe.test.SourceListsClass");
        page.enableSourceClass("io.atlasmap.qe.test.SmallMappingTestClass");

        page.enableTargetClass("io.atlasmap.qe.test.TargetMappingTestClass");
        page.enableTargetClass("io.atlasmap.qe.test.StringObject");
        page.enableTargetClass("io.atlasmap.qe.test.TargetListsClass");

        for (File file: Objects.requireNonNull((new File(DOCUMENTS_FOLDER)).listFiles())) {
            if (file.getName().startsWith("source")) {
                page.enableSourceFile(file.getAbsolutePath());
            } else if (file.getName().startsWith("target")) {
                page.enableTargetFile(file.getAbsolutePath());
            } else {
                LOG.error("Unexpected file in \"documents\" folder: " + file.getName());
            }
        }
    });

    /**
     * {@link EventHandler} that is started after all tests.
     * Resets all data in AtlasMap.
     */
    private EventHandler<TestRunFinished> teardown = event -> runOnPage(AtlasmapPage::resetAll);

    /**
     * Opens browser and executes steps defined in {@code pageConsumer}.
     * @param pageConsumer that will be executed inside web browser on {@link AtlasmapInit#atlasMapPage}.
     */
    private void runOnPage(Consumer<AtlasmapPage> pageConsumer) {
        try {
            atlasMapPage.openBrowser();

            pageConsumer.accept(atlasMapPage);

            WebDriverRunner.closeWebDriver();
        } catch (Exception e) {
            LOG.error("Something wrong happened during steps execution inside browser!");
            e.printStackTrace();
        }
    }

    /**
     * Registers handlers for {@link TestRunStarted} and {@link TestRunFinished}.
     * @param eventPublisher that handles registration.
     */
    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunStarted.class, setup);
        eventPublisher.registerHandlerFor(TestRunFinished.class, teardown);
    }
}
