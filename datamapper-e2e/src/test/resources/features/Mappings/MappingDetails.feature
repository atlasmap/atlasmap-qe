@Mappings
@MappingDetails
Feature: Simple mappings creation and test framework verification

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "true"
    And browser is opened
    And open mapping details window

  @CreateModifyDelete
  Scenario: Create modify and delete mapping from table
    And set "sourceString" as "source"
    And set "targetString" as "target"


    Then save and verify "mappingsTable" with
      | sourceString | targetString |
      | source       | source       |

    And delete "targetString" on "target"
    And set "targetAnotherString" as "target"

    Then save and verify "mappingsTable2" with
      | sourceString | targetAnotherString |
      | source       | source              |

    When delete current mapping

    Then save and verify "mappingsTable3" with
      | sourceString | targetAnotherString |
      | source       | targetAnotherString |



#  Scenario: Mappings







