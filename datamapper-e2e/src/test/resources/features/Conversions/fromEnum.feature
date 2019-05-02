@Conversions
@FromEnum
@SmokeTest
Feature: conversion from enum

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "true"

  Scenario: Conversions from Enum to all types and warning checks
    And set source data
      | sourceEnum |
      | VALUE1     |

    When click on mapping to "targetInteger" from "sourceEnum"
    Then check if danger warning contains "The field 'SourceEnum' cannot be selected, Enumeration fields are not valid for this mapping." message

    When click on mapping to "targetLong" from "sourceEnum"
    Then check if danger warning contains "The field 'SourceEnum' cannot be selected, Enumeration fields are not valid for this mapping." message

    When click on mapping to "targetDouble" from "sourceEnum"
    Then check if danger warning contains "The field 'SourceEnum' cannot be selected, Enumeration fields are not valid for this mapping." message

    When click on mapping to "targetFloat" from "sourceEnum"
    Then check if danger warning contains "The field 'SourceEnum' cannot be selected, Enumeration fields are not valid for this mapping." message

    When click on mapping to "targetShort" from "sourceEnum"
    Then check if danger warning contains "The field 'SourceEnum' cannot be selected, Enumeration fields are not valid for this mapping." message

    When click on mapping to "targetBigDecimal" from "sourceEnum"
    Then check if danger warning contains "The field 'SourceEnum' cannot be selected, Enumeration fields are not valid for this mapping." message

    When click on mapping to "targetString" from "sourceEnum"
    Then check if danger warning contains "The field 'SourceEnum' cannot be selected, Enumeration fields are not valid for this mapping." message

    When set mapping to "targetEnum" from "sourceEnum"
    Then check if no warnings are displayed

    And save mapping as "fromEnum.json"
    And verify "fromEnum.json"
