package io.atlasmap.qe.test.atlas.steps;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import com.codeborne.selenide.WebDriverRunner;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UISteps extends CucumberGlue {
    private static String previousSelected = "";

    private Scenario myScenario;

    @Before()
    public void embedScreenshotStep(Scenario scenario) {

        myScenario = scenario;

    }

    @When("^set mapping from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void userSsetsMappingFromTo(String source, String target) throws Exception {
        this.atlasmapPage.clickOn(source);
        this.atlasmapPage.clickOn(target);
        if (internalMapping) {
            this.validator.map(source, target);
        }
    }

    @Then("^browser is opened$")
    public void userOpensBrowser() throws Exception {
        atlasmapPage.openBrowser();
    }

    @And("^set mapping to \"([^\"]*)\" from \"([^\"]*)\"$")
    public void userSetsMappingToFrom(String target, String source) throws Throwable {
        this.atlasmapPage.clickOn(target);
        this.atlasmapPage.clickOn(source);
        if (this.internalMapping) {
            this.validator.map(source, target);
        }
    }

    @Then("^check if \"([^\"]*)\" warning from \"([^\"]*)\" to \"([^\"]*)\" is displayed$")
    public void checkIfFromToDisplayed(String exceptionType, String from, String to) {
        Assert.assertTrue(this.atlasmapPage.checkWarning(exceptionType, from, to));
    }

    @And("^check if warning contains \"([^\"]*)\" message$")
    public void checkIfWarningContainsMessage(String message) throws Throwable {
        Assert.assertTrue(this.atlasmapPage.checkWarningContainMessage(message));
    }

    @Then("^check if \"([^\"]*)\" warning from \"([^\"]*)\" to \"([^\"]*)\" is not displayed$")
    public void checkIfWarningFromToIsNotDisplayed(String exceptionType, String from, String to) {
        Assert.assertFalse(this.atlasmapPage.checkWarning(exceptionType, from, to));
    }

    @Then("^check if no warnings are displayed$")
    public void checkIfNoWarningsAreDisplayed() {
        this.atlasmapPage.checkWarnings();
    }

    @And("^add click \"([^\"]*)\" button$")
    public void addClickButton(String arg0) throws Throwable {
        this.atlasmapPage.clickOnButtonByText(arg0);
    }

    @And("^select \"([^\"]*)\" transformation$")
    public void selectTransformation(String arg0) throws Throwable {
        this.atlasmapPage.selectTransformation(arg0, "Append");
        Thread.sleep(1000);

    }

    @And("^for \"([^\"]*)\" input set \"([^\"]*)\"$")
    public void putValueIn(String inputSelector, String inputValue) throws Throwable {
        this.atlasmapPage.setInputValueByClass(inputSelector, inputValue);
    }

    @When("^click on \"([^\"]*)\"$")
    public void clickOn(String arg0) throws Throwable {
        this.atlasmapPage.clickOn(arg0);
    }

    @And("^add select \"([^\"]*)\" action$")
    public void addSelectAction(String action) throws Throwable {
        this.atlasmapPage.selectAction(action);
    }

    @And("^for \"([^\"]*)\" id input set \"([^\"]*)\"$")
    public void forIdInputSet(String id, String value) throws Throwable {
        this.atlasmapPage.setInputValueById(id, value);
    }

    @And("^add \"([^\"]*)\" to combine$")
    public void addToCombine(String field) throws Throwable {
        addClickButton("Add Source");
        forIdInputSet("input-source-", field);
    }

    @When("^select \"([^\"]*)\" separator$")
    public void selectSeparator(String separator) throws Throwable {
        this.atlasmapPage.selectSeparator(separator);
        Thread.sleep(1000);
    }

    @And("^for \"([^\"]*)\" input with \"([^\"]*)\" set \"([^\"]*)\"$")
    public void forInputWithSet(String id, String def, String value) throws Throwable {
        this.atlasmapPage.setInputValueByClassAndDefaultValue(id, def, value);
    }

    @And("^add \"([^\"]*)\" to separate$")
    public void addToSeparate(String field) throws Throwable {
        addClickButton("Add Target");
        forIdInputSet("input-target-", field);
    }

    @When("^select \"([^\"]*)\" number transformation$")
    public void selectNumberTransformation(String arg0) throws Throwable {
        atlasmapPage.selectTransformation(arg0, "AbsoluteValue");

    }

    @And("^open mapping details window$")
    public void openMappingDetailsWindow() throws Throwable {
        atlasmapPage.openMappingDetails();
    }

    @When("^delete current mapping$")
    public void deleteCurrentMapping() throws Throwable {
        atlasmapPage.deleteCurrent();
    }

    @And("^reveal mapping details$")
    public void revealMappingDetails() throws Throwable {
        atlasmapPage.clickOnLinkByClass(".fa.fa-edit.link");
    }

    @When("^change transformation from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void changeTransformationFromTo(String defaultValue, String newValue) throws Throwable {
        this.atlasmapPage.selectTransformation(newValue, defaultValue);
    }

    @And("^add mapping from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void addMappingFromTo(String from, String to) throws Throwable {
        atlasmapPage.clickOnLinkByClass(".fa.fa-plus.link");
        forIdInputSet("input-source-", from);
        forIdInputSet("input-target-", to);
      //  Utils.waitAndVerifyMappingIsWritten(from,to);
    }

    @And("^add click \"([^\"]*)\" link$")
    public void addClickLink(String arg0) {
        this.atlasmapPage.clickOnLinkByClass(".fa.fa-long-arrow-right");
    }

    @And("^for \"([^\"]*)\" id input with \"([^\"]*)\" set \"([^\"]*)\"$")
    public void forIdInputWithSet(String id, String def, String value) {
        this.atlasmapPage.setInputValueByIdAndDefaultValue(id, def, value);
    }

    @When("^change select from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void changeSelectFromTo(String from, String to) {
        this.atlasmapPage.changeSelectValue(from, to);
    }

    @When("^click on \"([^\"]*)\" holding cmd button$")
    public void clickOnHoldingCmdButton(String id) {
        this.atlasmapPage.clickOnWhileHolding(id, "cmd");
    }

    @And("^drag \"([^\"]*)\" and drop on \"([^\"]*)\"$")
    public void dragAndDropOn(String drag, String drop) {
        this.atlasmapPage.dragNDrop(drag, drop);
    }

    @And("^Show mapping preview$")
    public void showMappingPreview() {

        this.atlasmapPage.clickOnLinkByClass(".fa.fa-cog.link");
        this.atlasmapPage.clickOnElementByText("a", "Show Mapping Preview ");
    }

    @And("^set preview data$")
    public void setPreviewData(DataTable values) {
        for (Map<String, String> data : values.asMaps(String.class, String.class)) {
            for (String key : data.keySet()) {
                final String value = data.get(key);
                System.out.println(key + " " + value);
                this.atlasmapPage.setInputValueForFieldPreview(key, value);
            }

        }
    }

    @Then("^verify that \"([^\"]*)\" contains \"([^\"]*)\"$")
    public void verifyThatContains(String field, String v) {
        String value = this.atlasmapPage.getFieldPreviewValue(field);
        Assert.assertEquals(v, value);

    }

    @Then("^verify preview data$")
    public void verifyPreviewData(DataTable values) {
        for (Map<String, String> data : values.asMaps(String.class, String.class)) {
            for (String key : data.keySet()) {
                final String value = data.get(key);
                System.out.println(key + " " + value);
                String val = this.atlasmapPage.getFieldPreviewValue(key);
                Assert.assertEquals(value, val);
            }
        }
    }

    @And("^set \"([^\"]*)\" for \"([^\"]*)\" field$")
    public void setForField(String value, String field) {
        this.atlasmapPage.setInputValueForFieldPreview(field, value);
    }

    @Then("^verify preview of \"([^\"]*)\" transformation from \"([^\"]*)\" with value \"([^\"]*)\" is transformed to \"([^\"]*)\" in \"([^\"]*)\"$")
    public void verifyPreviewOfTransformationFromWithValueIsTransformedToIn(String transformation, String sourceField, String sourceValue, String targetValue, String targetField) throws Throwable {
        this.atlasmapPage.selectTransformation(transformation, ("".equals(previousSelected) ? "Append" : previousSelected));
        this.atlasmapPage.setInputValueForFieldPreview(sourceField, sourceValue);
        this.atlasmapPage.setInputValueForFieldPreview(sourceField, sourceValue);
        this.atlasmapPage.clickOn(targetField);
        String preview = this.atlasmapPage.getFieldPreviewValue(targetField);
        Assert.assertEquals(targetValue, preview);
        UISteps.previousSelected = transformation;
    }

    @And("^check if danger warning contains \"([^\"]*)\" message$")
    public void checkIfDangerWarningContainsMessage(String message) throws Throwable {
       Assert.assertTrue(this.atlasmapPage.checkDangerWarningContainMessage(message));
    }

    @And("^add transformation on target$")
    public void addTransformationOnTarget() {
        this.atlasmapPage.clickOnTargets(".fa.fa-long-arrow-right");
    }


    @Then ("^take a screenshot$")
    public void takeAscreenshot() throws Throwable {

        try {
            myScenario.write("Current Page URL is " + getWebDriver().getCurrentUrl());
            byte[] screenshot = ((TakesScreenshot)getWebDriver()).getScreenshotAs(OutputType.BYTES);
            myScenario.embed(screenshot, "image/png");  // Stick it in the report
        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
            //log.error(somePlatformsDontSupportScreenshots.getMessage());
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }
    @After
    public void closeDriver() {
        try {
            takeAscreenshot();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        WebDriverRunner.closeWebDriver();
    }

    @And("^set \"([^\"]*)\" constant with \"([^\"]*)\" value$")
    public void setConstantWithValue(String type, String value) throws Throwable {
        atlasmapPage.addConstant(type,value);
    }

    @When("^set \"([^\"]*)\" property of \"([^\"]*)\" type and \"([^\"]*)\" value$")
    public void setPropertyWithTypeAndValue(String name, String type, String value) throws Throwable {
        atlasmapPage.addProperty(type,name,value);
    }
}
