@Mappings
@RepeatingMappings

Feature: flat mappings between Collections ..

  Background:  Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

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


  Scenario: Mapping set to list
    When click on "set"
    And for "input-target-" id input set "targetSmallMappingTestClass/listOfStrings"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "set" as "repetitive5.xml"


  Scenario: Mapping array to list
    When click on "array"
    And for "input-target-" id input set "targetSmallMappingTestClass/listOfStrings"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "array" as "repetitive6.xml"


    @SmokeTest
  Scenario: mapping between root json arrays
    When add mapping from "/<>/arrayString" to "/<>/arrayAnotherString"
    And add mapping from "/<>/arrayAnotherString" to "/<>/arrayString"
    Then save and verify rootArrayMappings mapping as "rootArrayMappings.xml"

  Scenario: mapping from arrays to root json array
    When add mapping from "/integers" to "/<>/arrayNumber"
    And add mapping from "/strings" to "/<>/arrayString"
    Then save and verify mapping from java collections to root array "toRootArrayMappings.xml"


  @SmokeTest
  Scenario: verify List<Object> to List<Object> mapping
    When click on "objects"
    And click on "firstName"
    And for "input-target-" id input set "/objects<>/lastName"
    And click on "lastName"
    And for "input-target-" id input set "/objects<>/firstName"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of ListClasses as "repetitive7.xml"

  Scenario: map list and set to List<Object> mapping
    When click on "strings"
    And for "input-target-" id input set "/objects<>/lastName"
    And click on "set"
    And for "input-target-" id input set "/objects<>/firstName"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of collections to object as "repetitive8.xml"


  @SmokeTest
  Scenario: map from json arrays to java array of object
    When click on "jsonStrings"
    And for "input-target-" id input set "/objects<>/lastName"
    And click on "jsonIntegers"
    And for "input-target-" id input set "/objects<>/firstName"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of json collections to object as "repetitive9.xml"

  Scenario: map from json array of objects to java array of objects
    When click on "jsonObjects"
    When click on "key"
    And for "input-target-" id input set "/objects<>/firstName"
    And click on "value"
    And for "input-target-" id input set "/objects<>/lastName"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of json object to object as "repetitive10.xml"

  Scenario: Mapping  collections of primitives
    When click on "jsonIntegers"
    And for "input-target-" id input set "targetSmallMappingTestClass/listOfStrings"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "listOfIntegers" as "repetitive11.xml"

  Scenario: map from json array of objects to java array of objects
    When click on "jsonObjects"
    When click on "key"
    And for "input-target-" id input set "listOfStrings"
    And click on "value"
    And for "input-target-" id input set "listOfIntegers"
    And add "ReplaceFirst" transformation on "source"
    And for "input-match" input set "v"
    And for "input-newString" input set ""
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "listOfIntegers" as "repetitive12.xml"
    Then save and verify that "listOfIntegers" contains "listOfIntegers" as "repetitive12.xml"

