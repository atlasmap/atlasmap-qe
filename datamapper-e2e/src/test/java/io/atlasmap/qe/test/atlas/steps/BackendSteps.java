package io.atlasmap.qe.test.atlas.steps;

import java.util.Map;

import org.junit.Assert;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.atlasmap.qe.test.DatesObject;
import io.atlasmap.qe.test.MappingValidator;
import io.atlasmap.qe.test.SmallMappingTestClass;
import io.atlasmap.qe.test.SourceListsClass;
import io.atlasmap.qe.test.StringObject;
import io.atlasmap.qe.test.TargetListsClass;
import io.atlasmap.qe.test.TargetMappingTestClass;
import io.atlasmap.qe.test.atlas.utils.Utils;

public class BackendSteps extends CucumberGlue {

    @Given("^atlasmap is clean$")
    public void atlasmapIsClean() throws Exception {
        Utils.cleanMappingFolder();
        validator = new MappingValidator();
    }

    @Given("^atlasmap contains TestClass$")
    public void atlasmapContainsTestClass() throws Exception {
        String resp = Utils.requestClass(atlasmapPage.TEST_CLASS);
        Assert.assertTrue(resp.contains(atlasmapPage.TEST_CLASS));
    }

    @Then("^save mapping as \"([^\"]*)\"$")
    public void userSavesMappingAs(String arg1) throws Exception {
        Thread.sleep(1000);
        String mappingLocation = Utils.moveMappping(arg1);
        validator.setMappingLocation(arg1);
    }

    @And("^verify \"([^\"]*)\"$")
    public void verify(String arg0) throws Throwable {
        Assert.assertTrue(validator.verifyMapping());

    }

    @When("^set source data$")
    public void setsSourceData(DataTable sourceMappingData) throws Throwable {
        //SourceMappingTestClass source = new SourceMappingTestClass();
        for (Map<String, String> source : sourceMappingData.asMaps(String.class, String.class)) {
            for (String field : source.keySet()) {
                this.validator.setSourceValue(field, source.get(field));
            }
        }
    }

    @And("^set expected data$")
    public void setsExpectedData(DataTable targetMappingData) throws Throwable {
        for (Map<String, String> source : targetMappingData.asMaps(String.class, String.class)) {
            for (String field : source.keySet()) {

                if (field.contains("Char")) {
                    this.validator.setTargetValue(field, source.get(field).charAt(0));
                } else {
                    this.validator.setTargetValue(field, source.get(field));
                }
            }
        }
    }

    @And("^internal mapping is skipped$")
    public void internalMappingIsSkipped() throws Throwable {
        this.internalMapping = false;
    }

    @And("^internal mapping is set to \"([^\"]*)\"$")
    public void internalMappingIsSetTo(boolean arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        this.internalMapping = arg0;
    }

    @And("^verify if \"([^\"]*)\" is not \"([^\"]*)\" in \"([^\"]*)\"$")
    public void verifyIfIsNotIn(String field, String value, String path) throws Throwable { //
        // Assert.assertTrue(validator.verifyMapping());
        TargetMappingTestClass processed = this.validator.processMapping();
        Assert.assertNotEquals(processed.getValue(field), value);

    }

    @Then("^save and verify mapping as \"([^\"]*)\"$")
    public void saveAndVerifyMappingAs(String arg0) throws Throwable {
        userSavesMappingAs(arg0);
        Assert.assertTrue(validator.verifyMapping());
    }

    @And("^set \"([^\"]*)\" value in source's \"([^\"]*)\"$")
    public void setValueInSource(String value, String field) throws Throwable {
        this.validator.setSourceValue(field, value);
    }

    @And("^set \"([^\"]*)\" value in target's \"([^\"]*)\"$")
    public void setValueInTarget(String value, String field) throws Throwable {
        if (field.contains("Char")) {
            this.validator.setTargetValue(field, value.charAt(0));
        } else {
            this.validator.setTargetValue(field, value);
        }
    }

    @And("^save and verify \"([^\"]*)\" with$")
    public void verifyValuesPositive(String mapping, DataTable testValues) throws Exception {
        verifyMultipleValues(mapping, testValues, true);
    }

    private void verifyMultipleValues(String mapping, DataTable testValues, Boolean checkIfTrue) throws Exception {
        userSavesMappingAs(mapping);

        String sourceField = testValues.getPickleRows().get(0).getCells().get(0).getValue();
        String targetField = testValues.getPickleRows().get(0).getCells().get(1).getValue();

        for (Map<String, String> data : testValues.asMaps(String.class, String.class)) {

            this.validator.setSourceValue(sourceField, data.get(sourceField));
            this.validator.setTargetValue(targetField, data.get(targetField));
            Assert.assertEquals(this.validator.verifyMapping(checkIfTrue), checkIfTrue);
        }
    }

