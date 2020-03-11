package io.atlasmap.qe.test.atlas.steps;

import org.junit.Assert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.atlasmap.qe.test.atlas.AtlasmapPage;

public class TransformationSteps extends CucumberGlue {
    private AtlasmapPage atlasmapPage = new AtlasmapPage();

    @When("add transformation on {string}")
    public void addTransformationOn(String sourceTarget) {
        final boolean isSource = sourceTarget.equals("source");
        atlasmapPage.addTransformationToTargetOrSource("Capitalize", isSource);
    }

    @When("add {string} transformation on {string}")
    public void addTransformationOn(String transformation, String sourceTarget) {
        final boolean isSource = sourceTarget.equals("source");
        atlasmapPage.addTransformationToTargetOrSource(transformation, isSource);
    }

    @And("add transformation on target")
    public void addTransformationOnTarget() {
        this.atlasmapPage.clickOnTargets(".fa.fa-long-arrow-right");
    }

    @And("add {string} collection transformation")
    public void addCollectionTransformationOn(String transformation) {
        atlasmapPage.addCollectionTransformation(transformation);
    }

    @And("select {string} transformation")
    public void selectTransformation(String arg0) throws Throwable {
        this.atlasmapPage.selectTransformation(arg0, "Append");
        Thread.sleep(1000);
    }

    @When("select {string} number transformation")
    public void selectNumberTransformation(String arg0) {
        atlasmapPage.selectTransformation(arg0, "AbsoluteValue");
    }

    @When("select {string} separator")
    public void selectSeparator(String separator) throws Throwable {
        this.atlasmapPage.selectSeparator(separator);
        Thread.sleep(1000);
    }

    /**
     * Sets data required for transformation.
     * @param field required by transformation
     * @param value of field
     */
    @And("set {string} for transformation to {string}")
    public void setDataForTransformation(String field, String value) {
        if (field.contains("N/A") && value.contains("N/A")) {
            return;
        }
        switch (field) {
            case "Index":
                this.atlasmapPage.setInputValueByClass("input-index", value);
                break;
            case "String":
                this.atlasmapPage.setInputValueByClass("input-string", value);
                break;
            case "Match":
                this.atlasmapPage.setInputValueByClass("input-match", value);
                break;
            case "New String":
                this.atlasmapPage.setInputValueByClass("input-newString", value);
                break;
            case "Start Index":
                this.atlasmapPage.setInputValueByClass("input-startIndex", value);
                break;
            case "End Index":
                this.atlasmapPage.setInputValueByClass("input-endIndex", value);
                break;
            case "Pad Character":
                this.atlasmapPage.setInputValueByClass("input-padCharacter", value);
                break;
            case "Pad Count":
                this.atlasmapPage.setInputValueByClass("input-padCount", value);
                break;
            case "Template":
                this.atlasmapPage.setInputValueByClass("input-template", value);
                break;
            case "Days":
                this.atlasmapPage.setInputValueByClass("input-days", value);
                break;
            case "Seconds":
                this.atlasmapPage.setInputValueByClass("input-seconds", value);
                break;
            case "Value":
                this.atlasmapPage.setInputValueByClass("input-value", value);
                break;
            default:
                throw new IllegalStateException("Unsupported field for transformation: " + field);
        }
    }

    @When("change transformation from {string} to {string}")
    public void changeTransformationFromTo(String defaultValue, String newValue) {
        this.atlasmapPage.selectTransformation(newValue, defaultValue);
    }

    @And("set from {string} to {string} units on {string}")
    public void setFromToUnitsOn(String from, String to, String sourceTarget) {
        final boolean isSource = sourceTarget.equals("source");
        atlasmapPage.selectOptionOnIndex(from, 1, isSource);
        atlasmapPage.selectOptionOnIndex(to, 2, isSource);
    }

    @Then("verify preview of {string} transformation from {string} with value {string} is transformed to {string} in {string}")
    public void verifyPreviewOfTransformationFromWithValueIsTransformedToIn(String transformation, String sourceField, String sourceValue, String targetValue, String targetField) {
        this.atlasmapPage.addTransformationToTargetOrSource(transformation, true);
        this.atlasmapPage.setInputValueForFieldPreview(sourceField, sourceValue);
        this.atlasmapPage.setInputValueForFieldPreview(sourceField, sourceValue);
        this.atlasmapPage.clickOn(targetField);
        String preview = this.atlasmapPage.getFieldPreviewValue(targetField);
        Assert.assertEquals(targetValue, preview);
    }
}
