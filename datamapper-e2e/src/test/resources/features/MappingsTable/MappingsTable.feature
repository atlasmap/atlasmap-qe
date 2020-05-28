@ENTESB
# Blocked by: https://issues.redhat.com/browse/ENTESB-13648
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

    Then check that row number "0" contains "/sourceInteger" as sources, "/targetInteger" as target and "One to One" as type
    And  check that row number "1" contains "/sourceString" as sources, "/targetString" as target and "One to One" as type
    And check that row number "2" contains "/sourceString" as sources, "/targetCombineString" as target and "One to One" as type


  @TableCombine
  Scenario: check combine mapping in table and check if table is filled properly
    When add mapping from "sourceInteger" to "targetString"
    And add "sourceFloat" to combine
    And add "sourceString" to combine
    Then check that row number "0" contains "/sourceInteger,/sourceFloat,/sourceString" as sources, "/targetString" as target and "Many to One (Concatenate)" as type

  @TableSplit
  Scenario: check separate mapping in table and check if table is filled properly
    When add mapping from "sourceCombineString" to "targetString"
    And add "targetInteger" to separate
    And add "targetFloat" to separate
    Then check that row number "0" contains "/sourceCombineString" as sources, "/targetString,/targetInteger,/targetFloat" as target and "One to Many (Split)" as type


  @SmokeTest
    @edit
  Scenario: select and edit
    When add mapping from "sourceInteger" to "targetInteger"
    And add mapping from "sourceString" to "targetString"
    Then check that row number "0" contains "/sourceInteger" as sources, "/targetInteger" as target and "One to One" as type

    When click on "0" index of table
    And  set "/sourceFloat" as "source"
    Then check that row number "0" contains "/sourceInteger,/sourceFloat" as sources, "/targetInteger" as target and "Many to One (Concatenate)" as type

  @debug
  @SmokeTest
  Scenario: delete from mappings table
    When add mapping from "sourceInteger" to "targetInteger"
    And add mapping from "sourceString" to "targetString"

    And click on "0" index of table
    And delete current mapping
    Then check that row number "0" contains "/sourceString" as sources, "/targetString" as target and "One to One" as type
