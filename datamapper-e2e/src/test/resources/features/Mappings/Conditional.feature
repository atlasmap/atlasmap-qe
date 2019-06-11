@Mappings
@Conditional

Feature: conditional mappings

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"

  Scenario: basic conditional mapping by Control key
    When click on "targetInteger"
    And set mapping condition to "if(ISEMPTY(@{sourceString}), @{sourceInteger}, @{sourceShort})" by Control key

    And set source data
      | sourceString | sourceInteger | sourceShort   |
      |              | 1             | 2             |

    And set expected data
      | targetInteger  |
      | 1             |

    And save mapping as "conditional_ctrl.xml"
    And verify "conditional_ctrl.xml"

  Scenario: basic conditional mapping by auto completion
    When click on "targetInteger"
    And set mapping condition to "if(ISEMPTY(@{sourceString}), @{sourceInteger}, @{sourceShort})" by auto completion

    And set source data
      | sourceString | sourceInteger | sourceShort   |
      |              | 1             | 2             |

    And set expected data
      | targetInteger  |
      | 1             |

    And save mapping as "conditional_auto_completion.xml"
    And verify "conditional_auto_completion.xml"
