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
    And set "sourceString" as "source"
    And set "targetString" as "target"


    Then save and verify "mappingsTable" with
      | sourceString | targetString |
      | source       | source       |
#fixme
   # And sleep for "30000000"
    And delete "targetString" on "target"
    And set "targetAnotherString" as "target"

    Then save and verify "mappingsTable2" with
      | sourceString | targetAnotherString |
      | source       | source |

    When delete current mapping
    And sleep for "1000"
    
    Then save "mappingsTable3" verify negative with
      | sourceString | targetAnotherString |
      | source       | source |
  


#  Scenario: Mappings







