@Mappings
@MappingDetails
Feature: Simple mappings creation and test framework verification

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "true"
    And browser is opened

  @CreateModifyDelete
  Scenario: Create modify and delete mapping from table

    When add mapping from "sourceString" to "targetString"


    Then save and verify "mappingsTable" with
      | sourceString | targetString |
      | source       | source       |

    And delete "targetString" on "target"
    When set mapping to "targetAnotherString" from "sourceString"

    Then save and verify "mappingsTable2" with
      | sourceString | targetAnotherString |
      | source       | source              |

    When delete current mapping

    Then save and verify "mappingsTable3" with
      | sourceString | targetAnotherString |
      | source       | targetAnotherString |



#  Scenario: Mappings







