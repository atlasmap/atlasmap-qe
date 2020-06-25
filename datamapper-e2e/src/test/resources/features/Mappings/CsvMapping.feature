@Mappings
@CSV
Feature: mapping from CSV

  Background:  Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

  Scenario: Csv collection string mapping

    And click on create new mapping from target "/targetSmallMappingTestClass/listOfStrings"
    And add "/<>/sourceCsvString" as "source"

    Then save and verify that "listOfStrings" contains "csvStrings" as "csvToStrings.json"

  Scenario: Csv collection integers mapping with transformation
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfIntegers"
    And add "/<>/sourceCsvNumber" as "source"

    Then save and verify that "listOfIntegers" contains "csvIntegers" as "csvToIntegers.json"

    # Add test - type conversions (integer, double, boolean, )
    # Blocked by: https://issues.redhat.com/browse/ENTESB-14222
    # Mapping to CSV (e.g. CSV -> CSV)
    # Blocked by: https://issues.redhat.com/browse/ENTESB-14189
    # CSV Mapping preview??
    # Advanced mapping options (e.g. remove header - not yet in UI)
    # Plan for custom user defined CSV??
    # Array of objects to CSV?? (basically a list format transformation)