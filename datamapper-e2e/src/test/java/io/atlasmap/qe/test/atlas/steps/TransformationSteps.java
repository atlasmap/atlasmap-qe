package io.atlasmap.qe.test.atlas.steps;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import io.atlasmap.qe.test.atlas.AtlasMapPage;
import io.atlasmap.qe.test.atlas.utils.MappingUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import javax.inject.Inject;


@Slf4j
public class TransformationSteps {

    @Inject
    private AtlasMapPage atlasMapPage;

    @When("add {string} transformation on {string}")
    public void addTransformationOn(String transformation, String sourceTarget) {
        atlasMapPage.addTransformationToTargetOrSource(transformation, sourceTarget.equals("source") ? true : false);
    }

    @And("add transformation on target")
    public void addTransformationOnTarget() {
        this.atlasMapPage.clickOnTargets(".fa.fa-long-arrow-right");
    }

    @And("add {string} collection transformation")
    public void addCollectionTransformationOn(String transformation) {
        atlasMapPage.addCollectionTransformationOnField(transformation);
    }

    @And("select {string} transformation")
    public void selectTransformation(String arg0) {
        this.atlasMapPage.selectTransformation(arg0, "Append");
        MappingUtils.sleep(1000);
    }

    @When("select {string} number transformation")
    public void selectNumberTransformation(String arg0) {
        atlasMapPage.selectTransformation(arg0, "AbsoluteValue");
    }

    @When("select {string} separator")
    public void selectSeparator(String separator) {
        this.atlasMapPage.selectSeparator(separator);
        MappingUtils.sleep(1000);
    }

    @When("set index to {int}")
    public void setCopyToIndex(int index) {
        this.atlasMapPage.setCopyToIndex(index);
        MappingUtils.sleep(1000);
    }

    @When("set repeat count to {int}")
    public void setRepeatCount(int count) {
        this.atlasMapPage.setRepeatCount(count);
        MappingUtils.sleep(1000);
    }

    /**
     * Sets data required for transformation.
     *
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
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-index-input-field", value);
                break;
            case "String":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-string-input-field", value);
                break;
            case "Match":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-match-input-field", value);
                break;
            case "New String":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-newString-input-field", value);
                break;
            case "Start Index":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-startIndex-input-field", value);
                break;
            case "End Index":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-endIndex-input-field", value);
                break;
            case "Pad Character":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-padCharacter-input-field", value);
                break;
            case "Pad Count":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-padCount-input-field", value);
                break;
            case "Template":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-template-input-field", value);
                break;
            case "Days":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-days-input-field", value);
                break;
            case "Seconds":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-seconds-input-field", value);
                break;
            case "Value":
                this.atlasMapPage.setInputValueByDataTestid("insert-transformation-parameter-value-input-field", value);
                break;
            default:
                throw new IllegalStateException("Unsupported field for transformation: " + field);
        }
    }

    @When("change transformation from {string} to {string}")
    public void changeTransformationFromTo(String defaultValue, String newValue) {
        this.atlasMapPage.selectTransformation(newValue, defaultValue);
    }

    @And("set from {string} to {string} units")
    public void setFromToUnitsOn(String from, String to) {
        atlasMapPage.selectUnitFromOption(from);
        atlasMapPage.selectUnitToOption(to);
    }

    @Then("verify preview of {string} transformation from {string} with value {string} is transformed to {string} in {string}")
    public void verifyPreviewOfTransformationFromWithValueIsTransformedToIn(String transformation, String sourceField, String sourceValue,
        String targetValue, String targetField) throws InterruptedException {
        this.atlasMapPage.addTransformationToTargetOrSource(transformation, true);
        this.atlasMapPage.setInputValueForFieldPreview(sourceField, sourceValue);
        this.atlasMapPage.setInputValueForFieldPreview(sourceField, sourceValue);
        MappingUtils.sleep(1000);
        String preview = this.atlasMapPage.getFieldPreviewValue(targetField);
        Assert.assertEquals(targetValue, preview);
    }
}
