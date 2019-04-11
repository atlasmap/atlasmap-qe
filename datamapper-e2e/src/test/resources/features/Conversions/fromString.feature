@Conversions
@FromString
@SmokeTest
Feature: conversion from string

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "true"

  Scenario: Conversions from String to all types and warning checks

    And set source data
      | sourceString |
      | 2            |

    When set mapping to "targetInteger" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "INTEGER" is displayed
    And check if "out of range exceptions" warning from "STRING" to "INTEGER" is displayed

    When set mapping to "targetLong" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "LONG" is displayed
    And check if "out of range exceptions" warning from "STRING" to "LONG" is displayed

    When set mapping to "targetDouble" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "DOUBLE" is displayed
    And check if "out of range exceptions" warning from "STRING" to "DOUBLE" is displayed

    When set mapping to "targetFloat" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "FLOAT" is displayed
    And check if "out of range exceptions" warning from "STRING" to "FLOAT" is displayed

    When set mapping to "targetShort" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "SHORT" is displayed
    And check if "out of range exceptions" warning from "STRING" to "SHORT" is displayed


    When set mapping to "targetBigDecimal" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "DECIMAL" is displayed

    When set mapping to "targetString" from "sourceString"
    Then check if no warnings are displayed

    When  set mapping to "targetAnotherString" from "sourceString"
    Then check if no warnings are displayed

    And save mapping as "fromString.xml"
    And verify "fromString.xml"


  Scenario: Conversion of bigDecimal and big integer
    When set source data
      | sourceString                                                   |
      | 12345678012345667899123456788999132456788898123456123456123456 |

    When set mapping to "targetBigDecimal" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "DECIMAL" is displayed

    When set mapping to "targetBigInteger" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "BIG_INTEGER" is displayed



    # And set mapping to "targetDate" from "sourceInteger"
  #  And set mapping to "targetChar" from "sourceFloat"
   # And set mapping to "targetByte" from "sourceFloat"
