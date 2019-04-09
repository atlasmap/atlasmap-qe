@Conversions
@FromLong
Feature: conversion from LONG

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass

  Scenario: Conversions from LONG to all types and warning checks
    Given browser is opened
    And internal mapping is set to "true"

    When set mapping to "targetInteger" from "sourceLong"
    Then check if "numeric format exceptions" warning from "LONG" to "INTEGER" is not displayed
    And check if "out of range exceptions" warning from "LONG" to "INTEGER" is displayed

    When set mapping to "targetLong" from "sourceLong"
    Then check if no warnings are displayed

    When set mapping to "targetString" from "sourceLong"
    Then check if no warnings are displayed

    When set mapping to "targetDouble" from "sourceLong"
    Then check if no warnings are displayed

    When set mapping to "targetFloat" from "sourceLong"
    Then check if no warnings are displayed

    When  set mapping to "targetBoolean" from "sourceLong"
    Then check if no warnings are displayed

    When set mapping to "targetBigDecimal" from "sourceLong"
    Then check if no warnings are displayed

    When  set mapping to "targetBigInteger" from "sourceLong"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceLong"
    Then check if "numeric format exceptions" warning from "LONG" to "SHORT" is not displayed
    And check if "out of range exceptions" warning from "LONG" to "SHORT" is displayed

    When set mapping to "targetChar" from "sourceLong"
    Then check if "numeric format exceptions" warning from "LONG" to "CHAR" is not displayed
    And check if "out of range exceptions" warning from "LONG" to "CHAR" is displayed

    When set mapping to "targetByte" from "sourceLong"
    Then check if "numeric format exceptions" warning from "LONG" to "BYTE" is not displayed
    And check if "out of range exceptions" warning from "LONG" to "BYTE" is displayed

    And save mapping as "fromLONG.json"
    And verify "fromLONG.json"


    # And set mapping to "targetDate" from "sourceLong"
  #
   # And set mapping to "targetByte" from "sourceFloat"
