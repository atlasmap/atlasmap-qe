package io.atlasmap.qe.test.atlas.steps;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import io.atlasmap.qe.test.atlas.AtlasmapPage;
import io.atlasmap.qe.test.atlas.utils.HoverAction;
import io.atlasmap.qe.test.atlas.utils.Utils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UISteps extends CucumberGlue {

    private AtlasmapPage atlasmapPage = new AtlasmapPage();
    private static boolean internalMapping = true;

    @Given("atlasmap contains TestClass")
    public void atlasmapContainsTestClass() throws Exception {
        String resp = Utils.requestClass(atlasmapPage.TEST_CLASS);
        assertThat(resp).contains(atlasmapPage.TEST_CLASS);
    }

    @Then("browser is opened")
    public void userOpensBrowser() throws Exception {
        atlasmapPage.refreshPage();
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
            atlasmapPage.addToConditionalMapping("@" + s);
            atlasmapPage.clickOnValueFromPicker(s);
        });
    }

    @And("set mapping condition to {string} by selecting sources")
    public void setMappingConditionBySelectingSources(String condition) {
        setMappingConditionTo(condition, s -> {
            atlasmapPage.addToMappingUsingMappingDetails(s, true);
        });
    }

    private void setMappingConditionTo(String condition, Consumer<String> method) {
        clickAddMappingCondition();
        for (String s : condition.split("((?<=@\\{[a-zA-Z/<>',]{0,100}\\})|(?=@\\{[a-zA-Z/<>]{0,100}|''\\}))")) {
            if (s.startsWith("@")) {
                method.accept(s.replaceAll("[@{}]", ""));
            } else {
                atlasmapPage.addToConditionalMapping(s);
            }
        }
    }

    @Then("click on enable or disable conditional mapping expression button")
    public void clickAddMappingCondition() {
        atlasmapPage.toggleConditionalMapping();
    }

    @Then("check if warnings from {string} to {string} are displayed with messages")
    public void checkIfMultipleWarningsFromToDisplayed(String from, String to, DataTable sourceMappingData) {
        List<String> source = new ArrayList<>(sourceMappingData.asList());
        Assert.assertTrue(this.atlasmapPage.checkMultipleWarnings(source, from, to));
    }

    @Then("check if {string} warning from {string} to {string} is displayed")
    public void checkIfFromToDisplayed(String exceptionType, String from, String to) {
        List<String> source = new ArrayList<>(Arrays.asList(exceptionType));
        Assert.assertTrue(this.atlasmapPage.checkMultipleWarnings(source, from, to));
    }

    @Then("check if asymmetric mapping warning from {string} level to {string} level is displayed")
    public void checkIfAsymmetricMappingWarningIsDisplayed(String sourceLevel, String targetLevel) {
        Assert.assertTrue(this.atlasmapPage.checkAsymmetricMappingWarning(Integer.parseInt(sourceLevel), Integer.parseInt(targetLevel)));
    }

    @Then("check if warning for conditional mapping with multiple targets is displayed")
    public void checkIfAsymmetricMappingWarningIsDisplayed() {
        Assert.assertTrue(this.atlasmapPage.checkMultipleTargetsWarning());
    }

    @And("check if warning contains {string} message")
    public void checkIfWarningContainsMessage(String message) {
        Assert.assertTrue(this.atlasmapPage.checkWarningContainMessage(message));
    }

    @Then("check if {string} warning from {string} to {string} is not displayed")
    public void checkIfWarningFromToIsNotDisplayed(String exceptionType, String from, String to) {
        Assert.assertFalse(this.atlasmapPage.checkWarning(exceptionType, from, to));
    }

    @Then("check if no warnings are displayed")
    public void checkIfNoWarningsAreDisplayed() {
        this.atlasmapPage.checkWarnings();
    }

    @And("add {string} to combine")
    public void addToCombine(String field) {
        this.atlasmapPage.addToMappingUsingFieldPanel(field, true);
    }

    @And("add {string} to separate")
    public void addToSeparate(String field) {
        atlasmapPage.addToMappingUsingFieldPanel(field, false);
    }

    @When("delete current mapping")
    public void deleteCurrentMapping() {
        atlasmapPage.deleteCurrentMapping();
    }

    @And("reveal mapping details")
    public void revealMappingDetails() {
        atlasmapPage.clickOnLinkByClass(".fa.fa-edit.link");
    }

    @And("add mapping from {string} to {string}")
    public void addMappingFromTo(String from, String to) throws Throwable {
        atlasmapPage.createNewMapping(from, "source");
        atlasmapPage.addToMappingUsingFieldPanel(to, false);
        if (internalMapping) {
            this.validator.map(from, to);
        }
    }

    @And("add click {string} link")
    public void addClickLink(String arg0) {
        this.atlasmapPage.clickOnLinkByClass(".fa.fa-long-arrow-right");
    }

    @When("change select from {string} to {string}")
    public void changeSelectFromTo(String from, String to) {
        this.atlasmapPage.changeSelectValue(from, to);
    }

    @And("drag {string} and drop on {string}")
    public void dragAndDropOn(String drag, String drop) {
        this.atlasmapPage.dragNDrop(drag, drop);
    }

    @And("Show mapping preview")
    public void showMappingPreview() {
        this.atlasmapPage.clickOnLinkByDataTestId("show-hide-mapping-preview-button");
    }

    @And("set preview data")
    public void setPreviewData(DataTable values) {
        for (Map<String, String> data : values.asMaps()) {
            for (String key : data.keySet()) {
                final String value = data.get(key);
                log.info(key + " " + value);
                this.atlasmapPage.setInputValueForFieldPreview(key, value);
            }
        }
    }

    @Then("verify that {string} contains {string}")
    public void verifyThatContains(String field, String v) {
        String value = this.atlasmapPage.getFieldPreviewValue(field);
        Assert.assertEquals(v, value);
    }

    @Then("verify preview data")
    public void verifyPreviewData(DataTable values) {
        for (Map<String, String> data : values.asMaps()) {
            for (String key : data.keySet()) {
                final String value = data.get(key);
                String val = this.atlasmapPage.getFieldPreviewValue(key);
                Assert.assertEquals(value, val);
            }
        }
    }

    @And("set {string} for {string} field")
    public void setForField(String value, String field) {
        this.atlasmapPage.setInputValueForFieldPreview(field, value);
    }

    @And("check if danger warning contains {string} message")
    public void checkIfDangerWarningContainsMessage(String message) {
        Assert.assertTrue(this.atlasmapPage.checkDangerWarningContainMessage(message));
    }

    @And("set {string} constant with {string} value")
    public void setConstantWithValue(String type, String value) {
        atlasmapPage.addConstant(type, value);
    }

    @When("set {string} property of {string} type and {string} value")
    public void setPropertyWithTypeAndValue(String name, String type, String value) {
        atlasmapPage.addProperty(type, name, value);
    }

    @And("switch to mapping table view")
    public void mappingTableView() {
        atlasmapPage.clickOnLinkByDataTestId("show-hide-mapping-table-button");
    }

    @And("switch to column mapper view")
    public void columnMapperView() {
        atlasmapPage.clickOnLinkByDataTestId("show-column-mapper-button");
    }

    @Then("check that row number \"{int}\" contains {string} as sources, {string} as target and {string} as type")
    public void checkThatRowNumberContainsAsSourcesAsTagetAndAsType(int number, String sources, String targets, String mappingType) {
        assertThat(atlasmapPage.getLabelFromMappingTable(number, "type")).isEqualToIgnoringCase(mappingType);
        assertThat(atlasmapPage.getLabelFromMappingTable(number, "sources")).isEqualToIgnoringCase(sources);
        assertThat(atlasmapPage.getLabelFromMappingTable(number, "targets")).isEqualToIgnoringCase(targets);
    }

    @And("click on \"{int}\" index of table")
    public void clickOnIndexOfTable(int index) {
        atlasmapPage.clickOnRowInMappingTable(index);
    }

    @Then("check that on \"{int}\" row number is for {string} source value displayed {string} target preview")
    public void checkThatOnRowNumberIsForSourceValueSDisplayedTargetPreview(int index, String source, String target) {
        atlasmapPage.clickOnRowInMappingTable(index);
        atlasmapPage.setPreviewValueInTable(index, "sources", source.split(";"));
        atlasmapPage.clickOnRowInMappingTable(index);
        final String preview = atlasmapPage.getPreviewValueInTable(index, "targets");
        assertThat(preview).isEqualTo(target);
    }

    @And("open all subfolders")
    public void openAllSubfolders() {
        atlasmapPage.openAllSubfolders();
    }

    @And("open all data buckets named {string}")
    public void openAllDataBuckets(String bucket) {
        atlasmapPage.openAllBucketsWithName(bucket);
    }

    @And("set {string} as {string}")
    public void setAs(String field, String src) {
        this.atlasmapPage.addToMappingUsingFieldPanel(field, "source".equals(src));
    }

    @And("add {string} as {string}")
    public void addToMappingUsingMappingDetail(String field, String src) {
        this.atlasmapPage.addToMappingUsingMappingDetails(field, "source".equals(src));
    }

    @And("delete {string} on {string}")
    public void deleteOn(String field, String src) {
        this.atlasmapPage.deleteFromMapping(field, "source".equals(src));
    }

    @And("change index of {string} to \"{int}\" on {string}")
    public void changeIndexOfToOn(String field, int value, String src) {
        this.atlasmapPage.changeIndexValue(field, value, "source".equals(src));
    }

    @When("init mapping from {string} to {string}")
    public void initMappingFromTo(String target, String source) {

        this.atlasmapPage.dragNDrop(source, target);
        //TODO: drag the source to target - will initialize the mapping
    }

    @And("show mapping details of fiels {string}")
    public void setMappingNrAsActive(String mappingNr) {
        //TODO
    }

    @And("click on create new mapping")
    public void clickOnCreateNewMapping() {
        this.atlasmapPage.clickOnLinkByDataTestId("add-new-mapping-button");
    }

    @And("add source {string} to active mapping")
    public void addSourceToActiveMapping(String mappingField) {
        this.atlasmapPage.addToMappingUsingFieldPanel(mappingField, true);
    }

    @And("add target {string} to active mapping")
    public void addTargetToActiveMapping(String mappingField) {
        this.atlasmapPage.addToMappingUsingFieldPanel(mappingField, false);
    }

    @When("connect field {string} to active mapping")
    public void connectFieldToActiveMapping(String source) {
        this.atlasmapPage.addToMappingUsingFieldPanel(source, true);
    }

    @And("set mapping to {string} from {string}")
    public void userSetsMappingToFrom(String target, String source) throws Throwable {
        this.atlasmapPage.createNewMapping(source, "source");
        this.atlasmapPage.addToMappingUsingFieldPanel(target, false);
        if (internalMapping) {
            this.validator.map(source, target);
        }
    }

    @And("click on create new mapping from source {string}")
    public void clickOnCreateNewMappingFromSource(String mappingField) {

        atlasmapPage.createNewMapping(mappingField, "source");
    }

    @And("click on create new mapping from target {string}")
    public void clickOnCreateNewMappingFrom(String mappingField) {

        atlasmapPage.createNewMapping(mappingField, "target");
    }

    @And("click show target mapping for {string}")
    public void clickShowTargetMapping(String mappingField) {
        atlasmapPage.hoverAndSelectOperation(mappingField, HoverAction.SHOW_MAPPING_DETAILS, "target");
    }

    @And("click show source mapping for {string}")
    public void clickShowSourceMapping(String mappingField) {
        atlasmapPage.hoverAndSelectOperation(mappingField, HoverAction.SHOW_MAPPING_DETAILS, "source");
    }
}
