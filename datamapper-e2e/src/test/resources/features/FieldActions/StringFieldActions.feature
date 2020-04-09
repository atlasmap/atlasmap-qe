@FieldActions
@StringFieldActions
Feature: conversion from string

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "true"
    And add mapping from "sourceString" to "targetString"
#
  @SimpleStringTransformations
  Scenario Outline: Simple string transformations: <transformation> on <source/target>
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
      | Length                 | foobar                     | 6                          | source        |
      | Is Null                | foobar                     | false                      | source        |

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

  @StringFieldActionsWithInputs
  Scenario Outline: transformations with inputs: <transformation> on <source/target>
    When add "<transformation>" transformation on "<source/target>"
    And set "<input-1>" for transformation to "<input-1-value>"
    And set "<input-2>" for transformation to "<input-2-value>"
    Then save and verify "<transformation>.json" with
      | sourceString | targetString |
      | <source>     | <target>     |

    Examples:
      | source/target | transformation   | input-1       | input-1-value | input-2     | input-2-value | source                       | target              |
      | target        | Append           | String        | bar           | N/A         | N/A           | FOO                          | FOObar              |
      | source        | Append           | String        | bar           | N/A         | N/A           | FOO                          | FOObar              |
      | target        | Pad String Left  | Pad Character | -             | Pad Count   | 3             | FooBar                       | ---FooBar           |
      | source        | Pad String Left  | Pad Character | -             | Pad Count   | 3             | FooBar                       | ---FooBar           |
      | target        | Pad String Left  | Pad Character | -             | Pad Count   | 0             | FooBar                       | FooBar              |
      | source        | Pad String Left  | Pad Character | -             | Pad Count   | 0             | FooBar                       | FooBar              |
      | target        | Pad String Right | Pad Character | -             | Pad Count   | 3             | FooBar                       | FooBar---           |
      | source        | Pad String Right | Pad Character | -             | Pad Count   | 3             | FooBar                       | FooBar---           |
      | target        | Prepend          | String        | bar           | N/A         | N/A           | FOO                          | barFOO              |
      | source        | Prepend          | String        | bar           | N/A         | N/A           | FOO                          | barFOO              |
      | source        | Prepend          | String        | bar           | N/A         | N/A           |                              | bar                 |
      | target        | Replace All      | Match         | DASH          | New String  | -             | ThisDASHisDASHtestDASHstring | This-is-test-string |
      | source        | Replace All      | Match         | DASH          | New String  | -             | ThisDASHisDASHtestDASHstring | This-is-test-string |
      | target        | Replace First    | Match         | DASH          | New String  | -             | FooDASHBarDASHFoo            | Foo-BarDASHFoo      |
      | source        | Replace First    | Match         | DASH          | New String  | -             | FooDASHBarDASHFoo            | Foo-BarDASHFoo      |
      | target        | Sub String       | End Index     | 4             | Start Index | 1             | FFoobarff                    | Foo                 |
      | source        | Sub String       | End Index     | 4             | Start Index | 1             | FFoobarff                    | Foo                 |
      | source        | Equals           | Value         | foo           | N/A         | N/A           | foo                          | true                |
      | source        | Equals           | Value         | bar           | N/A         | N/A           | foo                          | false               |
      | source        | Index Of         | String        | foo           | N/A         | N/A           | barfoobarfoo                 | 3                   |
      | source        | Index Of         | String        | foo           | N/A         | N/A           | bar                          | -1                  |
      | source        | Last Index Of    | String        | foo           | N/A         | N/A           | barfoobarfoo                 | 9                   |
      | source        | Last Index Of    | String        | foo           | N/A         | N/A           | barbarbar                    | -1                  |
      | source        | Starts With      | String        | foo           | N/A         | N/A           | foobar                       | true                |
      | source        | Starts With      | String        | bar           | N/A         | N/A           | foobar                       | false               |
      | source        | Ends With        | String        | foo           | N/A         | N/A           | foobar                       | false               |
      | source        | Ends With        | String        | bar           | N/A         | N/A           | foobar                       | true               |

  @SubstringAfter
  Scenario: Substring after from input string
    When add "Sub String After" transformation on "target"
    And set "End Index" for transformation to "5"
    And set "Start Index" for transformation to "0"
    And set "Match" for transformation to "middle"
    Then save and verify "SubstringAfter.json" with
      | sourceString          | targetString |
      | fooleftmiddlerightbar | right        |

  @SubstringBefore
  Scenario: Substring before
    And add "Sub String Before" transformation on "source"
    And set "End Index" for transformation to "6"
    And set "Start Index" for transformation to "3"
    And set "Match" for transformation to "blah"
    Then save and verify "SubstringBefore.json" with
      | sourceString | targetString |
      | foobarblah   | bar          |


