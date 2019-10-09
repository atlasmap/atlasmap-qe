@Mappings
@CollectionMappings

Feature: Mappings of collections

  Background:  Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

  Scenario: List<String> to String
    When set mapping from "listOfStrings" to "targetString"
    And Init smallMappingTestClass and add to source map
    And set "1_0 1_1 1_2 1_3 1_4 1_5 1_6 1_7 1_8 1_9" value in target's "targetString"
    Then save and verify mapping with multiple objects as "repetitive1.json"

  @fail
  Scenario: String to String<List>
    When add mapping from "sourceString" to "/targetSmallMappingTestClass/listOfStrings"
    And set "test" value in source's "sourceString"
    Then save and verify that "listOfStrings" contains "test" as "repetitive2.json"

  Scenario: String to integers conversions
    When add mapping from "sourceString" to "/targetSmallMappingTestClass/listOfIntegers"
    And set "999" value in source's "sourceString"
    Then save and verify that "listOfIntegers" contains "999" as "repetitive3.json"

  Scenario: Integers to Strings
    When click on "listOfIntegers"
    And set "targetSmallMappingTestClass/listOfStrings" as "target"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "listOfIntegers" as "repetitive4.json"


  Scenario: Mapping set to list of Strings
    When click on "set"
    And  set "targetSmallMappingTestClass/listOfStrings" as "target"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "set" as "repetitive5.json"

  Scenario: Mapping array to list of Strings
    When click on "array"
    And  set "targetSmallMappingTestClass/listOfStrings" as "target"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "array" as "repetitive6.json"


  @SmokeTest
  Scenario: mapping between  json arrays on root level
    When add mapping from "/<>/arrayString" to "/<>/arrayAnotherString"
    And add mapping from "/<>/arrayAnotherString" to "/<>/arrayString"
    Then save and verify rootArrayMappings mapping as "rootArrayMappings.json"

  Scenario: mapping from arrays to root json array
    When add mapping from "/integers" to "/<>/arrayNumber"
    And add mapping from "/strings" to "/<>/arrayString"
    Then save and verify mapping from java collections to root array "toRootArrayMappings.json"


  @SmokeTest
  Scenario: verify List<Object> to List<Object> mapping
    When click on "objects"
    And click on "firstName"
    And  set "/objects<>/lastName" as "target"
    And click on "lastName"
    And  set "/objects<>/firstName" as "target"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of ListClasses as "repetitive7.json"

  Scenario: map list and set to List<Object> mapping
    When click on "strings"
    And  set "/objects<>/lastName" as "target"
    And click on "set"
    And  set "/objects<>/firstName" as "target"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of collections to object as "repetitive8.json"


  @SmokeTest
  Scenario: map from json arrays to java array of object
    When click on "jsonStrings"
    And  set "/objects<>/lastName" as "target"
    And click on "jsonIntegers"
    And  set "/objects<>/firstName" as "target"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of json collections to object as "repetitive9.json"

  Scenario: map from json array of objects to java array of objects
    When click on "jsonObjects"
    When click on "key"
    And  set "/objects<>/firstName" as "target"
    And click on "value"
    And  set "/objects<>/lastName" as "target"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of json object to object as "repetitive10.json"

    @primitives
  Scenario: Mapping  collections of primitives
    When click on "jsonIntegers"
    And  set "targetSmallMappingTestClass/listOfStrings" as "target"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "listOfIntegers" as "repetitive11.json"

  Scenario: map from json array of objects to java array of objects
    When click on "jsonObjects"
    When click on "key"
    And  set "/targetSmallMappingTestClass/listOfStrings" as "target"
    And click on "value"
    And  set "/targetSmallMappingTestClass/listOfIntegers" as "target"
    And add "Replace First" transformation on "source"
    And for "input-match" input set "v"
    And for "input-newString" input set ""
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "listOfIntegers" as "repetitive12.json"
    Then save and verify that "listOfIntegers" contains "listOfIntegers" as "repetitive12.json"

