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
    And  set "sourceAnotherString" as "source"
    And click on "targetCombineString"

    And set source data
      | sourceString | sourceAnotherString |
      | foo          | bar                 |

    And set expected data
      | targetCombineString |
      | foo bar             |
    Then  save and verify mapping as "SimpleCombine.json"


  Scenario: Mixed types combine , executed from Mapping Details window
    When click on "sourceString"
    And  set "targetCombineString" as "target"
    And add "/sourceChar" to combine
    And add "/sourceInteger" to combine
    And add "/sourceFloat" to combine
    And add "/sourceLong" to combine
    And add "/sourceShort" to combine
    And add "/sourceDouble" to combine
    And add "/sourceDate" to combine

   # And add click "Add Transformation" link
  #  And select "Prepend" transformation
  #  And for "input-string" input set "Combined: "
    And select "Space [ ]" separator
    Then save and verify combine mapping with " " separator as "ComplexCombine.json"

    When select "Colon [:]" separator
    Then save and verify combine mapping with ":" separator as "ComplexCombineColon.json"

    When select "Ampersand [&]" separator
    Then save and verify combine mapping with "&" separator as "ComplexCombineAmpersand.json"

    When select "At Sign [@]" separator
    Then save and verify combine mapping with "@" separator as "ComplexCombineAtSign.json"

    When select "Backslash [\]" separator
    Then save and verify combine mapping with "\" separator as "ComplexCombineBackslash.json"

    When select "Comma [,]" separator
    Then save and verify combine mapping with "," separator as "ComplexCombineComma.json"

    When select "Dash [-]" separator
    Then save and verify combine mapping with "-" separator as "ComplexCombineDash.json"

    When select "Equal [=]" separator
    Then save and verify combine mapping with "=" separator as "ComplexCombineEqual.json"

    When select "Hash [#]" separator
    Then save and verify combine mapping with "#" separator as "ComplexCombineHash.json"

    When select "Period [.]" separator
    Then save and verify combine mapping with "." separator as "ComplexCombinePeriod.json"

    When select "Pipe [|]" separator
    Then save and verify combine mapping with "|" separator as "ComplexCombinePipe.json"

    When select "Semicolon [;]" separator
    Then save and verify combine mapping with ";" separator as "ComplexCombineSemicolon.json"

    When select "Slash [/]" separator
    Then save and verify combine mapping with "/" separator as "ComplexCombineSlash.json"

    When select "Underscore [_]" separator
    Then save and verify combine mapping with "_" separator as "ComplexCombinePipe.json"

  @MixedIndexes
  Scenario: Mixed types combine with mixed indexes , executed from Mapping Details window
    When click on "sourceString"
    And  set "/targetCombineString" as "target"

    And add "/sourceDouble" to combine
    And add "/sourceInteger" to combine
    And add "/sourceFloat" to combine
    And add "/sourceLong" to combine
    And add "/sourceShort" to combine
    And add "/sourceChar" to combine
    And add "/sourceDate" to combine

    And change index of "sourceShort" to "3" on "source"
    And change index of "sourceLong" to "4" on "source"
    And change index of "sourceFloat" to "5" on "source"



    #And add click "Add Transformation" link
  #  And select "Prepend" transformation
  #  And for "input-string" input set "Combined: "
    And set source data
      | sourceString | sourceChar | sourceInteger | sourceFloat | sourceLong | sourceShort | sourceDouble |
      | numbers:     | 1          | 2             | 3           | 4          | 5           | 6            |

    And set expected data
      | targetCombineString                           |
      #| Combined: numbers: 6.0 5 4 3.0 2 1 1970-01-01T00:00:00Z |
      | numbers: 6.0 5 4 3.0 2 1 1970-01-01T00:00:00Z |

    Then save and verify mapping as "ComplexCombineMixedIndexes.json"



#  Scenario: Mixing indexes with drag'n'drop
#    When click on "sourceString"
#    And add select "Combine" action
#    And for "input-target-" id input set "/targetCombineString"
#
#    And add "/sourceDouble" to combine
#    And add "/sourceInteger" to combine
#    And add "/sourceFloat" to combine
#    And add "/sourceLong" to combine
#    And add "/sourceShort" to combine
#    And add "/sourceChar" to combine
#    And add "/sourceDate" to combine
#
#
#    #And change index of "sourceShort" to "3"
#    And drag "input-source-sourceShort" and drop on "input-source-sourceInteger"
#   # And change index of "sourceLong" to "4"
#   # And change index of "sourceFloat" to "5"


#
#    And add click "Add Transformation" link
#    And select "Prepend" transformation
#    And for "input-string" input set "Combined: "
#    And set source data
#      | sourceString | sourceChar | sourceInteger | sourceFloat | sourceLong | sourceShort | sourceDouble |
#      | numbers:     | 1          | 2             | 3           | 4          | 5           | 6            |
#
#    And set expected data
#      | targetCombineString                                     |
#      | Combined: numbers: 6.0 5 4 3.0 2 1 1970-01-01T00:00:00Z |

  @SmokeTest
  Scenario: Simple combine with holding CMD/control button
    When click on "sourceString" holding cmd button
    And click on "sourceChar" holding cmd button
    And click on "sourceInteger" holding cmd button
    And click on "sourceFloat" holding cmd button
    And click on "sourceLong" holding cmd button
    And click on "sourceShort" holding cmd button
    And click on "sourceDouble" holding cmd button
    And click on "sourceDate" holding cmd button

    And click on "targetCombineString"

   # And add click "Add Transformation" link
#    And select "Prepend" transformation
#    And for "input-string" input set "Combined: "

    Then save and verify combine mapping with " " separator as "ComplexCombineCMD.json"

  @DragAndDropCombine
  @Ignore
  Scenario: Simple combine with holding CMD/control button using drag'n'drop
    When click on "sourceString" holding cmd button
    And click on "sourceChar" holding cmd button
    And click on "sourceInteger" holding cmd button
    And click on "sourceFloat" holding cmd button
    And click on "sourceLong" holding cmd button
    And click on "sourceShort" holding cmd button
    And click on "sourceDouble" holding cmd button
    And click on "sourceDate" holding cmd button

    And drag "sourceDate" and drop on "targetCombineString"

    Then save and verify combine mapping with " " separator as "ComplexCombineCMDDragNDrop.json"

  @gaps
  Scenario: Gaps testing
    And click on "sourceInteger" holding cmd button
    And click on "sourceFloat" holding cmd button
    And click on "sourceLong" holding cmd button
    And click on "sourceDouble" holding cmd button
    And click on "targetCombineString"

    And change index of "sourceDouble" to "8" on "source"
    And change index of "sourceLong" to "6" on "source"
    And change index of "sourceFloat" to "4" on "source"
    And change index of "sourceInteger" to "2" on "source"

    And select "Colon [:]" separator
    And set source data
      | sourceInteger | sourceFloat | sourceLong | sourceDouble |
      | 2             | 4           | 6          | 8            |
    And set expected data
      | targetCombineString |
      | :2::4.0::6::8.0     |
   # And sleep for "3000000"
    Then save and verify mapping as "ComplexCombineGaps.json"
