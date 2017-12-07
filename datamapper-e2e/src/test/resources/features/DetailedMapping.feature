Feature: Simple mappings creation and test framework verification

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean

  Scenario: Creating mapping without necessary conversion using source and target tables
    Given browser is opened
    When set mapping from "sourceAnotherString" to "targetAnotherString"
    And set mapping from "sourceInteger" to "targetInteger"
    And set mapping from "sourceLong" to "targetLong"
    And set mapping from "sourceDouble" to "targetDouble"
    And set mapping from "sourceFloat" to "targetFloat"
    And set mapping from "sourceString" to "targetString"
    And set mapping from "sourceDate" to "targetDate"
    Then save mapping as "simpleApproach.xml"
    And verify "simpleApproach.xml"


  Scenario:
