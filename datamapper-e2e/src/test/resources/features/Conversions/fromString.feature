@Conversions
@FromString
@SmokeTest
Feature: conversion from string

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "true"

  Scenario: Conversions from String to all types and warning checks

    And set source data
      | sourceString |
      | 2            |

    When set mapping to "targetInteger" from "sourceString"
    And check if warnings from "STRING" to "INTEGER" are displayed with messages
      | out of range exceptions    |
      | fractional part to be lost |
      | numeric format exceptions  |

    When set mapping to "targetLong" from "sourceString"
    And check if warnings from "STRING" to "LONG" are displayed with messages
      | out of range exceptions    |
      | fractional part to be lost |
      | numeric format exceptions  |

    When set mapping to "targetDouble" from "sourceString"
    And check if warnings from "STRING" to "DOUBLE" are displayed with messages
      | out of range exceptions    |
      | numeric format exceptions  |

    When set mapping to "targetFloat" from "sourceString"
    And check if warnings from "STRING" to "FLOAT" are displayed with messages
      | out of range exceptions    |
      | numeric format exceptions  |


    When set mapping to "targetShort" from "sourceString"
    And check if warnings from "STRING" to "SHORT" are displayed with messages
      | out of range exceptions    |
      | fractional part to be lost |
      | numeric format exceptions  |

    When set mapping to "targetBigDecimal" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "DECIMAL" is displayed

    When set mapping to "targetString" from "sourceString"
    Then check if no warnings are displayed

    When  set mapping to "targetAnotherString" from "sourceString"
    Then check if no warnings are displayed

    And save mapping as "fromString.json"
    And verify "fromString.json"


  Scenario: Conversion of bigDecimal and big integer
    When set source data
      | sourceString                                                   |
      | 12345678012345667899123456788999132456788898123456123456123456 |

    When set mapping to "targetBigDecimal" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "DECIMAL" is displayed

    When set mapping to "targetBigInteger" from "sourceString"
    Then check if "numeric format exceptions" warning from "STRING" to "BIG_INTEGER" is displayed



    # And set mapping to "targetDate" from "sourceInteger"
  #  And set mapping to "targetChar" from "sourceFloat"
   # And set mapping to "targetByte" from "sourceFloat"
