@Conversions
@FromBigInteger
Feature: conversion from LONG

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass

  Scenario: Conversions from LONG to all types and warning checks
    Given browser is opened
    And internal mapping is set to "true"

    When set mapping to "targetInteger" from "sourceBigInteger"
    Then check if "numeric format exceptions" warning from "BIG_INTEGER" to "INTEGER" is not displayed
    And check if "out of range exceptions" warning from "BIG_INTEGER" to "INTEGER" is displayed

    When set mapping to "targetLong" from "sourceBigInteger"
    Then check if no warnings are displayed

    When set mapping to "targetString" from "sourceBigInteger"
    Then check if no warnings are displayed

    When set mapping to "targetDouble" from "sourceBigInteger"
    Then check if no warnings are displayed

    When set mapping to "targetFloat" from "sourceBigInteger"
    Then check if no warnings are displayed

    When  set mapping to "targetBoolean" from "sourceBigInteger"
    Then check if no warnings are displayed

    When set mapping to "targetBigDecimal" from "sourceBigInteger"
    Then check if no warnings are displayed

    When  set mapping to "targetBigInteger" from "sourceBigInteger"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceBigInteger"
    Then check if "numeric format exceptions" warning from "BIG_INTEGER" to "SHORT" is not displayed
    And check if "out of range exceptions" warning from "BIG_INTEGER" to "SHORT" is displayed

    When set mapping to "targetChar" from "sourceBigInteger"
    Then check if "numeric format exceptions" warning from "BIG_INTEGER" to "CHAR" is not displayed
    And check if "out of range exceptions" warning from "BIG_INTEGER" to "CHAR" is displayed

#    When set mapping to "targetByte" from "sourceBigInteger"
#    Then check if "numeric format exceptions" warning from "BIG_INTEGER" to "BYTE" is not displayed
#    And check if "out of range exceptions" warning from "BIG_INTEGER" to "BYTE" is displayed

    And save mapping as "fromBigIngeter.xml"
    And verify "fromBigInteger.xml"
