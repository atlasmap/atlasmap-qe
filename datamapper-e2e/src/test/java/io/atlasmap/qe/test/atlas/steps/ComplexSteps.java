package io.atlasmap.qe.test.atlas.steps;

import cucumber.api.java.en.When;

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
        backendSteps.userSavesMappingAs(transformation + ".xml");
        backendSteps.verify(transformation + ".xml");

    }
}
