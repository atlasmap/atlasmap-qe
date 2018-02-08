@Conversions
@FromBoolean
Feature: conversion from Boolean

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass


  Scenario: Conversions from BOOLEAN to all types and warning checks
    Given browser is opened
    And internal mapping is set to "false"


    When set mapping to "targetInteger" from "sourceBoolean"
    Then check if no warnings are displayed

    When set mapping to "targetString" from "sourceBoolean"
    Then check if no warnings are displayed

    When set mapping to "targetLong" from "sourceBoolean"
    Then check if no warnings are displayed

    When set mapping to "targetDouble" from "sourceBoolean"
    Then check if no warnings are displayed

    When set mapping to "targetFloat" from "sourceBoolean"
    Then check if no warnings are displayed

    When  set mapping to "targetBoolean" from "sourceBoolean"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceBoolean"
    Then check if no warnings are displayed

    When set mapping to "targetChar" from "sourceBoolean"
   # Then check if no warnings are displayed

    When set mapping to "targetByte" from "sourceBoolean"
    Then check if no warnings are displayed

    And set "false" value in source's "sourceBoolean"
    And set expected data
      | targetInteger | targetString | targetLong | targetDouble | targetFloat | targetBoolean | targetByte | targetShort |
      | 0             | false        | 0          | 0            | 0           | false         | 0          | 0           |
    And save and verify mapping as "fromDouble.xml"

