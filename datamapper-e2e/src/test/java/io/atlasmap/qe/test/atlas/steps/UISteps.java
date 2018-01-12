package io.atlasmap.qe.test.atlas.steps;

import org.junit.Assert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class UISteps extends CucumberGlue {

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
        this.atlasmapPage.selectTransformation(arg0);
        Thread.sleep(1000);

    }

    @And("^for \"([^\"]*)\" input set \"([^\"]*)\"$")
    public void putValueIn(String inputSelector, String inputValue) throws Throwable {
        this.atlasmapPage.setInputValueByClass(inputSelector,inputValue);
    }
}
