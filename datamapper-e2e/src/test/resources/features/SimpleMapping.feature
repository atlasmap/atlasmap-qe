Feature: User should create simple mapping

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean

  Scenario: User browse atlasmap
    Given User opens browser
  #  When User clicks on "sourceInteger"$
    #And  User clicks on "targetDouble"
    And User sets mapping from "sourceInteger" to "targetString"
    And User sets mapping to "targetCombineString" from "sourceDouble"
    And User sets mapping to "targetInteger" from "sourceDouble"
    And User sets mapping to "targetFloat" from "sourceDouble"
    And User sets mapping to "targetDouble" from "sourceDouble"
    And User sets mapping to "targetLong" from "sourceDouble"
    Then User saves mapping as "simpleMapping.xml"
    And User verifies "simpleMapping.xml"

    # this.sourceString = "string";
#  this.sourceCombineString = "combine";
#  this.sourceInteger = 1;
#  this.sourceLong = 2L;
#  this.sourceFloat = 3f;
#  this.sourceDouble = 4d;
#  this.sourceDate = new Date(0);
#  this.sourceAnotherString = "Another";

  Scenario: User browse atlasmap
    Given User opens browser
    When User sets source data
    |sourceString|sourceCombineString|sourceInteger|sourceLong|sourceFloat|sourceDouble|sourceDate|sourceAnotherString|
    |String1|"String2|10| 20|30 |40|1989-05-05|AnotherSource|
    And User sets mapping from "sourceInteger" to "targetInteger"
    And User sets mapping from "sourceLong" to "targetLong"
    And User sets mapping from "sourceDouble" to "targetDouble"
    And User sets mapping from "sourceFloat" to "targetFloat"
    And User sets mapping from "sourceString" to "targetString"
    And User sets mapping from "sourceDate" to "targetDate"
    Then User saves mapping as "simpleMapping2.xml"
    And User verifies "simpleMapping2.xml"
