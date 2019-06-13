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
    When set mapping to "targetInteger" from "sourceInteger"
    When set mapping to "targetEnum" from "sourceEnum"

    And set source data
      | sourceEnum |
      | VALUE2     |

    And set expected data
      | sourceEnum |
      | VALUE2     |

    Then save and verify mapping as "enumToEnum.json"

  Scenario: Conversions from Enum to all types and warning checks
    # mapping to sourceEnum
    When click on mapping to "sourceEnum" from "targetInteger"
    Then check if danger warning contains "The field 'targetInteger' cannot be selected, only Enumeration fields are valid for this mapping." message

    When click on mapping to "sourceEnum" from "targetLong"
    Then check if danger warning contains "The field 'targetLong' cannot be selected, only Enumeration fields are valid for this mapping." message

    When click on mapping to "sourceEnum" from "targetDouble"
    Then check if danger warning contains "The field 'targetDouble' cannot be selected, only Enumeration fields are valid for this mapping." message

    When click on mapping to "sourceEnum" from "targetFloat"
    Then check if danger warning contains "The field 'targetFloat' cannot be selected, only Enumeration fields are valid for this mapping." message

    When click on mapping to "sourceEnum" from "targetShort"
    Then check if danger warning contains "The field 'targetShort' cannot be selected, only Enumeration fields are valid for this mapping." message

    When click on mapping to "sourceEnum" from "targetBigDecimal"
    Then check if danger warning contains "The field 'targetBigDecimal' cannot be selected, only Enumeration fields are valid for this mapping." message

    When click on mapping to "sourceEnum" from "targetString"
    Then check if danger warning contains "The field 'targetString' cannot be selected, only Enumeration fields are valid for this mapping." message

    # mapping from sourceEnum
    When click on mapping to "targetString" from "sourceEnum"
    Then check if danger warning contains "The field 'sourceEnum' cannot be selected, Enumeration fields are not valid for this mapping." message

    # mapping from enum to enum
    When click on mapping to "targetEnum" from "sourceEnum"
    Then check if no warnings are displayed

    And save and verify mapping as "fromEnum.json"
