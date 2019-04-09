@Conversions
@FromInteger
Feature: conversions from INTEGER to all supported types

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass

  Scenario: Conversions from INTEGER to all types and warning checks
    Given browser is opened
    And internal mapping is set to "true"
  
    When set mapping to "targetInteger" from "sourceInteger"
    Then check if no warnings are displayed

    When set mapping to "targetString" from "sourceInteger"
    Then check if no warnings are displayed

    When set mapping to "targetLong" from "sourceInteger"
    Then check if no warnings are displayed

    When set mapping to "targetDouble" from "sourceInteger"
    Then check if no warnings are displayed

    When set mapping to "targetBigInteger" from "sourceInteger"
    Then check if no warnings are displayed

    When set mapping to "targetBigDecimal" from "sourceInteger"
    Then check if no warnings are displayed

    When set mapping to "targetFloat" from "sourceInteger"
    Then check if "out of range exceptions" warning from "INTEGER" to "FLOAT" is displayed
    And check if "numeric format exceptions" warning from "INTEGER" to "FLOAT" is not displayed

    When  set mapping to "targetBoolean" from "sourceInteger"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceInteger"
    Then check if "numeric format exceptions" warning from "INTEGER" to "SHORT" is not displayed
    And check if "out of range exceptions" warning from "INTEGER" to "SHORT" is displayed

    When set mapping to "targetChar" from "sourceInteger"
    Then check if "numeric format exceptions" warning from "INTEGER" to "CHAR" is not displayed
    And check if "out of range exceptions" warning from "INTEGER" to "CHAR" is displayed

    And set mapping to "targetByte" from "sourceInteger"
    Then check if "numeric format exceptions" warning from "INTEGER" to "BYTE" is not displayed
    And check if "out of range exceptions" warning from "INTEGER" to "BYTE" is displayed

    And save mapping as "fromInteger.json"
    And verify "fromInteger.json"


    # And set mapping to "targetDate" from "sourceInteger"
    #

