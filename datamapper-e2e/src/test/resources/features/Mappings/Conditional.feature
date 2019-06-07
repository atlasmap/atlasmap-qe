@Mappings
@Conditional

Feature: conditional mappings

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "true"

  Scenario: basic IF statement
    When click on "targetInteger"
    And set mapping condition to "if(ISEMPTY(@{sourceInteger}), @{sourceShort}, @{sourceInteger})" by Control key

    And set source data
      | sourceInteger | sourceShort |
      | 50            | 3          |

    And set expected data
      | targetInteger |
      | 50            |

    And save mapping as "if.xml"
    And verify "if.xml"
