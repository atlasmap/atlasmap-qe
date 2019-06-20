@FieldActions
@StringFieldActions
Feature: conversion from string

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "true"
    And set mapping from "sourceString" to "targetString"
#
  Scenario Outline: Simple string transformations:
    When add transformation on "<source/target>"
    Then verify in "<transformation>" on "<source/target>" transformation that  "<source>" is transformed to "<target>"

    Examples:
      | transformation         | source                     | target                     | source/target |
      | Capitalize             | foo                        | Foo                        | source        |
      | Capitalize             | foo                        | Foo                        | target        |
      | File Extension         | bar.jpg                    | jpg                        | source        |
      | File Extension         | bar.jpg                    | jpg                        | target        |
      | Lowercase              | BAR                        | bar                        | source        |
      | Lowercase              | BAR                        | bar                        | target        |
      | Normalize              | foo \t bar                 | foo bar                    | source        |
      | Normalize              | foo \t bar                 | foo bar                    | target        |
      | Remove File Extension  | bar.jpg                    | bar                        | source        |
      | Remove File Extension  | bar.jpg                    | bar                        | target        |
      | Uppercase              | foo                        | FOO                        | source        |
      | Uppercase              | foo                        | FOO                        | target        |
      | Separate By Dash       | this:is_foo=bar+expression | this-is-foo-bar-expression | source        |
      | Separate By Dash       | this:is_foo=bar+expression | this-is-foo-bar-expression | target        |
      | Separate By Underscore | this:is_foo=bar+expression | this_is_foo_bar_expression | source        |
      | Separate By Underscore | this:is_foo=bar+expression | this_is_foo_bar_expression | target        |


  Scenario: Trim input string
    When  verify in "Trim" on "source" transformation that  "  String for   trim  " is transformed to "String for   trim"

  Scenario: Trim left input string
    When  verify in "Trim Left" on "target" transformation that  "  String for   trim  " is transformed to "String for   trim  "
#
  Scenario: Trim right input string
    When  verify in "Trim Right" on "source" transformation that  "  String for   trim  " is transformed to "  String for   trim"

 #    GGNERATE UUID Problem --disabled
#  Scenario Outline:  Generate UUID
#    When add transformation on "<source/target>"
#    And select "GenerateUUID" transformation
#    And set "test" value in source's "sourceString"
#    And set "jpg" value in target's "targetString"
#    Then save mapping as "uuid.json"
#    And  verify if "targetString" is not "jpg" in "uuid.xml"
#    And  verify if "targetString" is not "test" in "uuid.xml"
#
#    Examples:
#      | source/target |
#      | source        |
#      | target        |

#
#  # PAD STRING LEFT
#  # PAD STRING LEFT
  @StringFieldActionsWithInputs
  Scenario Outline: with inputs
    When add "<transformation>" transformation on "<source/target>"
    And for "<input-1>" input set "<input-1-value>"
    And for "<input-2>" input set "<input-2-value>"
    Then save and verify "<transformation>.json" with
      | sourceString | targetString |
      | <source>     | <target>     |

    Examples:
      | source/target | transformation   | input-1            | input-1-value | input-2          | input-2-value | source                       | target              |
      | target        | Append           | input-string       | bar           | N/A              | N/A           | FOO                          | FOObar              |
      | target        | Pad String Left  | input-padCharacter | -             | input-padCount   | 3             | FooBar                       | ---FooBar           |
      | source        | Pad String Left  | input-padCharacter | -             | input-padCount   | 3             | FooBar                       | ---FooBar           |
      | target        | Pad String Left  | input-padCharacter | -             | input-padCount   | 0             | FooBar                       | FooBar              |
      | source        | Pad String Left  | input-padCharacter | -             | input-padCount   | 0             | FooBar                       | FooBar              |
      | target        | Pad String Right | input-padCharacter | -             | input-padCount   | 3             | FooBar                       | FooBar---           |
      | source        | Pad String Right | input-padCharacter | -             | input-padCount   | 3             | FooBar                       | FooBar---           |
      | target        | Prepend          | input-string       | bar           | N/A              | N/A           | FOO                          | barFOO              |
      | source        | Prepend          | input-string       | bar           | N/A              | N/A           | FOO                          | barFOO              |
      | source        | Prepend          | input-string       | bar           | N/A              | N/A           |                              | bar                 |
      | target        | Replace All      | input-match        | DASH          | input-newString  | -             | ThisDASHisDASHtestDASHstring | This-is-test-string |
      | source        | Replace All      | input-match        | DASH          | input-newString  | -             | ThisDASHisDASHtestDASHstring | This-is-test-string |
      | target        | Replace First    | input-match        | DASH          | input-newString  | -             | FooDASHBarDASHFoo            | Foo-BarDASHFoo      |
      | source        | Replace First    | input-match        | DASH          | input-newString  | -             | FooDASHBarDASHFoo            | Foo-BarDASHFoo      |
      | target        | Sub String       | input-endIndex     | 4             | input-startIndex | 1             | FFoobarff                    | Foo                 |
      | source        | Sub String       | input-endIndex     | 4             | input-startIndex | 1             | FFoobarff                    | Foo                 |

  Scenario: Substring after from input string
    When add "Sub String After" transformation on "target"
    And for "input-endIndex" input set "5"
    And for "input-startIndex" input set "0"
    And for "input-match" input set "middle"
    Then save and verify "SubstringAfter.json" with
      | sourceString          | targetString |
      | fooleftmiddlerightbar | right        |

  Scenario: Substring before
    And add "Sub String Before" transformation on "source"
    And for "input-endIndex" input set "6"
    And for "input-startIndex" input set "3"
    And for "input-match" input set "blah"
    Then save and verify "SubstringBefore.json" with
      | sourceString | targetString |
      | foobarblah   | bar          |


