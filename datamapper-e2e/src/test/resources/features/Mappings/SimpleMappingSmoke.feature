@SmokeTest
@Ignore
Feature: Simple mappings creation and test framework verification

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "true"

  Scenario: Few mapping are created, processed and verified.
    Given browser is opened

    And click on create new mapping from target "targetString"
    And add mapping from "sourceInteger" to "targetString"

    And click on create new mapping from source "sourceDouble"
    And set mapping to "targetCombineString" from "sourceDouble"
    And set mapping to "targetInteger" from "sourceDouble"
    And set mapping to "targetFloat" from "sourceDouble"
    And set mapping to "targetDouble" from "sourceDouble"
    And set mapping to "targetLong" from "sourceDouble"
    Then save mapping as "simpleMapping.json"
    And verify "simpleMapping.json"

#  Scenario: Setting  source data for mapping verification.
#    Given browser is opened
#    When set source data
#      | sourceString | sourceCombineString | sourceInteger | sourceLong | sourceFloat | sourceDouble  | sourceAnotherString |
#      | String1      | String2             | 10            | 20         | 30          | 40            | AnotherSource       |
#    And set mapping from "sourceInteger" to "targetInteger"
#    And set mapping from "sourceLong" to "targetLong"
#    And set mapping from "sourceDouble" to "targetDouble"
#    And set mapping from "sourceFloat" to "targetFloat"
#    And set mapping from "sourceString" to "targetString"
#    Then save mapping as "simpleMapping2.json"
#    And verify "simpleMapping2.json"
#
#  Scenario: Setting source and target data for verification.
#    Given browser is opened
#    When set source data
#      | sourceString | sourceCombineString | sourceAnotherString |
#      | one          | two                 | three               |
#    When add mapping from "sourceString" to "targetAnotherString"
#    And set mapping from "sourceCombineString" to "targetString"
#    And set mapping from "sourceAnotherString" to "targetCombineString"
#    And set expected data
#      | targetString | targetCombineString | targetAnotherString |
#      | two          | three               | one                 |
#    Then save mapping as "simpleMapping3.json"
#    And verify "simpleMapping3.json"
