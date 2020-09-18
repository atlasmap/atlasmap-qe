@Mappings
@CSV
Feature: mapping from CSV

  Background:  Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

  @CsvTransformations
  Scenario: Csv collection string mapping
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfStrings"
    And add "/<>/sourceCsvString" as "source"
    Then save and verify that "listOfStrings" contains "csvStrings" as "csvToStrings.json"

  @CsvTransformations
  Scenario: Csv collection integers mapping with transformation
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfIntegers"
    And add "/<>/sourceCsvNumber" as "source"
    Then save and verify that "listOfIntegers" contains "csvIntegers" as "csvToIntegers.json"

  @CsvTransformations
  Scenario: Csv collection doubles mapping with transformation
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvToDoubles.json"

  @CsvToJson
  Scenario: map from csv array to json array
    And click on create new mapping
    And add "/<>/sourceCsvString" as "source"
    And add "/<>/arrayString" as "target"
    Then save and verify repeating mapping of csv object to object as "csvToJson.json"

  @CsvSourceCsvTargetMapAll
  Scenario: Csv collection doubles mapping with transformation
    And click on create new mapping
    And add "/<>/targetCsvBoolean" as "target"
    And add "/<>/sourceCsvBoolean" as "source"
    And click on create new mapping
    And add "/<>/targetCsvDate" as "target"
    And add "/<>/sourceCsvDate" as "source"
    And click on create new mapping
    And add "/<>/targetCsvDecimal" as "target"
    And add "/<>/sourceCsvDecimal" as "source"
    And click on create new mapping
    And add "/<>/targetCsvNumber" as "target"
    And add "/<>/sourceCsvNumber" as "source"
    And click on create new mapping
    And add "/<>/targetCsvString" as "target"
    And add "/<>/sourceCsvString" as "source"
    And save and verify CSV mapping as "mappingCSVAll.json"

    # Advanced mapping options (e.g. remove header)
