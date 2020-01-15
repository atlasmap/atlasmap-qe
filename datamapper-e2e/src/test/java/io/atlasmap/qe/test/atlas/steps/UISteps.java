package io.atlasmap.qe.test.atlas.steps;

import static org.assertj.core.api.Assertions.assertThat;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import org.junit.Assert;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.util.Map;
import java.util.function.Consumer;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.atlasmap.qe.test.atlas.AtlasmapPage;
import io.atlasmap.qe.test.atlas.utils.Utils;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UISteps extends CucumberGlue {
    private static String previousSelected = "";

    private Scenario myScenario;
    private AtlasmapPage atlasmapPage = new AtlasmapPage();
    private static boolean internalMapping = true;

    @Before()
    public void embedScreenshotStep(Scenario scenario) {
        myScenario = scenario;
    }

    @After
    public void closeDriver() {
        try {
            takeAscreenshot();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Given("atlasmap contains TestClass")
    public void atlasmapContainsTestClass() throws Exception {
        String resp = Utils.requestClass(atlasmapPage.TEST_CLASS);
        assertThat(resp).contains(atlasmapPage.TEST_CLASS);
    }

    @When("set mapping from {string} to {string}")
    public void userSsetsMappingFromTo(String source, String target) throws Exception {
        this.atlasmapPage.clickOn(source);
        this.atlasmapPage.clickOn(target);
        if (internalMapping) {
            this.validator.map(source, target);
        }
    }

    @Then("browser is opened")
    public void userOpensBrowser() throws Exception {
        atlasmapPage.refreshPage();
    }

    @And("set mapping to {string} from {string}")
    public void userSetsMappingToFrom(String target, String source) throws Throwable {
        this.atlasmapPage.clickOn(target);
        this.atlasmapPage.clickOn(source);
        if (internalMapping) {
            this.validator.map(source, target);
        }
    }

    @And("internal mapping is skipped")
    public void internalMappingIsSkipped() throws Throwable {
        internalMapping = false;
    }

    @And("internal mapping is set to {string}")
    public void internalMappingIsSetTo(String mapping) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        internalMapping = "true".equals(mapping);
    }

    @And("set mapping condition to {string} by Control key")
    public void setMappingConditionByCtrl(String condition) {
        setMappingConditionTo(condition, this::clickOnHoldingCmdButton);
    }

    @And("set mapping condition to {string} by drag and drop")
    public void setMappingConditionByDragAndDrop(String condition) {
        // TODO
    }

    @And("set mapping condition to {string} by auto completion")
    public void setMappingConditionByAutoCompletion(String condition) {
        setMappingConditionTo(condition, s -> {
            atlasmapPage.addToConditionalMapping("@" + s);
            atlasmapPage.clickOnValueFromPicker("conditional-expr-picker", s);
        });
    }

    @And("set mapping condition to {string} by selecting sources")
    public void setMappingConditionBySelectingSources(String condition) {
        setMappingConditionTo(condition, s -> {
            try {
                atlasmapPage.addToMapping(s, true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void setMappingConditionTo(String condition, Consumer<String> method) {
        atlasmapPage.toggleConditionalMapping();
        for (String s : condition.split("((?<=@\\{\\w{0,100}\\})|(?=@\\{\\w{0,100}\\}))")) {
            if (s.startsWith("@")) {
                method.accept(s.replaceAll("[@{}]", ""));
            } else {
                atlasmapPage.addToConditionalMapping(s);
            }
        }
    }

    @Then("check if {string} warning from {string} to {string} is displayed")
    public void checkIfFromToDisplayed(String exceptionType, String from, String to) {
        //TODO fix this
        //  Assert.assertTrue(this.atlasmapPage.checkWarning(exceptionType, from, to));
    }

    @And("check if warning contains {string} message")
    public void checkIfWarningContainsMessage(String message) throws Throwable {
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

    @And("add click {string} button")
    public void addClickButton(String arg0) throws Throwable {
        this.atlasmapPage.clickOnButtonByText(arg0);
    }

    @When("click on {string}")
    public void clickOn(String arg0) throws Throwable {
        this.atlasmapPage.clickOn(arg0);
    }

    @And("add select {string} action")
    public void addSelectAction(String action) throws Throwable {
        this.atlasmapPage.selectAction(action);
    }

    @And("for {string} id input set {string}")
    public void forIdInputSet(String id, String value) throws Throwable {
        this.atlasmapPage.setInputValueById(id, value);
    }

    @And("add {string} to combine")
    public void addToCombine(String field) throws Throwable {
        this.atlasmapPage.addToMapping(field, true);
    }

    @And("for {string} input with {string} set {string}")
    public void forInputWithSet(String id, String def, String value) throws Throwable {
        this.atlasmapPage.setInputValueByClassAndDefaultValue(id, def, value);
    }

    @And("add {string} to separate")
    public void addToSeparate(String field) throws Throwable {
        atlasmapPage.addToMapping(field,false);
        //atlasmapPage.verifyThatIpnutExist("input-target-" + field);
    }

    @And("open mapping details window")
    public void openMappingDetailsWindow() {
        atlasmapPage.openMappingDetails();
    }

    @When("delete current mapping")
    public void deleteCurrentMapping() {
        try {
            atlasmapPage.deleteCurrent();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @And("reveal mapping details")
    public void revealMappingDetails() {
        atlasmapPage.clickOnLinkByClass(".fa.fa-edit.link");
    }

    @And("add mapping from {string} to {string}")
    public void addMappingFromTo(String from, String to) throws Throwable {
        atlasmapPage.clickOnLinkByClass(".fa.fa-plus.link");
        atlasmapPage.addToMapping(from, true);
        atlasmapPage.addToMapping(to, false);
    }

    @And("add click {string} link")
    public void addClickLink(String arg0) {
        this.atlasmapPage.clickOnLinkByClass(".fa.fa-long-arrow-right");
    }

    @And("for {string} id input with {string} set {string}")
    public void forIdInputWithSet(String id, String def, String value) {
        this.atlasmapPage.setInputValueByIdAndDefaultValue(id, def, value);
    }

    @When("change select from {string} to {string}")
    public void changeSelectFromTo(String from, String to) {
        this.atlasmapPage.changeSelectValue(from, to);
    }

    @When("click on {string} holding cmd button")
    public void clickOnHoldingCmdButton(String id) {
        this.atlasmapPage.clickOnWhileHolding(id, "cmd");
    }

    @And("drag {string} and drop on {string}")
    public void dragAndDropOn(String drag, String drop) {
        this.atlasmapPage.dragNDrop(drag, drop);
    }

    @And("Show mapping preview")
    public void showMappingPreview() {

        this.atlasmapPage.clickOnLinkByClass(".fa.fa-cog.link");
        this.atlasmapPage.clickOnElementByText("a", "Show Mapping Preview ");
    }

    @And("set preview data")
    public void setPreviewData(DataTable values) {
        for (Map<String, String> data : values.asMaps()) {
            for (String key : data.keySet()) {
//                try {
//               //     Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                final String value = data.get(key);
                System.out.println(key + " " + value);
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
//            data.keySet().stream()
//                    .map(k -> this.atlasmapPage.getFieldPreviewValue(data.get(k)))
//                    .fo
    }

    @And("set {string} for {string} field")
    public void setForField(String value, String field) {
        this.atlasmapPage.setInputValueForFieldPreview(field, value);
    }

    @And("check if danger warning contains {string} message")
    public void checkIfDangerWarningContainsMessage(String message) {
        Assert.assertTrue(this.atlasmapPage.checkDangerWarningContainMessage(message));
    }

    @Then("take a screenshot")
    public void takeAscreenshot() {

        try {
            myScenario.write("Current Page URL is " + getWebDriver().getCurrentUrl());
            byte[] screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
            myScenario.embed(screenshot, "image/png");  // Stick it in the report
        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
            //log.error(somePlatformsDontSupportScreenshots.getMessage());
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }

    @And("set {string} constant with {string} value")
    public void setConstantWithValue(String type, String value) {
        atlasmapPage.addConstant(type, value);
    }

    @When("set {string} property of {string} type and {string} value")
    public void setPropertyWithTypeAndValue(String name, String type, String value) {
        atlasmapPage.addProperty(type, name, value);
    }

    @And("reveal mapping table")
    public void revealMappingTable() throws Throwable {
        atlasmapPage.clickOnLinkByClass(".fa.fa-table.link");
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
    public void checkThatOnRowNumberIsForSourceValueSDisplayedTargetPreview(int index, String source, String target) throws Throwable {
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

    @And("open data bucket {string}")
    public void openDataBucket(String bucket) {
        atlasmapPage.openBucket(bucket);
    }

    @And("open all data buckets named {string}")
    public void openAllDataBuckets(String bucket) {
        atlasmapPage.openAllBucketsWithName(bucket);
    }

    @And("set {string} as {string}")
    public void setAs(String field, String src) throws Throwable {
        this.atlasmapPage.addToMapping(field,"source".equals(src));
    }

    @And("delete {string} on {string}")
    public void deleteOn(String field, String src) {
        this.atlasmapPage.deleteFromMapping(field, "source".equals(src));
    }

    @And("change index of {string} to \"{int}\" on {string}")
    public void changeIndexOfToOn(String field, int value, String src) throws Throwable {
        this.atlasmapPage.changeIndexValue(field, value, "source".equals(src));
    }
}
