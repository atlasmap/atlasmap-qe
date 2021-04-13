package io.atlasmap.qe.test.atlas.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.atlasmap.qe.test.MappingValidator;
import io.atlasmap.qe.test.atlas.AtlasMapInit;
import org.junit.Assert;

import java.util.*;
import java.util.function.Consumer;

import io.atlasmap.qe.test.atlas.AtlasMapPage;
import io.atlasmap.qe.test.atlas.utils.HoverAction;
import io.atlasmap.qe.test.atlas.utils.MappingUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;


@Slf4j
public class UISteps {

    @Inject
    private MappingValidator validator;

    @Inject
    private AtlasMapPage atlasMapPage;

    private boolean internalMapping = true;

    @Given("atlasmap contains TestClass")
    public void atlasMapContainsTestClass() throws Exception {
        String resp = MappingUtils.requestClass(atlasMapPage.TEST_CLASS);
        assertThat(resp).contains(atlasMapPage.TEST_CLASS);
    }

    @Then("browser is opened")
    public void userOpensBrowser() throws Exception {
        atlasMapPage.refreshPage();
    }

    @And("internal mapping is skipped")
    public void internalMappingIsSkipped() {
        internalMapping = false;
    }

    @And("internal mapping is set to {string}")
    public void internalMappingIsSetTo(String mapping) {
        // Write code here that turns the phrase above into concrete actions
        internalMapping = "true".equals(mapping);
    }

    @And("set mapping condition to {string} by drag and drop")
    public void setMappingConditionByDragAndDrop(String condition) {
        // TODO
    }

    @And("set mapping condition to {string} by auto completion")
    public void setMappingConditionByAutoCompletion(String condition) {
        setMappingConditionTo(condition, s -> {
            atlasMapPage.addToConditionalMapping("@" + s);
            atlasMapPage.clickOnValueFromPicker(s);
        });
    }

    @And("set mapping condition to {string} by selecting sources")
    public void setMappingConditionBySelectingSources(String condition) {
        setMappingConditionTo(condition, s -> {
            atlasMapPage.addToMappingUsingMappingDetails(s, true);
        });
    }

    private void setMappingConditionTo(String condition, Consumer<String> method) {
        clickAddMappingCondition();
        for (String s : condition.split("((?<=@\\{[a-zA-Z/<>',]{0,100}\\})|(?=@\\{[a-zA-Z/<>]{0,100}|''\\}))")) {
            if (s.startsWith("@")) {
                method.accept(s.replaceAll("[@{}]", ""));
            } else {
                atlasMapPage.addToConditionalMapping(s);
            }
        }
    }

    @Then("click on enable or disable conditional mapping expression button")
    public void clickAddMappingCondition() {
        atlasMapPage.toggleConditionalMapping();
    }

    @Then("check if warnings from {string} to {string} are displayed with messages")
    public void checkIfMultipleWarningsFromToDisplayed(String from, String to, DataTable sourceMappingData) {
        List<String> source = new ArrayList<>(sourceMappingData.asList());
        Assert.assertTrue(this.atlasMapPage.checkMultipleWarnings(source, from, to));
    }

    @Then("check if {string} warning from {string} to {string} is displayed")
    public void checkIfFromToDisplayed(String exceptionType, String from, String to) {
        List<String> source = new ArrayList<>(Arrays.asList(exceptionType));
        Assert.assertTrue(this.atlasMapPage.checkMultipleWarnings(source, from, to));
    }

    @Then("check if asymmetric mapping warning from {string} level to {string} level is displayed")
    public void checkIfAsymmetricMappingWarningIsDisplayed(String sourceLevel, String targetLevel) {
        Assert.assertTrue(this.atlasMapPage.checkAsymmetricMappingWarning(Integer.parseInt(sourceLevel), Integer.parseInt(targetLevel)));
    }

    @Then("check if warning for conditional mapping with multiple targets is displayed")
    public void checkIfAsymmetricMappingWarningIsDisplayed() {
        Assert.assertTrue(this.atlasMapPage.checkMultipleTargetsWarning());
    }

    @And("check if warning contains {string} message")
    public void checkIfWarningContainsMessage(String message) {
        Assert.assertTrue(this.atlasMapPage.checkWarningContainMessage(message));
    }

    @Then("check if {string} warning from {string} to {string} is not displayed")
    public void checkIfWarningFromToIsNotDisplayed(String exceptionType, String from, String to) {
        Assert.assertFalse(this.atlasMapPage.checkWarning(exceptionType, from, to));
    }

    @Then("check if no warnings are displayed")
    public void checkIfNoWarningsAreDisplayed() {
        this.atlasMapPage.checkWarnings();
    }

