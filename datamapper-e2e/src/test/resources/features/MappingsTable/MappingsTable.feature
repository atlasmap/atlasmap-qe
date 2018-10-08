@MappingsTable
Feature: mappings table

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
    And reveal mapping table

  @SmokeTest
  Scenario: check simple mapping in table anch check if table is filled properly
    When add mapping from "sourceInteger" to "targetInteger"
    And add mapping from "sourceString" to "targetString"
    And add mapping from "sourceString" to "targetCombineString"

    Then check that row number "0" contains "/sourceInteger" as sources, "/targetInteger" as taget and "Map" as type
    And  check that row number "1" contains "/sourceString" as sources, "/targetString" as taget and "Map" as type
    And check that row number "2" contains "/sourceString" as sources, "/targetCombineString" as taget and "Map" as type

  Scenario: check combine mapping in table anch check if table is filled properly
    When add mapping from "sourceInteger" to "targetString"
    And add select "Combine" action
    And add "sourceFloat" to combine
    And add "sourceString" to combine
    Then check that row number "0" contains "/sourceFloat,/sourceInteger,/sourceString" as sources, "/targetString" as taget and "Combine (Space [ ])" as type


  Scenario: check separate mapping in table anch check if table is filled properly
    When add mapping from "sourceCombineString" to "targetString"
    And add select "Separate" action
    And add "targetInteger" to separate
    And add "targetFloat" to separate
    Then check that row number "0" contains "/sourceCombineString" as sources, "/targetFloat,/targetInteger,/targetString" as taget and "Separate (Space [ ])" as type

  @SmokeTest
  Scenario: select and edit
    When add mapping from "sourceInteger" to "targetInteger"
    And add mapping from "sourceString" to "targetString"
    Then check that row number "0" contains "/sourceInteger" as sources, "/targetInteger" as taget and "Map" as type

    When click on "0" index of table
    And for "input-source-sourceInteger" id input set "/sourceFloat"
    Then check that row number "0" contains "/sourceFloat" as sources, "/targetInteger" as taget and "Map" as type

  @SmokeTest
  Scenario: delete from mappings table
    When add mapping from "sourceInteger" to "targetInteger"
    And add mapping from "sourceString" to "targetString"

    And click on "0" index of table
    And delete current mapping
    Then check that row number "0" contains "/sourceString" as sources, "/targetString" as taget and "Map" as type
