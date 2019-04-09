@Conversions
@FromShort
Feature: conversion to all supported types from Short

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass

  Scenario: Conversions from SHORT to all types and warning checks
    Given browser is opened
    And internal mapping is set to "true"

    When set mapping to "targetInteger" from "sourceShort"
    Then check if no warnings are displayed

    When set mapping to "targetString" from "sourceShort"
    Then check if no warnings are displayed

    When set mapping to "targetLong" from "sourceShort"
    Then check if no warnings are displayed

    When set mapping to "targetDouble" from "sourceShort"
    Then check if no warnings are displayed

    When set mapping to "targetFloat" from "sourceShort"
    Then check if no warnings are displayed

    When  set mapping to "targetBoolean" from "sourceShort"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceShort"
    Then check if no warnings are displayed
    When set mapping to "targetBigInteger" from "sourceShort"
    Then check if no warnings are displayed

    When set mapping to "targetBigDecimal" from "sourceShort"
    Then check if no warnings are displayed

    When set mapping to "targetChar" from "sourceShort"
    Then check if "numeric format exceptions" warning from "SHORT" to "CHAR" is not displayed
    And check if "out of range exceptions" warning from "SHORT" to "CHAR" is displayed

    When set mapping to "targetByte" from "sourceShort"
    Then check if "numeric format exceptions" warning from "SHORT" to "BYTE" is not displayed
    And check if "out of range exceptions" warning from "SHORT" to "BYTE" is displayed


    And save mapping as "fromShort.json"
    And verify "fromShort.json"
