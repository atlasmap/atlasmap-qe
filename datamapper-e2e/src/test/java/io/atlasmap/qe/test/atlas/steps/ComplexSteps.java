package io.atlasmap.qe.test.atlas.steps;

import org.junit.Assert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.atlasmap.qe.test.TargetMappingTestClass;


public class ComplexSteps extends CucumberGlue {

    private static UISteps uiSteps = new UISteps();
    private static BackendSteps backendSteps = new BackendSteps();

    @When("^verify in \"([^\"]*)\" transformation that  \"([^\"]*)\" is transformed to \"([^\"]*)\"$")
    public void verifyInTransformationThatIsTransformedTo(String transformation, String input, String output) throws Throwable {
        uiSteps.selectTransformation(transformation);
        this.validator.setSourceValue("sourceString", input);
        this.validator.setTargetValue("targetString", output);
        if ("Normalize".equals(transformation)) {
            this.validator.setSourceValue("sourceString", "foo \t bar");
        }
        backendSteps.userSavesMappingAs(transformation + ".json");
        backendSteps.verify(transformation + ".json");
    }

    @And("^verify conversion from \"([^\"]*)\" in preview$")
    public void verifyConversionFromInPreview(String field) throws Exception {
        final String[] targetFields = {"targetInteger", "targetBoolean", "targetByte", "targetChar",
                "targetDouble", "targetFloat", "targetLong", "targetShort",
                "targetString"};

        String s = this.validator.getSourceValue(field).toString();
        this.atlasmapPage.setInputValueForFieldPreview(field, s);
        backendSteps.userSavesMappingAs("from_" + field + ".json");
        this.validator.setMappingLocation("from_" + field + ".json");
        TargetMappingTestClass target = this.validator.processMapping();

        for (String targetField : targetFields) {
            this.atlasmapPage.clickOn(targetField);
            String targetMapped = target.getValue(targetField).toString();
            if (!targetField.contains("String")) {
                targetMapped = (targetMapped.endsWith(".0") ? targetMapped.replace(".0", "") : targetMapped);
            }
            final String targetPreview = this.atlasmapPage.getFieldPreviewValue(targetField);
            System.out.println(targetField + " " + targetMapped + " " + targetPreview);
            Assert.assertEquals(targetMapped, targetPreview);
        }
    }

    @Then("^verify in \"([^\"]*)\" on \"([^\"]*)\" transformation that  \"([^\"]*)\" is transformed to \"([^\"]*)\"$")
    public void verifyInOnTransformationThatIsTransformedTo(String transformation, String source, String input, String output) throws Throwable {
        uiSteps.addTransformationOn(transformation,source);
        this.validator.setSourceValue("sourceString", input);
        this.validator.setTargetValue("targetString", output);
        if ("Normalize".equals(transformation)) {
            this.validator.setSourceValue("sourceString", "foo \t bar");
        }
        backendSteps.userSavesMappingAs(transformation + ".json");
        backendSteps.verify(transformation + ".json");
    }
}
