@Conversions
@FromDouble
Feature: conversion from LONG

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass

  Scenario: Conversions from LONG to all types and warning checks
    Given browser is opened
    And internal mapping is set to "true"

    When set mapping to "targetInteger" from "sourceDouble"
    Then check if "numeric format exceptions" warning from "DOUBLE" to "INTEGER" is not displayed
    And check if "out of range exceptions" warning from "DOUBLE" to "INTEGER" is displayed

    When set mapping to "targetString" from "sourceDouble"
    Then check if no warnings are displayed

    When set mapping to "targetLong" from "sourceDouble"
    Then check if "numeric format exceptions" warning from "DOUBLE" to "LONG" is not displayed
    And check if "out of range exceptions" warning from "DOUBLE" to "LONG" is displayed

    When set mapping to "targetDouble" from "sourceDouble"
    Then check if no warnings are displayed

    When set mapping to "targetFloat" from "sourceDouble"
    Then check if "numeric format exceptions" warning from "DOUBLE" to "FLOAT" is not displayed
    And check if "out of range exceptions" warning from "DOUBLE" to "FLOAT" is displayed

    When  set mapping to "targetBoolean" from "sourceDouble"
    Then check if no warnings are displayed

    When  set mapping to "targetBigInteger" from "sourceDouble"
    Then check if "fractional part to be lost" warning from "DOUBLE" to "BIG_INTEGER" is displayed
    
    When  set mapping to "targetBigDecimal" from "sourceDouble"
    Then check if no warnings are displayed


    When set mapping to "targetShort" from "sourceDouble"
    Then check if "numeric format exceptions" warning from "DOUBLE" to "SHORT" is not displayed
    And check if "out of range exceptions" warning from "DOUBLE" to "SHORT" is displayed

    When set mapping to "targetChar" from "sourceDouble"
    Then check if "numeric format exceptions" warning from "DOUBLE" to "CHAR" is not displayed
    And check if "out of range exceptions" warning from "DOUBLE" to "CHAR" is displayed

    When set mapping to "targetByte" from "sourceDouble"
    Then check if "numeric format exceptions" warning from "DOUBLE" to "BYTE" is not displayed
    And check if "out of range exceptions" warning from "DOUBLE" to "BYTE" is displayed

    And save mapping as "fromDouble.xml"
    And verify "fromDouble.xml"


    # And set mapping to "targetDate" from "sourceDouble"
  #
   # And set mapping to "targetByte" from "sourceFloat"
