@Conversions
@FromFloat
Feature: conversion from FLOAT to all supported types 

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass

  Scenario: Conversions from FLOAT to all types and warning checks
    Given browser is opened
    And internal mapping is set to "true"

    When set mapping to "targetInteger" from "sourceFloat"
    Then check if "numeric format exceptions" warning from "FLOAT" to "INTEGER" is not displayed
    And check if "out of range exceptions" warning from "FLOAT" to "INTEGER" is displayed

    When set mapping to "targetString" from "sourceFloat"
    Then check if no warnings are displayed

    When set mapping to "targetLong" from "sourceFloat"
    Then check if "numeric format exceptions" warning from "FLOAT" to "LONG" is not displayed
    And check if "out of range exceptions" warning from "FLOAT" to "LONG" is displayed

    When set mapping to "targetDouble" from "sourceFloat"
    Then check if no warnings are displayed

    When set mapping to "targetFloat" from "sourceFloat"
    Then check if no warnings are displayed

    When  set mapping to "targetBoolean" from "sourceFloat"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceFloat"
    Then check if "numeric format exceptions" warning from "FLOAT" to "SHORT" is not displayed
    And check if "out of range exceptions" warning from "FLOAT" to "SHORT" is displayed

    When set mapping to "targetChar" from "sourceFloat"
    Then check if "numeric format exceptions" warning from "FLOAT" to "CHAR" is not displayed
    And check if "out of range exceptions" warning from "FLOAT" to "CHAR" is displayed

    When set mapping to "targetByte" from "sourceFloat"
    Then check if "numeric format exceptions" warning from "FLOAT" to "BYTE" is not displayed
    And check if "out of range exceptions" warning from "FLOAT" to "BYTE" is displayed

    And save mapping as "fromFloat.xml"
    And verify "fromFloat.xml"
