@MappingPreview
@MappingsTablePreviews
Feature: mappings table

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
    And reveal mapping table

  @SmokeTest
  Scenario: check Preview of single mapping
    When add mapping from "sourceInteger" to "targetInteger"
    And add mapping from "sourceString" to "targetString"
    And add mapping from "sourceString" to "targetCombineString"
    And Show mapping preview
    Then check that on "0" row number is for "5" source value displayed "5" target preview

    And sleep for "5000"

  Scenario: check combine mapping in table anch check if table is filled properly
    When add mapping from "sourceInteger" to "targetString"
    And add select "Combine" action
    And add "sourceFloat" to combine
    And add "sourceString" to combine
    And Show mapping preview
    Then check that on "0" row number is for "5;6;7" source value displayed "6 5.0 7" target preview

  Scenario: check separate mapping in table anch check if table is filled properly
    When add mapping from "sourceCombineString" to "targetString"
    And add select "Separate" action
    And add "targetInteger" to separate
    And add "targetFloat" to separate
    And Show mapping preview
    Then check that on "0" row number is for "5 6 7" source value displayed "7,6,5" target preview
