@Conversions
@FromChar
Feature: conversions from Char to all supported types

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "true"
    And set source data
      | sourceChar |
      | 5          |


  Scenario: Conversions from Char to all types and warning checks

    When set mapping to "targetInteger" from "sourceChar"
    Then check if no warnings are displayed

    When set mapping to "targetLong" from "sourceChar"
    Then check if no warnings are displayed

    When set mapping to "targetString" from "sourceChar"
    Then check if no warnings are displayed

    When set mapping to "targetDouble" from "sourceChar"
    Then check if no warnings are displayed

    When set mapping to "targetByte" from "sourceChar"
    Then check if "out of range exceptions" warning from "CHAR" to "BYTE" is displayed
    And check if "numeric format exceptions" warning from "CHAR" to "BYTE" is not displayed

    When set mapping to "targetFloat" from "sourceChar"
    Then check if no warnings are displayed

    When  set mapping to "targetBoolean" from "sourceChar"
    Then check if no warnings are displayed

    When set mapping to "targetShort" from "sourceChar"
    Then check if "out of range exceptions" warning from "CHAR" to "SHORT" is displayed
    And check if "numeric format exceptions" warning from "CHAR" to "SHORT" is not displayed

    When set mapping to "targetChar" from "sourceChar"
    Then check if no warnings are displayed

    And set expected data
      | targetChar | targetBoolean | targetByte |
      | 5          | false         | 53         |

    And save and verify mapping as "fromChar.xml"

