Feature: User should create simple mapping

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean

  Scenario: user creates few mappings. Created mapping file is processed and verified.
    Given browser is opened
    And set mapping from "sourceInteger" to "targetString"
    And set mapping to "targetCombineString" from "sourceDouble"
    And set mapping to "targetInteger" from "sourceDouble"
    And set mapping to "targetFloat" from "sourceDouble"
    And set mapping to "targetDouble" from "sourceDouble"
    And set mapping to "targetLong" from "sourceDouble"
    Then save mapping as "simpleMapping.xml"
    And verify "simpleMapping.xml"

  Scenario: User creates few mappings and sets source data for verification.
    Given browser is opened
    When set source data
      | sourceString | sourceCombineString | sourceInteger | sourceLong | sourceFloat | sourceDouble | sourceDate | sourceAnotherString |
      | String1      | String2             | 10            | 20         | 30          | 40           | 1989-05-05 | AnotherSource       |
    And set mapping from "sourceInteger" to "targetInteger"
    And set mapping from "sourceLong" to "targetLong"
    And set mapping from "sourceDouble" to "targetDouble"
    And set mapping from "sourceFloat" to "targetFloat"
    And set mapping from "sourceString" to "targetString"
    And set mapping from "sourceDate" to "targetDate"
    Then save mapping as "simpleMapping2.xml"
    And verify "simpleMapping2.xml"


  Scenario: User creates few mappings and sets source and target data for verification.
    Given browser is opened
    When set source data
      | sourceString | sourceCombineString | sourceAnotherString |
      | one          | two                 | three               |
    When set mapping from "sourceString" to "targetAnotherString"
    And set mapping from "sourceCombineString" to "targetString"
    And set mapping from "sourceAnotherString" to "targetCombineString"
    And set expected data
      | targetString | targetCombineString | targetAnotherString |
      | two          | three               | one                 |
    Then save mapping as "simpleMapping3.xml"
    And verify "simpleMapping3.xml"
