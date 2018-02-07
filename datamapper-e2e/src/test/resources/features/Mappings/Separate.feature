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

    Then  save and verify mapping as "SimpleSeparate.xml"

#    #COLON separator
#    When select "Colon" separator
#    And set "foo:bar" value in source's "sourceCombineString"
#    And set "foo" value in target's "targetString"
#    And set "bar" value in target's "targetAnotherString"
#
#    Then  save and verify mapping as "SimpleSeparateColon.xml"
#    Comma separator
#    When select "Comma" separator
#    And set "foo,bar" value in source's "sourceCombineString"
#    And set "foo" value in target's "targetString"
#    And set "bar" value in target's "targetAnotherString"
#
#    Then  save and verify mapping as "SimpleSeparateComma.xml"

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

    And set expected data
      | targetString | targetChar | targetInteger | targetFloat | targetLong | targetShort | targetDouble |
      | numbers:     | A          | 2             | 3           | 4          | 5           | 6            |

    And set source data
      | sourceCombineString |
      | numbers: A 2 3.0 4 5 6.0 |

    Then save and verify mapping as "ComplexSeparate.xml"

  Scenario: Mixed types separate, with mixed indexes executed from Mapping Details window
    When click on "sourceCombineString"
    And add select "Separate" action
    And for "input-target-" id input set "targetString"


    And add "targetChar" to separate
    And for "input-Index" input with "2" set "7"

    And add "targetInteger" to separate
    And for "input-Index" input with "8" set "6"

    And add "targetFloat" to separate
    And for "input-Index" input with "8" set "5"

    And add "targetLong" to separate
    And for "input-Index" input with "8" set "4"

    And add "targetShort" to separate
    And for "input-Index" input with "8" set "3"

    And add "targetDouble" to separate
    And for "input-Index" input with "8" set "2"
   # And sleep for "30000"


    And set expected data
      | targetString | targetChar | targetInteger | targetFloat | targetLong | targetShort | targetDouble |
      | numbers:     | A          | 2             | 3           | 4          | 5           | 6            |

    And set source data
      | sourceCombineString |
      | numbers: 6.0 5 4 3.0 2 A |

    Then save and verify mapping as "ComplexSeparateMixedIndexes.xml"

