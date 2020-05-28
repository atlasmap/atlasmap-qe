@Conversions
@FromByte
Feature: conversions from BYTE to all supported types

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass

  Scenario: Conversions from BYTE to all types and warning checks
    Given browser is opened
    And internal mapping is set to "true"

    When set mapping to "targetInteger" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetString" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetLong" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetDouble" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetFloat" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetBoolean" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetChar" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetByte" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetBigInteger" from "sourceByte"
    Then check if no warnings are displayed

    When set mapping to "targetBigDecimal" from "sourceByte"
    Then check if no warnings are displayed

    And save mapping as "fromByte.json"
    And verify "fromByte.json"

