@Mappings
@RepeatingMappings

Feature: flat mappings between Collections ..
  
  Background:  Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

#  Scenario: List<String> to List<String> mapping

  Scenario: List<String> to String
    When set mapping from "listOfStrings" to "targetString"
    And Init smallMappingTestClass and add to source map
    And set "1_9" value in target's "targetString"
    Then save and verify mapping with multiple objects as "repetitive1.xml"

  Scenario: String to String<List>
    When add mapping from "sourceString" to "/targetSmallMappingTestClass/listOfStrings"
    And set "test" value in source's "sourceString"
    Then save and verify that "listOfStrings" contains "test" as "repetitive2.xml"

  Scenario: String to integers conversions
    When add mapping from "sourceString" to "/targetSmallMappingTestClass/listOfIntegers"
    And set "999" value in source's "sourceString"
    Then save and verify that "listOfIntegers" contains "999" as "repetitive3.xml"

  Scenario: Mapping  collections of primitives
    When click on "listOfIntegers"
    And for "input-target-" id input set "targetSmallMappingTestClass/listOfStrings"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "listOfIntegers" as "repetitive4.xml"

  Scenario: verify List<Object> to List<Object> mapping
    When click on "objects"
    And click on "firstName"
    And for "input-target-" id input set "/objects<>/lastName"
    And click on "lastName"
    And for "input-target-" id input set "/objects<>/firstName"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of ListClasses as "repetitive5.xml"
