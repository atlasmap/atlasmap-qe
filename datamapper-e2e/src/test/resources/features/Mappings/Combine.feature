@Mappings
@Combine
Feature: atlasmap is able to combine multiple inputs into one filed

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"

  Scenario: Simple combine workflow, executed from Mapping Details window
    When click on "sourceString"
    And add select "Combine" action
    And for "input-target-" id input set "targetCombineString"

    And add click "Add Source" button
    And for "input-source-" id input set "sourceAnotherString"

    And set source data
      | sourceString | sourceAnotherString |
      | foo          | bar                 |

    And set expected data
      | targetCombineString |
      | foo bar             |
    Then  save and verify mapping as "SimpleCombine.xml"

  Scenario: Mixed types combine , executed from Mapping Details window
    When click on "sourceString"
    And add select "Combine" action
    When select "Colon" separator
    And for "input-target-" id input set "targetCombineString"


    And add "sourceChar" to combine
    And add "sourceInteger" to combine
    And add "sourceFloat" to combine
    And add "sourceLong" to combine
    And add "sourceShort" to combine
    And add "sourceDouble" to combine
    And add "sourceDate" to combine

    And add click "Add Transformation" button
    And select "Prepend" transformation
    And for "input-string" input set "Combined: "
    And set source data
      | sourceString | sourceChar | sourceInteger | sourceFloat | sourceLong | sourceShort | sourceDouble |
      | numbers:     | 1          | 2             | 3           | 4          | 5           | 6            |

    And set expected data
      | targetCombineString |
      | Combined: numbers: 1 2 3.0 4 5 6.0 1970-01-01T00:00:00Z |

    Then save and verify mapping as "ComplexCombine.xml"

    # NOT IMPLEMENTED
#    When select "Colon" separator
#    And set source data
#      | sourceString | sourceChar | sourceInteger | sourceFloat | sourceLong | sourceShort | sourceDouble |
#      | numbers:     | 1          | 2             | 3           | 4          | 5           | 6            |
#
#    And set expected data
#      | targetCombineString |
#      | Combined: numbers::1:2:3.0:4:5:6.0:Thu Jan 01 01:00:00 CET 1970 |
#
#    Then save and verify mapping as "ComplexCombineColon.xml"




  Scenario: Mixed types combine with mixed indexes , executed from Mapping Details window
    When click on "sourceString"
    And add select "Combine" action
    And for "input-target-" id input set "targetCombineString"


    And add "sourceChar" to combine
    And for "input-Index" input with "2" set "7"

    And add "sourceInteger" to combine
    And for "input-Index" input with "8" set "6"

    And add "sourceFloat" to combine
    And for "input-Index" input with "8" set "5"

    And add "sourceLong" to combine
    And for "input-Index" input with "8" set "4"

    And add "sourceShort" to combine
    And for "input-Index" input with "8" set "3"

    And add "sourceDouble" to combine
    And for "input-Index" input with "8" set "2"

    And add "sourceDate" to combine
    And for "input-Index" input with "8" set "8"



    And add click "Add Transformation" button
    And select "Prepend" transformation
    And for "input-string" input set "Combined: "
    And set source data
      | sourceString | sourceChar | sourceInteger | sourceFloat | sourceLong | sourceShort | sourceDouble |
      | numbers:     | 1          | 2             | 3           | 4          | 5           | 6            |

    And set expected data
      | targetCombineString |
      | Combined: numbers: 6.0 5 4 3.0 2 1 1970-01-01T00:00:00Z |

    Then save and verify mapping as "ComplexCombineMixedIndexes.xml"
