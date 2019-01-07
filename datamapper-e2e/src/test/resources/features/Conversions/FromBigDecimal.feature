@Conversions
@FromBigDecimal
Feature: conversion from LONG

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass

  Scenario: Conversions from LONG to all types and warning checks
    Given browser is opened
    And internal mapping is set to "true"

    When set mapping to "targetInteger" from "sourceBigDecimal"
    Then check if "numeric format exceptions" warning from "DECIMAL" to "INTEGER" is not displayed
    And check if "out of range exceptions" warning from "DECIMAL" to "INTEGER" is displayed

    When set mapping to "targetLong" from "sourceBigDecimal"
    Then check if no warnings are displayed

    When set mapping to "targetString" from "sourceBigDecimal"
    Then check if no warnings are displayed

    When set mapping to "targetDouble" from "sourceBigDecimal"
    Then check if no warnings are displayed

    When set mapping to "targetFloat" from "sourceBigDecimal"
    Then check if no warnings are displayed

    When  set mapping to "targetBoolean" from "sourceBigDecimal"
    Then check if no warnings are displayed

    When set mapping to "targetBigDecimal" from "sourceBigDecimal"
    Then check if no warnings are displayed

    When  set mapping to "targetBigInteger" from "sourceBigDecimal"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceBigDecimal"
    Then check if "numeric format exceptions" warning from "DECIMAL" to "SHORT" is not displayed
    And check if "out of range exceptions" warning from "DECIMAL" to "SHORT" is displayed

    When set mapping to "targetChar" from "sourceBigDecimal"
    Then check if "numeric format exceptions" warning from "DECIMAL" to "CHAR" is not displayed
    And check if "out of range exceptions" warning from "DECIMAL" to "CHAR" is displayed

#    When set mapping to "targetByte" from "sourceBigDecimal"
#    Then check if "numeric format exceptions" warning from "DECIMAL" to "BYTE" is not displayed
#    And check if "out of range exceptions" warning from "DECIMAL" to "BYTE" is displayed

    And save mapping as "fromBigDecimal.xml"
    And verify "fromBigDecimal.xml"
