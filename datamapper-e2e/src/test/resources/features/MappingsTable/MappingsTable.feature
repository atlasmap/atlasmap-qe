@MappingsTable
Feature: mappings table

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
    And switch to column mapper view

  @SmokeTest
  Scenario: check simple mapping in table anch check if table is filled properly
    And switch to mapping table view
    When click on create new mapping
    And add "/sourceInteger" as "source"
    And add "/targetInteger" as "target"

    When click on create new mapping
    And add "/sourceString" as "source"
    And add "/targetString" as "target"

    When click on create new mapping
    And add "/sourceString" as "source"
    And add "/targetCombineString" as "target"

    Then check that row number "0" contains "sourceInteger" as sources, "targetInteger" as target and "One to One" as type
    And  check that row number "1" contains "sourceString" as sources, "targetString" as target and "One to One" as type
    And check that row number "2" contains "sourceString" as sources, "targetCombineString" as target and "One to One" as type


  @TableCombine
  Scenario: check combine mapping in table and check if table is filled properly
    When add mapping from "sourceInteger" to "targetString"
    And add "sourceFloat" to combine
    And add "sourceString" to combine

    And switch to mapping table view
    Then check that row number "0" contains "sourceInteger,sourceFloat,sourceString" as sources, "targetString" as target and "Many to One (Concatenate)" as type

  @TableSplit
  Scenario: check separate mapping in table and check if table is filled properly
    When add mapping from "sourceCombineString" to "targetString"
    And add "targetInteger" to separate
    And add "targetFloat" to separate

    And switch to mapping table view
    Then check that row number "0" contains "sourceCombineString" as sources, "targetString,targetInteger,targetFloat" as target and "One to Many (Split)" as type


  @SmokeTest
  @edit
  Scenario: select and edit
    When add mapping from "sourceInteger" to "targetInteger"
    And add mapping from "sourceString" to "targetString"

    And switch to mapping table view
    Then check that row number "0" contains "sourceInteger" as sources, "targetInteger" as target and "One to One" as type

    When click on "0" index of table
    And add "/sourceFloat" as "source"

    And switch to mapping table view
    Then check that row number "0" contains "sourceInteger,sourceFloat" as sources, "targetInteger" as target and "Many to One (Concatenate)" as type

  @debug
  @SmokeTest
  Scenario: delete from mappings table
    And switch to mapping table view

    When click on create new mapping
    And add "/sourceInteger" as "source"
    And add "/targetInteger" as "target"

    When click on create new mapping
    And add "/sourceString" as "source"
    And add "/targetString" as "target"

    And click on "0" index of table
    And delete current mapping
    Then check that row number "0" contains "sourceString" as sources, "targetString" as target and "One to One" as type
