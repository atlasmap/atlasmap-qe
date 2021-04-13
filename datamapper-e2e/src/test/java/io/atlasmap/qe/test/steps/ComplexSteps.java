package io.atlasmap.qe.test.steps;

import io.atlasmap.qe.mapper.MappingValidator;
import io.atlasmap.qe.data.target.TargetMappingTestClass;
import io.atlasmap.qe.test.utils.HoverAction;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import io.atlasmap.qe.test.AtlasMapPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import javax.inject.Inject;


@Slf4j
public class ComplexSteps {

    @Inject
    private MappingValidator validator;

    @Inject
    private AtlasMapPage atlasMapPage;

    @Inject
    private TransformationSteps transformationSteps;

    @Inject
    private BackendSteps backendSteps;

    @When("verify in {string} transformation that  {string} is transformed to {string}")
    public void verifyInTransformationThatIsTransformedTo(String transformation, String input, String output) throws Throwable {
        transformationSteps.selectTransformation(transformation);
        this.validator.setSourceValue("sourceString", input);
        this.validator.setTargetValue("targetString", output);
        if ("Normalize".equals(transformation)) {
            this.validator.setSourceValue("sourceString", "foo \t bar");
        }
        backendSteps.userSavesMappingAs(transformation + ".json");
        backendSteps.verify(transformation + ".json");
    }

    @And("verify conversion from {string} in preview")
    public void verifyConversionFromInPreview(String field) throws Exception {
        final String[] targetFields = {"targetInteger", "targetBoolean", "targetByte", "targetChar",
            "targetDouble", "targetFloat", "targetLong", "targetShort",
            "targetString"};

        String s = this.validator.getSourceValue(field).toString();
        this.atlasMapPage.setInputValueForFieldPreview(field, s);
        backendSteps.userSavesMappingAs("from_" + field + ".json");
        this.validator.setMappingLocation("from_" + field + ".json");
        TargetMappingTestClass target = this.validator.processMapping();

        for (String targetField : targetFields) {
            this.atlasMapPage.hoverAndSelectOperation(targetField, HoverAction.SHOW_MAPPING_DETAILS, "target");
            String targetMapped = validator.getValueOfBeanProperty(target, targetField).toString();
            if (!targetField.contains("String")) {
                targetMapped = (targetMapped.endsWith(".0") ? targetMapped.replace(".0", "") : targetMapped);
            }
            final String targetPreview = this.atlasMapPage.getFieldPreviewValue(targetField);
            System.out.println(targetField + " " + targetMapped + " " + targetPreview);
            Assert.assertEquals(targetMapped, targetPreview);
        }
    }

    @Then("verify in {string} on {string} transformation that  {string} is transformed to {string}")
    public void verifyInOnTransformationThatIsTransformedTo(String transformation, String source, String input, String output) throws Throwable {
        transformationSteps.addTransformationOn(transformation, "source");
        this.validator.setSourceValue("sourceString", input);
        this.validator.setTargetValue("targetString", output);
        if ("Normalize".equals(transformation)) {
            this.validator.setSourceValue("sourceString", "foo \t bar");
        }
        backendSteps.userSavesMappingAs(transformation + ".json");
        backendSteps.verify(transformation + ".json");
    }
}