    @And("^sleep for \"([^\"]*)\"$")
    public void sleepFor(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Thread.sleep(arg0);
    }

    @Then("^save \"([^\"]*)\" verify negative with$")
    public void saveVerifyNegativeWith(String mapping, DataTable testValues) throws Throwable {
        verifyMultipleValues(mapping, testValues, false);
    }


    @And("^Add StringObject to expected map with \"([^\"]*)\", \"([^\"]*)\" values$")
    public void addStringObjectToExpedtedMapWithAndValues(String arg0, String arg1) throws Throwable {
        StringObject so = new StringObject();
        so.setFirstName(arg0);
        so.setLastName(arg1);

        validator.getExpectedMap().put(so.getClass().getName(), so);
    }

    @Then("^save and verify mapping with multiple objects as \"([^\"]*)\"$")
    public void saveAndVerifyMappingWithMultipleObjectsAs(String arg0) throws Throwable {
        userSavesMappingAs(arg0);
        Assert.assertTrue(validator.verifyMultiObjectMapping());
    }

    @And("^Init smallMappingTestClass and add to source map$")
    public void initSmallMappingTestClassAndAddToSourceMap() throws Throwable {
        final SmallMappingTestClass s = new SmallMappingTestClass();
        validator.addSource(s.getClass().getName(), s);
    }

    @Then("^save and verify repeating mapping of ListClasses as \"([^\"]*)\"$")
    public void saveAndVerifyMappingOfListClassesAs(String mapping) throws Throwable {
        userSavesMappingAs(mapping);

        SourceListsClass slc = new SourceListsClass();
        TargetListsClass tlc = (TargetListsClass) validator.processSingleObjectMapping(slc,TargetListsClass.class.getName());

        for (int i =0;i<slc.getObjects().size();i++) {
            final StringObject src = slc.getObjects().get(i);
            final StringObject tgt = tlc.getObjects().get(i);

            Assert.assertEquals(src.getFirstName(),tgt.getLastName());
            Assert.assertEquals(src.getLastName(),tgt.getFirstName());
        }
    }

    @And("^init DateObject \"([^\"]*)\"$")
    public void initDateObject(String date) throws Throwable {
        DatesObject d = new DatesObject(date);
       // System.out.println(d.toString());

        this.validator.addSource(d.getClass().getName(),d);
        //System.out.println(this.validator.getSource(d.getClass().getName()));

    }

    @Then("^save and verify mapping from \"([^\"]*)\" to datetypes as \"([^\"]*)\"$")
    public void saveAndVerifyMappingFromToDatetypesAs(String source, String mapping) throws Throwable {
     //   Thread.sleep(00);
        userSavesMappingAs(mapping);
        TargetMappingTestClass t = (TargetMappingTestClass) validator.processMapping(TargetMappingTestClass.class.getName());
        DatesObject d = t.getDateObjectVariable();
        DatesObject sourceDate = (DatesObject)this.validator.getSource(DatesObject.class.getName());

        Assert.assertEquals(sourceDate.getStandardJavaDate(),d.getStandardJavaDate());
        Assert.assertEquals(sourceDate.getCalendar().getTimeInMillis(),d.getCalendar().getTimeInMillis());
        Assert.assertEquals(sourceDate.getLocalDate(),d.getLocalDate());
        Assert.assertEquals(sourceDate.getLocalDateTime(),d.getLocalDateTime());
        Assert.assertEquals(sourceDate.getLocalTime(),d.getLocalTime());
        Assert.assertEquals(sourceDate.getTime(),d.getTime());
        Assert.assertEquals(sourceDate.getTimestamp(),d.getTimestamp());
        Assert.assertEquals(sourceDate.getZonedDateTime(),d.getZonedDateTime());
        Assert.assertEquals(sourceDate.getSqlDate().toString(),d.getSqlDate().toString());
        Assert.assertEquals(sourceDate.getGregorianCalendar(),d.getGregorianCalendar());

        Assert.assertTrue(validator.verifyMultiObjectMapping());
    }

