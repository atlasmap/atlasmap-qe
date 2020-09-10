package io.atlasmap.qe.test.atlas.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Assert;

import org.openqa.selenium.NotFoundException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.atlasmap.qe.resources.ResourcesGenerator;
import io.atlasmap.qe.test.DatesObject;
import io.atlasmap.qe.test.SmallMappingTestClass;
import io.atlasmap.qe.test.SourceListsClass;
import io.atlasmap.qe.test.SourceNestedCollectionClass;
import io.atlasmap.qe.test.StringObject;
import io.atlasmap.qe.test.TargetListsClass;
import io.atlasmap.qe.test.TargetMappingTestClass;
import io.atlasmap.qe.test.TargetNestedCollectionClass;
import io.atlasmap.qe.test.atlas.utils.Utils;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BackendSteps extends CucumberGlue {

    @Given("atlasmap is clean")
    public void atlasmapIsClean() {
        try {
            Utils.deleteMappingsFromFolder();
            Utils.restoreAdmFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        validator.initializeValues();
    }

    @Then("save mapping as {string}")
    public void userSavesMappingAs(String arg1) throws Exception {
        Utils.sleep(1000);
        String mappingLocation = Utils.moveMapping(arg1);
        validator.setMappingLocation(arg1);
    }

    @And("verify {string}")
    public void verify(String arg0) throws Throwable {
        assertThat(validator.verifyMapping()).isTrue();
    }

    @When("set source data")
    public void setsSourceData(DataTable sourceMappingData) throws Throwable {
        //SourceMappingTestClass source = new SourceMappingTestClass();
        for (Map<String, String> source : sourceMappingData.asMaps()) {
            for (String field : source.keySet()) {
                this.validator.setSourceValue(field, source.get(field));
            }
        }
    }

    @And("set expected data")
    public void setsExpectedData(DataTable targetMappingData) throws Throwable {
        for (Map<String, String> source : targetMappingData.asMaps()) {
            for (String field : source.keySet()) {

                if (field.contains("Char")) {
                    this.validator.setTargetValue(field, source.get(field).charAt(0));
                } else {
                    this.validator.setTargetValue(field, source.get(field));
                }
            }
        }
    }

    @And("verify if {string} is not {string} in {string}")
    public void verifyIfIsNotIn(String field, String value, String path) throws Throwable { //
        // Assert.assertTrue(validator.verifyMapping());
        TargetMappingTestClass processed = this.validator.processMapping();
        assertThat(processed.getValue(field)).isNotEqualTo(value);
    }

    @Then("save and verify mapping as {string}")
    public void saveAndVerifyMappingAs(String arg0) throws Throwable {
        userSavesMappingAs(arg0);
        assertThat(validator.verifyMapping()).isTrue();
    }

    @And("set {string} value in source's {string}")
    public void setValueInSource(String value, String field) throws Throwable {
        this.validator.setSourceValue(field, value);
    }

    @And("set {string} value in target's {string}")
    public void setValueInTarget(String value, String field) throws Throwable {
        if (field.contains("Char")) {
            this.validator.setTargetValue(field, value.charAt(0));
        } else {
            this.validator.setTargetValue(field, value);
        }
    }

    @And("save and verify {string} with")
    public void verifyValuesPositive(String mapping, DataTable testValues) throws Exception {
        verifyMultipleValues(mapping, testValues, true);
    }

    private void verifyMultipleValues(String mapping, DataTable testValues, Boolean checkIfTrue) throws Exception {
        userSavesMappingAs(mapping);

        String sourceField = testValues.row(0).get(0);
        String targetField = testValues.row(0).get(1);

        for (Map<String, String> data : testValues.asMaps()) {
            this.validator.setSourceValue(sourceField, data.get(sourceField));
            this.validator.setTargetValue(targetField, data.get(targetField));
            assertThat(this.validator.verifyMapping(checkIfTrue)).isEqualTo(checkIfTrue);
        }
    }

    @And("sleep for \"{int}\"")
    public void sleepFor(int arg0) {
        // Write code here that turns the phrase above into concrete actions
        Utils.sleep(arg0);
    }

    @Then("save {string} verify negative with")
    public void saveVerifyNegativeWith(String mapping, DataTable testValues) throws Throwable {
        verifyMultipleValues(mapping, testValues, false);
    }

    @And("Add StringObject to expected map with {string}, {string} values")
    public void addStringObjectToExpedtedMapWithAndValues(String arg0, String arg1) throws Throwable {
        StringObject so = new StringObject();
        so.setFirstName(arg0);
        so.setLastName(arg1);

        validator.getExpectedMap().put(so.getClass().getName(), so);
    }

    @Then("save and verify mapping with multiple objects as {string}")
    public void saveAndVerifyMappingWithMultipleObjectsAs(String arg0) throws Throwable {
        userSavesMappingAs(arg0);
        assertThat(validator.verifyMultiObjectMapping()).isTrue();
    }

    @And("Init smallMappingTestClass and add to source map")
    public void initSmallMappingTestClassAndAddToSourceMap() throws Throwable {
        final SmallMappingTestClass s = new SmallMappingTestClass();
        validator.addSource(s.getClass().getName(), s);
    }

    @Then("save and verify repeating mapping of ListClasses as {string}")
    public void saveAndVerifyMappingOfListClassesAs(String mapping) throws Throwable {
        userSavesMappingAs(mapping);

        SourceListsClass slc = new SourceListsClass();
        TargetListsClass tlc = (TargetListsClass) validator.processSingleObjectMapping(slc, TargetListsClass.class.getName());
        tlc.getObjects().forEach(s -> System.out.println(s.toString()));
        for (int i = 0; i < slc.getObjects().size(); i++) {
            final StringObject src = slc.getObjects().get(i);
            final StringObject tgt = tlc.getObjects().get(i);

            assertThat(src.getFirstName()).isEqualTo(tgt.getLastName());
            assertThat(src.getLastName()).isEqualTo(tgt.getFirstName());
        }
    }

    @And("init DateObject {string}")
    public void initDateObject(String date) throws Throwable {
        DatesObject d = new DatesObject(date);
        // System.out.println(d.toString());

        this.validator.addSource(d.getClass().getName(), d);
        //System.out.println(this.validator.getSource(d.getClass().getName()));

    }

    @Then("save and verify mapping from {string} to datetypes as {string}")
    public void saveAndVerifyMappingFromToDatetypesAs(String source, String mapping) throws Throwable {
        userSavesMappingAs(mapping);
        TargetMappingTestClass t = (TargetMappingTestClass) validator.processMapping(TargetMappingTestClass.class.getName());
        DatesObject d = t.getDateObjectVariable();
        DatesObject sourceDate = (DatesObject) this.validator.getSource(DatesObject.class.getName());

        assertThat(sourceDate.getStandardJavaDate()).isEqualTo(d.getStandardJavaDate());
        assertThat(sourceDate.getCalendar().getTimeInMillis()).isEqualTo(d.getCalendar().getTimeInMillis());
        assertThat(sourceDate.getLocalDate()).isEqualTo(d.getLocalDate());
        assertThat(sourceDate.getLocalDateTime()).isEqualTo(d.getLocalDateTime());
        assertThat(sourceDate.getLocalTime()).isEqualTo(d.getLocalTime());
        assertThat(sourceDate.getTime()).isEqualTo(d.getTime());
        assertThat(sourceDate.getTimestamp()).isEqualTo(d.getTimestamp());
        assertThat(sourceDate.getZonedDateTime()).isEqualTo(d.getZonedDateTime());
        assertThat(sourceDate.getSqlDate().toString()).isEqualTo(d.getSqlDate().toString());
        assertThat(sourceDate.getGregorianCalendar()).isEqualTo(d.getGregorianCalendar());

        assertThat(validator.verifyMultiObjectMapping()).isTrue();
    }

    @Then("save and verify datetypes mapping as {string} and skip sql formats")
    public void saveAndVerifyDatetypesMappingAsAndSkipSqlFormats(String mapping) throws Throwable {
        userSavesMappingAs(mapping);
        TargetMappingTestClass t = (TargetMappingTestClass) validator.processMapping(TargetMappingTestClass.class.getName());
        DatesObject d = t.getDateObjectVariable();
        DatesObject sourceDate = (DatesObject) this.validator.getSource(DatesObject.class.getName());

        if (!mapping.contains("Time")) {
            assertThat(sourceDate.getLocalDate()).isEqualTo(d.getLocalDate());
            assertThat(sourceDate.getStandardJavaDate()).isEqualTo(d.getStandardJavaDate());
            assertThat(sourceDate.getCalendar().getTime()).isEqualTo(d.getCalendar().getTime());
        }
        assertThat(sourceDate.getLocalDateTime()).isEqualTo(d.getLocalDateTime());
        assertThat(sourceDate.getLocalTime()).isEqualTo(d.getLocalTime());
        assertThat(sourceDate.getZonedDateTime()).isEqualTo(d.getZonedDateTime());
        assertThat(sourceDate.getGregorianCalendar()).isEqualTo(d.getGregorianCalendar());
        assertThat(validator.verifyMultiObjectMapping()).isTrue();
    }

    @Then("^save and verify combine mapping with \"([^\"]*)\" separator as \"([^\"]*)\"$")
    public void saveAndVerifyCombineMappingWithSeparatorAs(String separator, String mapping) throws Throwable {
        validator.getTarget().setTargetCombineString(String.format("numbers:%1$s1%1$s2%1$s3.0%1$s4%1$s5%1$s6.0%1$s1970-01-01T00:00:00Z", separator));

        validator.getSource().setSourceString("numbers:");
        validator.getSource().setSourceChar('1');
        validator.getSource().setSourceInteger(2);
        validator.getSource().setSourceFloat(3);
        validator.getSource().setSourceLong(4);
        validator.getSource().setSourceShort((short) 5);
        validator.getSource().setSourceDouble(6);

        userSavesMappingAs(mapping);
        assertThat(validator.verifyMapping()).isTrue();
    }

    @Then("save and verify separate mapping with {string} separator as {string}")
    public void saveAndVerifySeparateMappingWithSeparatorAs(String separator, String mapping) throws Throwable {
        validator.getSource().setSourceCombineString(String.format("numbers:%1$sA%1$s2%1$s3.0%1$s4%1$s5%1$s6.0", separator));

        validator.getTarget().setTargetChar('A');
        validator.getTarget().setTargetString("numbers:");
        validator.getTarget().setTargetInteger(2);
        validator.getTarget().setTargetFloat(3);
        validator.getTarget().setTargetLong(4);
        validator.getTarget().setTargetShort((short) 5);
        validator.getTarget().setTargetDouble(6);
        userSavesMappingAs(mapping);
        assertThat(validator.verifyMapping()).isTrue();
    }

    @And("init SourceListClass and add in sourceMap")
    public void initSourceListClassAndAddInSourceMap() {
        final SourceListsClass src = new SourceListsClass();
        validator.addSource(src.getClass().getName(), src);
    }

    @Then("save and verify that {string} contains {string} as {string}")
    public void saveAndVerifyThatContainsAs(String array, String var, String path) throws Throwable {
        userSavesMappingAs(path);
        TargetMappingTestClass target = (TargetMappingTestClass) validator.processMapping(TargetMappingTestClass.class.getName());

        if ("listOfStrings".equals(array)) {
            final List strings = target.getTargetSmallMappingTestClass().getListOfStrings();

            if (var.contains("listOfIntegers")) {
                final List<Integer> integers = target.getTargetSmallMappingTestClass().getListOfIntegers();
                integers.forEach(i -> {
                    assertThat(strings).contains(i.toString());
                });
            } else if (var.contains("set")) {
                assertThat(target.getTargetSmallMappingTestClass().getListOfStrings()).containsAll((new SourceListsClass()).getSet());
            } else if (var.contains("array")) {
                assertThat(target.getTargetSmallMappingTestClass().getListOfStrings())
                    .containsAll(Arrays.asList((new SourceListsClass()).getArray()));
            }
        } else if ("listOfIntegers".equals(array)) {
            final List integers = target.getTargetSmallMappingTestClass().getListOfIntegers();
            if ("listOfIntegers".equals(var)) {
                integers.forEach(i -> {
                    ResourcesGenerator.getJsonArrays("jsonIntegers").contains(i);
                });
            } else {
                assertThat(integers).contains(Integer.valueOf(var));
            }
        } else {
            throw new NotFoundException("Unable to find field " + array);
        }
    }

    @Then("save and verify repeating mapping of collections to object as {string}")
    public void saveAndVerifyRepeatingMappingOfCollectionsToObjectAs(String mapping) throws Throwable {
        userSavesMappingAs(mapping);

        SourceListsClass slc = new SourceListsClass();
        TargetListsClass tlc = (TargetListsClass) validator.processSingleObjectMapping(slc, TargetListsClass.class.getName());
        tlc.getObjects().forEach(s -> {
            System.out.println(s.toString());
            assertThat(slc.getSet()).contains(s.getFirstName());
            assertThat(slc.getStrings()).contains(s.getLastName());
        });
    }

    @Then("save mapping as {string} and verify {string} with")
    public void saveAndVerifyMappingXmlJsonAsWith(String path, String expected, DataTable values) throws Throwable {
        userSavesMappingAs(path);
        String result = (String) validator.processMapping(expected);
        System.out.println(result);
        values.asList().forEach(value -> {
            log.info("Checking " + value);
            log.info("result, {}, value: {}", result, value);
            assertThat(result).contains(value);
        });
    }

    @Then("save and verify collections mappings in {string} {string} value is presented in {string} collection")
    public void saveAndVerifyCollectionsMappingsInValueIsPresentedInCollection(String mapping, String value, String collection) throws Exception {
        userSavesMappingAs(mapping);

        TargetListsClass result = (TargetListsClass) validator.processMapping(TargetListsClass.class.getName());
        if ("/doubles".equalsIgnoreCase(collection)) {
            assertThat(result.getDoubles().toString()).isEqualTo(value);
        } else if ("/integers".equalsIgnoreCase(collection)) {
            assertThat(result.getIntegers().toString()).isEqualTo(value);
        }
        if ("/strings".equalsIgnoreCase(collection)) {
            assertThat(result.getStrings().toString()).isEqualTo(value);
        }
        if ("/floats".equalsIgnoreCase(collection)) {
            assertThat(result.getFloats().toString()).isEqualTo(value);
        }
    }

    @Then("save and verify repeating mapping of json collections to object as {string}")
    public void saveAndVerifyRepeatingMappingOfJsonCollectionsToObjectAs(String mapping) throws Throwable {
        userSavesMappingAs(mapping);

        TargetListsClass tlc = (TargetListsClass) validator
            .processSingleObjectMapping(ResourcesGenerator.getJsonArrays(), "sourceArrays", TargetListsClass.class.getName());
        final List<Object> integers = ResourcesGenerator.getJsonArrays("jsonIntegers");
        final List<Object> strings = ResourcesGenerator.getJsonArrays("jsonStrings");

        assertThat(integers.size()).isGreaterThan(0);
        assertThat(strings.size()).isGreaterThan(0);

        tlc.getObjects().forEach(s -> {
            System.out.println(s.toString());
            assertThat(integers).contains(Integer.parseInt(s.getFirstName()));
            assertThat(strings).contains(s.getLastName());
        });
    }

    @Then("save and verify repeating mapping of json object to object as {string}")
    public void saveAndVerifyRepeatingMappingOfJsonObjectToObjectAs(String mapping) throws Throwable {
        userSavesMappingAs(mapping);
        final List<StringObject> targetObjects = ((TargetListsClass) validator
            .processSingleObjectMapping(ResourcesGenerator.getJsonArrays(), "sourceArrays", TargetListsClass.class.getName())).getObjects();
        final List jsonObjects = ResourcesGenerator.getJsonArrays("jsonObjects");

        for (int i = 0; i < targetObjects.size(); i++) {
            assertThat(targetObjects.get(i).getFirstName()).isEqualTo(((StringObject) jsonObjects.get(i)).getFirstName());
            assertThat(targetObjects.get(i).getLastName()).isEqualTo(((StringObject) jsonObjects.get(i)).getLastName());
        }
    }

    @Then("save and verify rootArrayMappings mapping as {string}")
    public void saveAndVerifyRootArrayMappingsMappingAs(String mapping) throws Throwable {
        userSavesMappingAs(mapping);
        String output = (String) validator.processSingleObjectMapping(ResourcesGenerator.getRootJsonArray(), "sourceJsonArray", "targetJsonArray");
        System.out.println("++++>" + output);
        assertThat(output).contains(
            "{\"arrayAnotherString\":\"1\",\"arrayString\":\"another-string\"},{\"arrayAnotherString\":\"2\",\"arrayString\":\"another-string\"}," +
                "{\"arrayAnotherString\":\"3\",\"arrayString\":\"another-string\"}");
    }

    @Then("save and verify mapping from java collections to root array {string}")
    public void saveAndVerifyMappingFromJavaCollectionsToRootArray(String mapping) throws Throwable {
        userSavesMappingAs(mapping);
        SourceListsClass slc = new SourceListsClass();
        String output = (String) validator.processSingleObjectMapping(new SourceListsClass(), SourceListsClass.class.getName(), "targetJsonArray");
        System.out.println("++++>" + output);

        slc.getIntegers().forEach(i -> {
            assertThat(output).contains("\"arrayNumber\":" + i);
            assertThat(output).contains("\"arrayString\":\"String" + i + "\"");
        });
    }

    @Then("save and verify mapping from nested {string} collection level {string} to first level collection as {string}")
    public void saveAndVerifyMappingFromNestedCollectionToFirstLevelArray(String sourceType, String level, String mapping) throws Throwable {
        userSavesMappingAs(mapping);
        String output = getTargetType(sourceType, "targetArrays");
        Assert.assertTrue(validateFirstLevelJsonCollectionResponse(sourceType, Integer.valueOf(level), output));
    }

    @Then("save and verify asymetric nested collection from third level {string} to second level json collection as {string}")
    public void saveAndVerifyAsymetricNestedCollectionToSecondLevelMapping(String sourceType, String mapping) throws Throwable {
        userSavesMappingAs(mapping);
        String output = getTargetType(sourceType, "targetArrays");
        Assert.assertTrue(validateAsymetricThirdToSecondLevelJsonCollectionResponse(sourceType, output));
    }

    @Then("save and verify asymetric nested collection from second level {string} to third level xml collection as {string}")
    public void saveAndVerifyAsymetricNestedCollectionToThirdLevelMapping(String sourceType, String mapping) throws Throwable {
        userSavesMappingAs(mapping);
        String output = getTargetType(sourceType, "targetXMLInstance");
        Assert.assertTrue(validateAsymetricSecondToThirdLevelXmlCollectionResponse(sourceType, output));
    }

    private String getTargetType(String sourceType, String targetDocId) {

        String output = null;
        String source;

        switch (sourceType) {
            case "json":
                source = "sourceArrays";
                output = (String) validator.processSingleObjectMapping(ResourcesGenerator.getJsonArrays(), source, targetDocId);
                break;
            case "java":
                source = SourceNestedCollectionClass.class.getName();
                output = (String) validator.processSingleObjectMapping(new SourceNestedCollectionClass(), source, targetDocId);
                break;
            case "xml":
                source = "sourceXmlInstance";
                output = (String) validator.processSingleObjectMapping(ResourcesGenerator.getXMLInstance(), source, targetDocId);
                break;
            default:
                fail("unknown source type: " + sourceType);
        }
        return output;
    }

    @Then("save and verify mapping from nested {string} collection to {string} as {string}")
    public void saveAndVerifyMappingsBetweenNestedCollections(String sourceType, String targetType, String mapping)
        throws Throwable {
        String source;
        String target = null;
        Object output = null;
        userSavesMappingAs(mapping);

        switch (targetType) {
            case "json":
                target = "targetArrays";
                break;
            case "java":
                target = TargetNestedCollectionClass.class.getName();
                break;
            case "xml":
                target = "targetXMLInstance";
                break;
            default:
                fail("unknown target type: " + targetType);
        }

        switch (sourceType) {
            case "json":
                source = "sourceArrays";
                output = validator.processSingleObjectMapping(ResourcesGenerator.getJsonArrays(), source, target);
                break;
            case "java":
                source = SourceNestedCollectionClass.class.getName();
                output = validator.processSingleObjectMapping(new SourceNestedCollectionClass(), source, target);
                break;
            case "xml":
                source = "sourceXmlInstance";
                output = validator.processSingleObjectMapping(ResourcesGenerator.getXMLInstance(), source, target);
                break;
            default:
                fail("unknown source type: " + sourceType);
        }

        Assert.assertTrue(validateNestedResponse(targetType, sourceType, output));
    }

    private boolean validateNestedResponse(String targetType, String sourceType, Object responseOriginal) {
        boolean responseMatch = false;
        switch (targetType) {
            case "json":
                responseMatch = validateNestedJsonResponse(sourceType, (String) responseOriginal);
                break;
            case "java":
                responseMatch = validateNestedJavaResponse(sourceType, (TargetNestedCollectionClass) responseOriginal);
                break;
            case "xml":
                responseMatch = validateNestedXmlResponse(sourceType, (String) responseOriginal);
                break;
            default:
                fail("unknown source type: " + sourceType);
        }
        return responseMatch;
    }

    private boolean validateNestedJsonResponse(String sourceType, String jsonResponseOriginal) {

        String jsonResponsePrototype = "[{\"secondArray\":[{\"thirdArray\":[{\"value\":\"xxxxThirdArrayValue0-0-0\"}," +
            "{\"value\":\"xxxxThirdArrayValue0-0-1\"}],\"value\":\"xxxxSecondArrayValue0-0\"}," +
            "{\"thirdArray\":[{\"value\":\"xxxxThirdArrayValue0-1-0\"},{\"value\":\"xxxxThirdArrayValue0-1-1\"}," +
            "{\"value\":\"xxxxThirdArrayValue0-1-2\"}],\"value\":\"xxxxSecondArrayValue0-1\"}],\"value\":\"xxxxFirstArrayValue0\"}," +
            "{\"secondArray\":[{\"thirdArray\":[{\"value\":\"xxxxThirdArrayValue1-0-0\"},{\"value\":\"xxxxThirdArrayValue1-0-1\"}," +
            "{\"value\":\"xxxxThirdArrayValue1-0-2\"}],\"value\":\"xxxxSecondArrayValue1-0\"}," +
            "{\"thirdArray\":[{\"value\":\"xxxxThirdArrayValue1-1-0\"},{\"value\":\"xxxxThirdArrayValue1-1-1\"}]," +
            "\"value\":\"xxxxSecondArrayValue1-1\"}],\"value\":\"xxxxFirstArrayValue1\"}]";

        return jsonResponseOriginal.contains(jsonResponsePrototype.replaceAll("xxxx", sourceType));
    }

    private boolean validateNestedJavaResponse(String sourceType, TargetNestedCollectionClass javaResponseOriginal) {
        TargetNestedCollectionClass result = new TargetNestedCollectionClass(sourceType);
        return result.equals(javaResponseOriginal);
    }

    private boolean validateNestedXmlResponse(String sourceType, String xmlResponseOriginal) {

        String xmlResponsePrototype =
            "<TargetXmlInstance><targetFirstArray><targetSecondArray><targetThirdArray><value>xxxxThirdArrayValue0-0-0</value></targetThirdArray" +
                "><targetThirdArray><value>xxxxThirdArrayValue0-0-1</value></targetThirdArray><value>xxxxSecondArrayValue0-0</value" +
                "></targetSecondArray><targetSecondArray><targetThirdArray><value>xxxxThirdArrayValue0-1-0</value></targetThirdArray" +
                "><targetThirdArray><value>xxxxThirdArrayValue0-1-1</value></targetThirdArray><targetThirdArray><value>xxxxThirdArrayValue0-1-2" +
                "</value></targetThirdArray><value>xxxxSecondArrayValue0-1</value></targetSecondArray><value>xxxxFirstArrayValue0</value" +
                "></targetFirstArray><targetFirstArray><targetSecondArray><targetThirdArray><value>xxxxThirdArrayValue1-0-0</value" +
                "></targetThirdArray><targetThirdArray><value>xxxxThirdArrayValue1-0-1</value></targetThirdArray><targetThirdArray><value" +
                ">xxxxThirdArrayValue1-0-2</value></targetThirdArray><value>xxxxSecondArrayValue1-0</value></targetSecondArray><targetSecondArray" +
                "><targetThirdArray><value>xxxxThirdArrayValue1-1-0</value></targetThirdArray><targetThirdArray><value>xxxxThirdArrayValue1-1-1" +
                "</value></targetThirdArray><value>xxxxSecondArrayValue1-1</value></targetSecondArray><value>xxxxFirstArrayValue1</value" +
                "></targetFirstArray></TargetXmlInstance>";

        return xmlResponseOriginal.contains(xmlResponsePrototype.replaceAll("xxxx", sourceType));
    }

    private boolean validateFirstLevelJsonCollectionResponse(String sourceType, int level, String jsonResponseOriginal) {

        String jsonResponsePrototypeLevel3 =
            "{\"jsonStrings\":[\"xxxxThirdArrayValue0-0-0\",\"xxxxThirdArrayValue0-0-1\",\"xxxxThirdArrayValue0-1-0\"," +
                "\"xxxxThirdArrayValue0-1-1\",\"xxxxThirdArrayValue0-1-2\",\"xxxxThirdArrayValue1-0-0\",\"xxxxThirdArrayValue1-0-1\"," +
                "\"xxxxThirdArrayValue1-0-2\",\"xxxxThirdArrayValue1-1-0\",\"xxxxThirdArrayValue1-1-1\"]}";

        String jsonResponsePrototypeLevel2 = "{\"jsonStrings\":[\"xxxxSecondArrayValue0-0\",\"xxxxSecondArrayValue0-1\"," +
            "\"xxxxSecondArrayValue1-0\",\"xxxxSecondArrayValue1-1\"]}";

        if (level == 2) {
            return jsonResponseOriginal.contains(jsonResponsePrototypeLevel2.replaceAll("xxxx", sourceType));
        } else {
            return jsonResponseOriginal.contains(jsonResponsePrototypeLevel3.replaceAll("xxxx", sourceType));
        }
    }

    private boolean validateAsymetricThirdToSecondLevelJsonCollectionResponse(String sourceType, String jsonResponseOriginal) {
        String jsonResponsePrototypeLevel2 = "{\"targetJsonNestedArray\":[{\"secondArray\":[{\"value\":\"xxxxThirdArrayValue0-0-0\"}," +
            "{\"value\":\"xxxxThirdArrayValue0-0-1\"},{\"value\":\"xxxxThirdArrayValue0-1-0\"},{\"value\":\"xxxxThirdArrayValue0-1-1\"}," +
            "{\"value\":\"xxxxThirdArrayValue0-1-2\"}]},{\"secondArray\":[{\"value\":\"xxxxThirdArrayValue1-0-0\"}," +
            "{\"value\":\"xxxxThirdArrayValue1-0-1\"},{\"value\":\"xxxxThirdArrayValue1-0-2\"},{\"value\":\"xxxxThirdArrayValue1-1-0\"}," +
            "{\"value\":\"xxxxThirdArrayValue1-1-1\"}]}]}";
        return jsonResponseOriginal.contains(jsonResponsePrototypeLevel2.replaceAll("xxxx", sourceType));
    }

    private boolean validateAsymetricSecondToThirdLevelXmlCollectionResponse(String sourceType, String xmlResponseOriginal) {
        String xmlResponsePrototypeLevel3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TargetXmlInstance><targetFirstArray><targetSecondArray" +
            "><targetThirdArray><value>xxxxSecondArrayValue0-0</value></targetThirdArray><targetThirdArray><value>xxxxSecondArrayValue0-1</value" +
            "></targetThirdArray></targetSecondArray><targetSecondArray><targetThirdArray><value>xxxxSecondArrayValue1-0</value></targetThirdArray" +
            "><targetThirdArray><value>xxxxSecondArrayValue1-1</value></targetThirdArray></targetSecondArray></targetFirstArray></TargetXmlInstance>";
        return xmlResponseOriginal.contains(xmlResponsePrototypeLevel3.replaceAll("xxxx", sourceType));
    }
}
