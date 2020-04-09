@Conversions
@FromEnum
@SmokeTest
Feature: conversion from enum

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"

  Scenario: Mapping from enum to another enum
    When set mapping to "targetEnum" from "sourceEnum"

    And set source data
      | sourceEnum |
      | VALUE3     |

    And set expected data
      | targetEnum |
      | VALUE3     |

    Then save and verify mapping as "enumToEnum.json"

  Scenario: Conversions from Enum to all types and warning checks
    When set mapping to "targetInteger" from "sourceEnum"
    Then check if danger warning contains "The field 'targetInteger' cannot be selected, only Enumeration fields are valid for this mapping." message

    When set mapping to "targetLong" from "sourceEnum"
    Then check if danger warning contains "The field 'targetLong' cannot be selected, only Enumeration fields are valid for this mapping." message

    When set mapping to "targetDouble" from "sourceEnum"
    Then check if danger warning contains "The field 'targetDouble' cannot be selected, only Enumeration fields are valid for this mapping." message

    When set mapping to "targetFloat" from "sourceEnum"
    Then check if danger warning contains "The field 'targetFloat' cannot be selected, only Enumeration fields are valid for this mapping." message

    When set mapping to "targetShort" from "sourceEnum"
    Then check if danger warning contains "The field 'targetShort' cannot be selected, only Enumeration fields are valid for this mapping." message

    When set mapping to "targetBigDecimal" from "sourceEnum"
    Then check if danger warning contains "The field 'targetBigDecimal' cannot be selected, only Enumeration fields are valid for this mapping." message

    When set mapping to "targetString" from "sourceEnum"
    Then check if danger warning contains "The field 'targetString' cannot be selected, only Enumeration fields are valid for this mapping." message

    # mapping from enum to enum
    When set mapping to "targetEnum" from "sourceEnum"
    Then check if no warnings are displayed