    @Then("^save and verify datetypes mapping  as \"([^\"]*)\" and skip sql formats$")
    public void saveAndVerifyDatetypesMappingAsAndSkipSqlFormats(String mapping) throws Throwable {
     //   Thread.sleep(500);
        userSavesMappingAs(mapping);
        TargetMappingTestClass t = (TargetMappingTestClass) validator.processMapping(TargetMappingTestClass.class.getName());
        DatesObject d = t.getDateObjectVariable();
        DatesObject sourceDate = (DatesObject)this.validator.getSource(DatesObject.class.getName());


        if (!mapping.contains("Time")) {
            Assert.assertEquals(sourceDate.getLocalDate(), d.getLocalDate());
            Assert.assertEquals(sourceDate.getStandardJavaDate(),d.getStandardJavaDate());
            Assert.assertEquals(sourceDate.getCalendar().getTime(),d.getCalendar().getTime());
        }
        Assert.assertEquals(sourceDate.getLocalDateTime(),d.getLocalDateTime());
        Assert.assertEquals(sourceDate.getLocalTime(),d.getLocalTime());
        Assert.assertEquals(sourceDate.getZonedDateTime(),d.getZonedDateTime());
        Assert.assertEquals(sourceDate.getGregorianCalendar(),d.getGregorianCalendar());
        Assert.assertTrue(validator.verifyMultiObjectMapping());
    }

    @Then("^save and verify combine mapping with \"([^\"]*)\" separator as \"([^\"]*)\"$")
    public void saveAndVerifyCombineMappingWithSeparatorAs(String separator, String mapping) throws Throwable {
        validator.getSource().setSourceChar('1');
        validator.getSource().setSourceInteger(2);
        validator.getSource().setSourceFloat(3);
        validator.getSource().setSourceLong(4);
        validator.getSource().setSourceShort((short) 5);
        validator.getSource().setSourceDouble(6);

        validator.getTarget().setTargetCombineString(String.format("Combined: sourceString%1$s1%1$s2%1$s3.0%1$s4%1$s5%1$s6.0%1$s1970-01-01T00:00:00Z",separator));
        userSavesMappingAs(mapping);
        Assert.assertTrue(validator.verifyMapping());

    }

    @Then("^save and verify separate mapping with \"([^\"]*)\" separator as \"([^\"]*)\"$")
    public void saveAndVerifySeparateMappingWithSeparatorAs(String separator, String mapping) throws Throwable {
        validator.getSource().setSourceCombineString(String.format("numbers:%1$sA%1$s2%1$s3.0%1$s4%1$s5%1$s6.0",separator));

        validator.getTarget().setTargetChar('A');
        validator.getTarget().setTargetString("numbers:");
        validator.getTarget().setTargetInteger(2);
        validator.getTarget().setTargetFloat(3);
        validator.getTarget().setTargetLong(4);
        validator.getTarget().setTargetShort((short) 5);
        validator.getTarget().setTargetDouble(6);
        userSavesMappingAs(mapping);
        Assert.assertTrue(validator.verifyMapping());
    }

    @And("^init SourceListClass and add in sourceMap$")
    public void initSourceListClassAndAddInSourceMap() throws Throwable {
        final SourceListsClass src = new SourceListsClass();
        validator.addSource(src.getClass().getName(),src);
    }

    @Then("^save and verify that \"([^\"]*)\" contains \"([^\"]*)\" as \"([^\"]*)\"$")
    public void saveAndVerifyThatContainsAs(String array, String var, String path) throws Throwable {
      userSavesMappingAs(path);

      TargetMappingTestClass target = (TargetMappingTestClass) validator.processMapping(TargetMappingTestClass.class.getName());
      if ("listOfStrings".equals(array)) {
          if(!var.contains("listOfIntegers")) {
              Assert.assertTrue(target.getTargetSmallMappingTestClass().getListOfStrings().contains(var));
          } else {
              //listOfIntegers
              for(int i=0;i<target.getTargetSmallMappingTestClass().getListOfIntegers().size();i++) {
                  Assert.assertEquals(target.getTargetSmallMappingTestClass().getListOfIntegers().get(i).toString(),target.getTargetSmallMappingTestClass().getListOfStrings().get(i));
              }
          }
      }
      else if ("listOfIntegers".equals(array)) {
          Assert.assertTrue(target.getTargetSmallMappingTestClass().getListOfIntegers().contains(Integer.valueOf(var)));
      }
      else {
          Assert.fail("Unable to find field " +array);
      }
    }

    @Then("^save mapping as \"([^\"]*)\" and verify \"([^\"]*)\" with$")
    public void saveAndVerifyMappingXmlJsonAsWith(String path ,String expected, DataTable values) throws Throwable {
        userSavesMappingAs(path);
        String result = (String) validator.processMapping(expected);
       // System.out.println(result);
        for(String value:values.asList(String.class)) {
            LOG.info("Checking " + value);
            Assert.assertTrue(result.contains(value));
        }


}}
