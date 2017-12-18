@Conversions
@FromString
Feature: conversion from string

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass

  Scenario: Conversions from String to all types and warning checks
    Given browser is opened
    And internal mapping is set to "true"
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

    When set mapping to "targetString" from "sourceString"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "SHORT" is displayed
    And check if "out of range exceptions" warning from "STRING" to "SHORT" is displayed

    When  set mapping to "targetAnotherString" from "sourceString"
    Then check if no warnings are displayed

    And save mapping as "fromString.xml"
    And verify "fromString.xml"


    # And set mapping to "targetDate" from "sourceInteger"
  #  And set mapping to "targetChar" from "sourceFloat"
   # And set mapping to "targetByte" from "sourceFloat"
