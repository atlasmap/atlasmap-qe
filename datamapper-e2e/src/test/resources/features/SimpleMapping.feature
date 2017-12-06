Feature: User should create simple mapping

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean

  Scenario: User creates few mappings. Created mapping file is processed and verified.
    Given Browser is opened
    And Set mapping from "sourceInteger" to "targetString"
    And Set mapping to "targetCombineString" from "sourceDouble"
    And Set mapping to "targetInteger" from "sourceDouble"
    And Set mapping to "targetFloat" from "sourceDouble"
    And Set mapping to "targetDouble" from "sourceDouble"
    And Set mapping to "targetLong" from "sourceDouble"
    Then Save mapping as "simpleMapping.xml"
    And Verify "simpleMapping.xml"

  Scenario: User creates few mappings and sets source data for verification.
    Given Browser is opened
    When Set source data
      | sourceString | sourceCombineString | sourceInteger | sourceLong | sourceFloat | sourceDouble | sourceDate | sourceAnotherString |
      | String1      | String2             | 10            | 20         | 30          | 40           | 1989-05-05 | AnotherSource       |
    And Set mapping from "sourceInteger" to "targetInteger"
    And Set mapping from "sourceLong" to "targetLong"
    And Set mapping from "sourceDouble" to "targetDouble"
    And Set mapping from "sourceFloat" to "targetFloat"
    And Set mapping from "sourceString" to "targetString"
    And Set mapping from "sourceDate" to "targetDate"
    Then Save mapping as "simpleMapping2.xml"
    And Verify "simpleMapping2.xml"


  Scenario: User creates few mappings and sets source and target data for verification.
    Given Browser is opened
    When Set source data
      | sourceString | sourceCombineString | sourceAnotherString |
      | one          | two                 | three               |
    When Set mapping from "sourceString" to "targetAnotherString"
    And Set mapping from "sourceCombineString" to "targetString"
    And Set mapping from "sourceAnotherString" to "targetCombineString"
    And Set expected data
      | targetString | targetCombineString | targetAnotherString |
      | two          | three               | one                 |
    Then Save mapping as "simpleMapping3.xml"
    And Verify "simpleMapping3.xml"