    @And("add {string} to combine")
    public void addToCombine(String field) {
        this.atlasMapPage.addToMappingUsingFieldPanel(field, true);
    }

    @And("add {string} to separate")
    public void addToSeparate(String field) {
        atlasMapPage.addToMappingUsingFieldPanel(field, false);
    }

    @When("delete current mapping")
    public void deleteCurrentMapping() {
        atlasMapPage.deleteCurrentMapping();
    }

    @And("reveal mapping details")
    public void revealMappingDetails() {
        atlasMapPage.clickOnLinkByClass(".fa.fa-edit.link");
    }

    @And("add mapping from {string} to {string}")
    public void addMappingFromTo(String from, String to) throws Throwable {
        atlasMapPage.createNewMapping(from, "source");
        atlasMapPage.addToMappingUsingFieldPanel(to, false);
        if (internalMapping) {
            this.validator.map(from, to);
        }
    }

    @And("add click {string} link")
    public void addClickLink(String arg0) {
        this.atlasMapPage.clickOnLinkByClass(".fa.fa-long-arrow-right");
    }

    @When("change select from {string} to {string}")
    public void changeSelectFromTo(String from, String to) {
        this.atlasMapPage.changeSelectValue(from, to);
    }

    @And("drag {string} and drop on {string}")
    public void dragAndDropOn(String drag, String drop) {
        this.atlasMapPage.dragNDrop(drag, drop);
    }

    @And("Show mapping preview")
    public void showMappingPreview() {
        this.atlasMapPage.clickOnLinkByDataTestId("show-hide-mapping-preview-button");
    }

    @And("set preview data")
    public void setPreviewData(DataTable values) {
        for (Map<String, String> data : values.asMaps()) {
            for (String key : data.keySet()) {
                final String value = data.get(key);
                log.info(key + " " + value);
                this.atlasMapPage.setInputValueForFieldPreview(key, value);
            }
        }
    }

    @Then("verify that {string} contains {string}")
    public void verifyThatContains(String field, String v) {
        String value = this.atlasMapPage.getFieldPreviewValue(field);
        Assert.assertEquals(v, value);
    }

    @Then("verify preview data")
    public void verifyPreviewData(DataTable values) {
        for (Map<String, String> data : values.asMaps()) {
            for (String key : data.keySet()) {
                final String value = data.get(key);
                String val = this.atlasMapPage.getFieldPreviewValue(key);
                Assert.assertEquals(value, val);
            }
        }
    }

    @And("set {string} for {string} field")
    public void setForField(String value, String field) {
        this.atlasMapPage.setInputValueForFieldPreview(field, value);
    }

    @And("check if danger warning contains {string} message")
    public void checkIfDangerWarningContainsMessage(String message) {
        Assert.assertTrue(this.atlasMapPage.checkDangerWarningContainMessage(message));
    }

    @And("set {string} constant with {string} value")
    public void setConstantWithValue(String type, String value) {
        atlasMapPage.addConstant(type, value);
    }

    @When("set {string} property of type {string}, name {string}, scope {string}")
    public void setProperty(String sourceTarget, String type, String name, String scope) {
        atlasMapPage.addProperty(sourceTarget.equals("source") ? true : false, type, name, scope);
    }

    @And("switch to mapping table view")
    public void mappingTableView() {
        atlasMapPage.clickOnLinkByDataTestId("show-hide-mapping-table-button");
    }

    @And("switch to column mapper view")
    public void columnMapperView() {
        atlasMapPage.clickOnLinkByDataTestId("show-column-mapper-button");
    }

    @Then("check that row number \"{int}\" contains {string} as sources, {string} as target and {string} as type")
    public void checkThatRowNumberContainsAsSourcesAsTagetAndAsType(int number, String sources, String targets, String mappingType) {
        assertThat(atlasMapPage.getLabelFromMappingTable(number, "type")).isEqualToIgnoringCase(mappingType);
        assertThat(atlasMapPage.getLabelFromMappingTable(number, "sources")).isEqualToIgnoringCase(sources);
        assertThat(atlasMapPage.getLabelFromMappingTable(number, "targets")).isEqualToIgnoringCase(targets);
    }

    @And("click on \"{int}\" index of table")
    public void clickOnIndexOfTable(int index) {
        atlasMapPage.clickOnRowInMappingTable(index);
    }

    @Then("check that on \"{int}\" row number is for {string} source value displayed {string} target preview")
    public void checkThatOnRowNumberIsForSourceValueSDisplayedTargetPreview(int index, String source, String target) {
        atlasMapPage.clickOnRowInMappingTable(index);
        atlasMapPage.setPreviewValueInTable(index, "sources", source.split(";"));
        atlasMapPage.clickOnRowInMappingTable(index);
        final String preview = atlasMapPage.getPreviewValueInTable(index, "targets");
        assertThat(preview).isEqualTo(target);
    }

