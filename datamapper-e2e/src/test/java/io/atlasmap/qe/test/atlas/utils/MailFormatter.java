package io.atlasmap.qe.test.atlas.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.cucumber.core.gherkin.messages.internal.gherkin.GherkinDocumentBuilder;
import io.cucumber.core.gherkin.messages.internal.gherkin.Parser;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestSourceRead;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailFormatter implements EventListener {

    private static final Pattern SUSTAINER_PATTERN = Pattern.compile(".*@sustainer: ([a-zA-Z@.]+)");

    private final String path;

    private ObjectMapper mapper = new ObjectMapper();

    private Map<URI, String> sustainers = new HashMap<>();
    private Map<URI, Messages.GherkinDocument.Feature> features = new HashMap<>();
    private Set<String> recipients = new HashSet<>();
    private List<ScenarioResult> results = new ArrayList<>();
    private int passed, failed, total = 0;

    public MailFormatter(String path) {
        this.path = path;
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestSourceRead.class, this::onTestSourceRead);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
    }

    private void onTestSourceRead(TestSourceRead t) {
        Messages.GherkinDocument doc = parseGherkinSource(t.getSource());
        if (doc != null) {
            features.put(t.getUri(), doc.getFeature());
            for (Messages.GherkinDocument.Comment c : doc.getCommentsList()) {
                Matcher matcher = SUSTAINER_PATTERN.matcher(c.getText());
                if (matcher.matches()) {
                    sustainers.put(t.getUri(), matcher.group(1));
                }
            }
        }
    }

    private void onTestCaseFinished(TestCaseFinished t) {
        URI uri = t.getTestCase().getUri();
        total++;
        switch (t.getResult().getStatus()) {
            case PASSED:
                passed++;
                break;
            case FAILED:
                failed++;
                break;
            default:
                break;
        }
        ;
        results.add(
            new ScenarioResult(
                features.get(uri), t.getTestCase(), t.getResult().getStatus())
        );
        if (!t.getResult().getStatus().equals(Status.PASSED) && sustainers.get(uri) != null) {
            recipients.add(sustainers.get(uri));
        }
    }

    private void onTestRunFinished(TestRunFinished t) {
        new File(path).mkdirs();
        try (FileWriter out = new FileWriter(new File(path, "report.html"))) {
            out.write("<h2>Atlasmap QE E2E test results</h2>" +
                String.format("PASSED %d / %d<br/>\n", passed, total) +
                String.format("Failed %d / %d<br/>\n", failed, total));

            out.write(String.join(
                "<br/>\n",
                results.stream().map(ScenarioResult::toString).collect(Collectors.toList())
                )
            );
        } catch (IOException e) {
            log.error("Error writing mail report file", e);
        }
        try (FileWriter out = new FileWriter(new File(path, "mail-recipients"))) {
            out.write(String.join("\n", recipients) + "\n");
        } catch (IOException e) {
            log.error("Error writing mail recipients file", e);
        }
    }

    private Messages.GherkinDocument parseGherkinSource(String source) {
        return new Parser<>(new GherkinDocumentBuilder(new IdGenerator.Incrementing())).parse(source).build();
    }

    @Data
    @RequiredArgsConstructor
    private static class ScenarioResult {
        private final Messages.GherkinDocument.Feature feature;
        private final TestCase testCase;
        private final Status result;

        public String toString() {
            // TODO: consider using a templating engine for this
            StringBuilder sb = new StringBuilder();

            sb
                .append(feature.getName())
                .append(" | ")
                .append(testCase.getName())
                .append(" | ");

            if (result.equals(Status.PASSED)) {
                sb
                    .append("<font color=\"green\">")
                    .append(result)
                    .append("</font>");
            } else if (result.equals(Status.SKIPPED)) {
                sb
                    .append("<font color=\"yellow\">")
                    .append(result)
                    .append("</font>");
            } else {
                sb
                    .append("<font color=\"red\"><b>")
                    .append(result)
                    .append("</b></font>");
            }

            return sb.toString();
        }
    }
}
