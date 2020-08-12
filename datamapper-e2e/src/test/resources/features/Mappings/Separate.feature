@Mappings
@Separate
Feature: atlasmap is able to separate input to multiple fields

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"

  @SmokeTest
  @SimpleSeparate
  Scenario: Simple separate workflow, executed from Mapping Details window

    And click on create new mapping from source "sourceCombineString"

    And  set "targetString" as "target"
    And  set "targetAnotherString" as "target"

    And set "foo bar" value in source's "sourceCombineString"
    And set "foo" value in target's "targetString"
    And set "bar" value in target's "targetAnotherString"

    Then  save and verify mapping as "SimpleSeparate.json"

  @SeparateWithAllSeparators
  Scenario: Mixed types separate , executed from Mapping Details window
    And click on create new mapping from source "sourceCombineString"
#    And change index of "" id input set "targetString"
#
#    And add "targetChar" to separate
#    And add "targetInteger" to separate
#    And add "targetFloat" to separate
#    And add "targetLong" to separate
#    And add "targetShort" to separate
#    And add "targetDouble" to separate

    And add target "targetString" to active mapping
    And add target "targetChar" to active mapping
    And add target "targetInteger" to active mapping
    And add target "targetFloat" to active mapping
    And add target "targetLong" to active mapping
    And add target "targetShort" to active mapping
    And add target "targetDouble" to active mapping

    Then save and verify separate mapping with " " separator as "ComplexSeparateSpace.json"

    When select "Ampersand [&]" separator
    Then save and verify separate mapping with "&" separator as "ComplexSeparateAmpersand.json"

    When select "At Sign [@]" separator
    Then save and verify separate mapping with "@" separator as "ComplexSeparateAtSign.json"

#    When select "Colon [:]" separator
#    Then save and verify separate mapping with ":" separator as "ComplexSeparateAtSign.json"
#
#    When select "Backslash [\]" separator
#    Then save and verify separate mapping with "\" separator as "ComplexSeparateBackslash.json"

    When select "Comma [,]" separator
    Then save and verify separate mapping with "," separator as "ComplexSeparateComma.json"

    When select "Dash [-]" separator
    Then save and verify separate mapping with "-" separator as "ComplexSeparateDash.json"

    When select "Equal [=]" separator
    Then save and verify separate mapping with "=" separator as "ComplexSeparateEqual.json"

    When select "Hash [#]" separator
    Then save and verify separate mapping with "#" separator as "ComplexSeparateHash.json"

#    When select "Pipe [|]" separator
#    Then save and verify separate mapping with "|" separator as "ComplexSeparatePipe.json"

    When select "Semicolon [;]" separator
    Then save and verify separate mapping with ";" separator as "ComplexSeparateSemicolon.json"

    When select "Slash [/]" separator
    Then save and verify separate mapping with "/" separator as "ComplexSeparateSlash.json"

    When select "Underscore [_]" separator
    Then save and verify separate mapping with "_" separator as "ComplexSeparatePipe.json"
##

  @MixedIndexesSeparate
  Scenario: Mixed types separate, with mixed indexes executed from Mapping Details window
    And click on create new mapping from source "sourceCombineString"

    And set "targetString" as "target"

    And add "targetChar" to separate
    And add "targetInteger" to separate
    And add "targetFloat" to separate
    And add "targetLong" to separate
    And add target "targetShort" to active mapping
    And add target "targetDouble" to active mapping

    And change index of "targetChar" to "7" on "target"
    #necessary
    And sleep for "1000"
    And change index of "targetInteger" to "6" on "target"
    And change index of "targetFloat" to "5" on "target"
    And change index of "targetLong" to "4" on "target"
    And change index of "targetShort" to "3" on "target"

    And set expected data
      | targetString | targetChar | targetInteger | targetFloat | targetLong | targetShort | targetDouble |
      | numbers:     | A          | 2             | 3           | 4          | 5           | 6            |

    And set source data
      | sourceCombineString      |
      | numbers: 6.0 5 4 3.0 2 A |

    Then save and verify mapping as "ComplexSeparateMixedIndexes.json"

  @SmokeTest
  Scenario: Separate created
    And click on create new mapping from source "sourceCombineString"
    And add target "targetString" to active mapping
    And add target "targetChar" to active mapping
    And add target "targetInteger" to active mapping
    And add target "targetFloat" to active mapping
    And add target "targetLong" to active mapping
    And add target "targetShort" to active mapping
    And add target "targetDouble" to active mapping

    Then save and verify separate mapping with " " separator as "ComplexSeparateSpace.json"

#  @SeparateDragAndDrop
#  @DragAndDrop
#  @Ignore
#  Scenario: Separate created with CMD holding using drag'n'drop
#    When click on "targetString" holding cmd button
#    And click on "targetAnotherString" holding cmd button
#
#    And drag "targetAnotherString" and drop on "sourceCombineString"
#
#    And set source data
#      | sourceCombineString |
#      | foo bar             |
#
#    And set expected data
#      | targetString | targetAnotherString |
#      | foo          | bar                 |
#
#    Then save and verify mapping as "ComplexSeparateSpaceDragNDrop.json"

  @gaps
  Scenario: Separate with Gaps
    And click on create new mapping from source "sourceCombineString"

    And add target "targetInteger" to active mapping
    And add target "targetFloat" to active mapping
    And add target "targetLong" to active mapping
    And add target "targetDouble" to active mapping

    When select "Colon [:]" separator

    And change index of "targetDouble" to "8" on "target"
    And change index of "targetLong" to "6" on "target"
    And change index of "targetFloat" to "4" on "target"
    And change index of "targetInteger" to "2" on "target"

    And set source data
      | sourceCombineString |
      | :2::4.0::6::8.0     |

    And set expected data
      | targetInteger | targetFloat | targetLong | targetDouble |
      | 2             | 4           | 6          | 8            |

    Then save and verify mapping as "ComplexSeparateCMD.json"