    @And("open all subfolders")
    public void openAllSubfolders() {
        atlasMapPage.openAllSubfolders();
    }

    @And("open all data buckets named {string}")
    public void openAllDataBuckets(String bucket) {
        atlasMapPage.openAllBucketsWithName(bucket);
    }

    @And("set {string} as {string}")
    public void setAs(String field, String src) {
        this.atlasMapPage.addToMappingUsingFieldPanel(field, "source".equals(src));
    }

    @And("add {string} as {string}")
    public void addToMappingUsingMappingDetail(String field, String src) {
        this.atlasMapPage.addToMappingUsingMappingDetails(field, "source".equals(src));
    }

    @And("delete {string} on {string}")
    public void deleteOn(String field, String src) {
        this.atlasMapPage.deleteFromMapping(field, "source".equals(src));
    }

    @And("change index of {string} to \"{int}\" on {string}")
    public void changeIndexOfToOn(String field, int value, String src) {
        this.atlasMapPage.changeIndexValue(field, value, "source".equals(src));
    }

    @When("init mapping from {string} to {string}")
    public void initMappingFromTo(String target, String source) {

        this.atlasMapPage.dragNDrop(source, target);
        //TODO: drag the source to target - will initialize the mapping
    }

    @And("show mapping details of fiels {string}")
    public void setMappingNrAsActive(String mappingNr) {
        //TODO
    }

    @And("click on create new mapping")
    public void clickOnCreateNewMapping() {
        this.atlasMapPage.clickOnLinkByDataTestId("add-new-mapping-button");
    }

    @And("add source {string} to active mapping")
    public void addSourceToActiveMapping(String mappingField) {
        this.atlasMapPage.addToMappingUsingFieldPanel(mappingField, true);
    }

    @And("select source {string} from dropdown to active mapping")
    public void selectSourceToActiveMapping(String mappingField) {
        this.atlasMapPage.addToMappingUsingFieldPanel(mappingField, true);
    }
    @And("select target {string} from dropdown to active mapping")
    public void selectTargetToActiveMapping(String mappingField) {
        this.atlasMapPage.addToMappingUsingFieldPanel(mappingField, true);
    }

    @And("add target {string} to active mapping")
    public void addTargetToActiveMapping(String mappingField) {
        this.atlasMapPage.addToMappingUsingFieldPanel(mappingField, false);
    }

    @When("connect field {string} to active mapping")
    public void connectFieldToActiveMapping(String source) {
        this.atlasMapPage.addToMappingUsingFieldPanel(source, true);
    }

    @And("set mapping to {string} from {string}")
    public void userSetsMappingToFrom(String target, String source) throws Throwable {
        this.atlasMapPage.createNewMapping(source, "source");
        this.atlasMapPage.addToMappingUsingFieldPanel(target, false);
        if (internalMapping) {
            this.validator.map(source, target);
        }
    }

    @And("click on create new mapping from source {string}")
    public void clickOnCreateNewMappingFromSource(String mappingField) {

        atlasMapPage.createNewMapping(mappingField, "source");
    }

    @And("click on create new mapping from target {string}")
    public void clickOnCreateNewMappingFrom(String mappingField) {

        atlasMapPage.createNewMapping(mappingField, "target");
    }

    @And("click show target mapping for {string}")
    public void clickShowTargetMapping(String mappingField) {
        atlasMapPage.hoverAndSelectOperation(mappingField, HoverAction.SHOW_MAPPING_DETAILS, "target");
    }

    @And("click show source mapping for {string}")
    public void clickShowSourceMapping(String mappingField) {
        atlasMapPage.hoverAndSelectOperation(mappingField, HoverAction.SHOW_MAPPING_DETAILS, "source");
    }

    @And("import CSV file {string} formatted as {string} with parameters")
    public void importCSVFileWithParameters(String fileName, String format, DataTable parameters) {
        System.out.println(fileName + format);
        atlasMapPage.enableCsvSourceDocument(AtlasMapInit.DOCUMENTS_FOLDER + fileName, format,
            parameters.asMap(String.class, String.class));
    }

    @And("import CSV file {string} formatted as {string}")
    public void importCSVFile(String fileName, String format) {
        System.out.println(fileName + format);
        atlasMapPage.enableCsvSourceDocument(AtlasMapInit.DOCUMENTS_FOLDER + fileName, format, new HashMap<>());
    }

    @And("remove {string} document called {string}")
    public void removeDocument(String type, String name) {
        if (!type.equals("source") && !type.equals("target")) {
            throw new IllegalArgumentException("Type of document needs to be 'source' or 'target'!");
        }
        atlasMapPage.removeDocument(type, name);
    }
}
