@Mappings
@CollectionMappings

Feature: Mappings of collections

  Background:  Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

  Scenario: List<String> to String
    When add mapping from "listOfStrings" to "targetString"
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
    When add mapping from "listOfIntegers" to "/targetSmallMappingTestClass/listOfStrings"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "listOfIntegers" as "repetitive4.json"

  Scenario: Mapping set to list of Strings
    When add mapping from "set" to "/targetSmallMappingTestClass/listOfStrings"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "set" as "repetitive5.json"

  Scenario: Mapping array to list of Strings
    When add mapping from "array" to "/targetSmallMappingTestClass/listOfStrings"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "array" as "repetitive6.json"


  @SmokeTest
  Scenario: mapping between  json arrays on root level
    And open all subfolders
    When add mapping from "/arrayString" to "/arrayAnotherString"
    And add mapping from "/arrayAnotherString" to "/arrayString"
    Then save and verify rootArrayMappings mapping as "rootArrayMappings.json"

  Scenario: mapping from arrays to root json array
    And open all subfolders
    When add mapping from "/integers" to "/arrayNumber"
    And add mapping from "/strings" to "/arrayString"
    Then save and verify mapping from java collections to root array "toRootArrayMappings.json"

  @SmokeTest
  Scenario: verify List<Object> to List<Object> mapping
    #TODO: this doesn't work correctly - the nested with same name as already existing element
    When add mapping from "/objects/firstName" to "/objects/lastName"
    When add mapping from "/objects/lastName" to "/objects/firstName"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of ListClasses as "repetitive7.json"

  Scenario: map list and set to List<Object> mapping
    When add mapping from "strings" to "/objects/lastName"
    When add mapping from "set" to "/objects/firstName"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of collections to object as "repetitive8.json"

  @SmokeTest
  Scenario: map from json arrays to java array of object
    When add mapping from "jsonStrings" to "/objects/lastName"
    When add mapping from "jsonIntegers" to "/objects/firstName"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of json collections to object as "repetitive9.json"

  Scenario: map from json array of objects to java array of objects
    When add mapping from "/jsonObjects/key" to "/objects/firstName"
    When add mapping from "/jsonObjects/value" to "/objects/lastName"
    And Init smallMappingTestClass and add to source map
    Then save and verify repeating mapping of json object to object as "repetitive10.json"

    @primitives
  Scenario: Mapping  collections of primitives
    When add mapping from "jsonIntegers" to "/targetSmallMappingTestClass/listOfStrings"
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "listOfIntegers" as "repetitive11.json"

  Scenario: map from json array of objects to java array of objects
    When add mapping from "/jsonObjects/key" to "/targetSmallMappingTestClass/listOfStrings"
    When add mapping from "/jsonObjects/value" to "/targetSmallMappingTestClass/listOfIntegers"
    And add "Replace First" transformation on "source"
    And set "Match" for transformation to "v"
    And set "New String" for transformation to ""
    And Init smallMappingTestClass and add to source map
    Then save and verify that "listOfStrings" contains "listOfIntegers" as "repetitive12.json"
    Then save and verify that "listOfIntegers" contains "listOfIntegers" as "repetitive12.json"

