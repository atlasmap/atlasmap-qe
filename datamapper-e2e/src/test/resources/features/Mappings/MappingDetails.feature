@Mappings
@MappingDetails
Feature: Simple mappings creation and test framework verification

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "true"
    And browser is opened
    And open mapping details window

  Scenario: Create modify and delete mapping from table
    And for "input-target-" id input set "targetString"
    And for "input-source-" id input set "sourceString"

    Then save and verify "mappingsTable" with
      | sourceString | targetString |
      | source       | source       |

    And for "input-target-targetString" id input set "targetAnotherString"

    Then save and verify "mappingsTable2" with
      | sourceString | targetAnotherString |
      | source       | source |

    When delete current mapping
    And sleep for "1000"
     And click on "sourceInteger"
    Then save "mappingsTable3" verify negative with
      | sourceString | targetAnotherString |
      | source       | source |
  


#  Scenario: Mappings







