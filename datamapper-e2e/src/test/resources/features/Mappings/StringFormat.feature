@StringFormat
@Mappings
@Combine
Feature: atlasmap is able to combine multiple inputs into one formatted string

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"

  Scenario Outline: String formatting of <source1> and <source2>

    And click on create new mapping from target "targetCombineString"
    And add source "<source1>" to active mapping
    And add source "<source2>" to active mapping

    And add "Format" collection transformation
    And set "Template" for transformation to "<template>"

    And set source data
      | <source1>      | <source2>      |
      | <source1Value> | <source2Value> |

    And set expected data
      | targetCombineString |
      | <expectedValue>     |

    Then  save and verify mapping as "StringFormatting<N>.json"

    Examples:
      | N | source1      | source1Value  | source2             | source2Value | template   | expectedValue |
      | 0 | sourceString | foo           | sourceAnotherString | bar          | N/A        | N/A           |
      | 1 | sourceString | foo           | sourceAnotherString | bar          | %s         | foo           |
      | 2 | sourceString | foo           | sourceAnotherString | bar          | %s %s      | foo bar       |
      | 3 | sourceString | foo           | sourceAnotherString | bar          | %2$s %1$s  | bar foo       |
      | 4 | sourceString | Hello world   | sourceAnotherString | George       | %.5s %s!   | Hello George! |
      | 5 | sourceString | foo           | sourceAnotherString | bar          | !%-5s!%5s! | !foo  !  bar! |
      | 6 | sourceString | foo           | sourceInteger       | -42          | %b%(d      | true(42)      |
      | 7 | sourceByte   | 19            | sourceFloat         | 3.1415926535 | %o %05.2f  | 23 03.14      |
      | 8 | sourceDate   | 1997-03-15-06 | sourceChar          | c            | %ta %c     | Sat c         |
      | 9 | sourceDate   | 1997-03-15-03 | sourceInteger       | 61           | %tB %x     | March 3d      |

  @SmokeTest
  Scenario: String formatting of three fields
    And click on create new mapping from target "targetCombineString"
    When add source "sourceString" to active mapping
    When add source "sourceAnotherString" to active mapping
    When add source "sourceInteger" to active mapping

    And add "Format" collection transformation
    And set "Template" for transformation to "%s %s %d"

    And set source data
      | sourceString   | sourceAnotherString | sourceInteger |
      | foo            | bar                 | 1             |

    And set expected data
      | targetCombineString |
      | foo bar 1           |

    Then  save and verify mapping as "StringFormattingOfThreeFields.json"
