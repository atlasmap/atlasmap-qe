@Mappings
@Separate
Feature: atlasmap is able to separate input to multiple fields

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"

  Scenario: Simple separate workflow, executed from Mapping Details window
    When click on "sourceCombineString"
    And add select "Separate" action
    And for "input-target-" id input set "targetString"

    And add click "Add Target" button
    And for "input-target-" id input set "targetAnotherString"

    And set "foo bar" value in source's "sourceCombineString"
    And set "foo" value in target's "targetString"
    And set "bar" value in target's "targetAnotherString"

    Then  save and verify mapping as "SimpleSeparate.json"

  Scenario: Mixed types separate , executed from Mapping Details window
    When click on "sourceCombineString"
    And add select "Separate" action
    And for "input-target-" id input set "targetString"


    And add "targetChar" to separate
    And add "targetInteger" to separate
    And add "targetFloat" to separate
    And add "targetLong" to separate
    And add "targetShort" to separate
    And add "targetDouble" to separate

    Then save and verify separate mapping with " " separator as "ComplexSeparateSpace.json"

    When select "Ampersand [&]" separator
    Then save and verify separate mapping with "&" separator as "ComplexSeparateAmpersand.json"

    When select "At Sign [@]" separator
    Then save and verify separate mapping with "@" separator as "ComplexSeparateAtSign.json"

    When select "Backslash [\]" separator
    Then save and verify separate mapping with "\" separator as "ComplexSeparateBackslash.json"

    When select "Comma [,]" separator
    Then save and verify separate mapping with "," separator as "ComplexSeparateComma.json"

    When select "Dash [-]" separator
    Then save and verify separate mapping with "-" separator as "ComplexSeparateDash.json"

    When select "Equal [=]" separator
    Then save and verify separate mapping with "=" separator as "ComplexSeparateEqual.json"

    When select "Hash [#]" separator
    Then save and verify separate mapping with "#" separator as "ComplexSeparateHash.json"

    When select "Pipe [|]" separator
    Then save and verify separate mapping with "|" separator as "ComplexSeparatePipe.json"

    When select "Semicolon [;]" separator
    Then save and verify separate mapping with ";" separator as "ComplexSeparateSemicolon.json"

    When select "Slash [/]" separator
    Then save and verify separate mapping with "/" separator as "ComplexSeparateSlash.json"

    When select "Underscore [_]" separator
    Then save and verify separate mapping with "_" separator as "ComplexSeparatePipe.json"
##
  Scenario: Mixed types separate, with mixed indexes executed from Mapping Details window
    When click on "sourceCombineString"
    And add select "Separate" action
    And for "input-target-" id input set "targetString"


    And add "targetChar" to separate
    And add "targetInteger" to separate
    And add "targetFloat" to separate
    And add "targetLong" to separate
    And add "targetShort" to separate
    And add "targetDouble" to separate
    And for "input-target-targetChar" id input with "2" set "7"
    #necessary
    And sleep for "1000"
    And for "input-target-targetInteger" id input with "2" set "6"
    And for "input-target-targetFloat" id input with "2" set "5"
    And for "input-target-targetLong" id input with "2" set "4"
    And for "input-target-targetShort" id input with "2" set "3"

    And set expected data
      | targetString | targetChar | targetInteger | targetFloat | targetLong | targetShort | targetDouble |
      | numbers:     | A          | 2             | 3           | 4          | 5           | 6            |

    And set source data
      | sourceCombineString      |
      | numbers: 6.0 5 4 3.0 2 A |

    Then save and verify mapping as "ComplexSeparateMixedIndexes.json"

  @SmokeTest
  Scenario: Separate created with CMD holding
    When click on "sourceCombineString"
    And click on "targetString" holding cmd button
    And click on "targetChar" holding cmd button
    And click on "targetInteger" holding cmd button
    And click on "targetFloat" holding cmd button
    And click on "targetLong" holding cmd button
    And click on "targetShort" holding cmd button
    And click on "targetDouble" holding cmd button

    Then save and verify separate mapping with " " separator as "ComplexSeparateSpace.json"


  Scenario: Separate with Gaps
    When click on "sourceCombineString"

    And click on "targetInteger" holding cmd button
    And click on "targetFloat" holding cmd button
    And click on "targetLong" holding cmd button
    And click on "targetDouble" holding cmd button

    When select "Colon [:]" separator

    And for "input-target-targetDouble" id input with "4" set "8"
    And for "input-target-targetLong" id input with "3" set "6"
    And for "input-target-targetFloat" id input with "2" set "4"
    And for "input-target-targetInteger" id input with "1" set "2"

    And set source data
      | sourceCombineString |
      | :2::4.0::6::8.0     |

    And set expected data
      | targetInteger | targetFloat | targetLong | targetDouble |
      | 2             | 4           | 6          | 8            |

    Then save and verify mapping as "ComplexSeparateCMD.json"

