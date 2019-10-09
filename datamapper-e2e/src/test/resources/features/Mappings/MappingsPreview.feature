@Mappings
@MappingPreviews
Feature: Atlasmap is able to create preview

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened
    And Show mapping preview
#
  @CombineGaps
  Scenario: simple Combine with index change
    When click on "sourceInteger" holding cmd button
    And click on "sourceFloat" holding cmd button
    And click on "sourceString" holding cmd button
    And click on "targetCombineString"

    And set preview data
      | sourceInteger | sourceFloat | sourceString |
      | 1             | 2           | 3            |
    And click on "targetCombineString"
    Then verify that "targetCombineString" contains "1 2.0 3"

    #Test separator change
    When select "Dash [-]" separator
    Then verify that "targetCombineString" contains "1-2.0-3"

    #Test change of indexes
    When change index of "sourceInteger" to "3" on "source"
    And change index of "sourceString" to "5" on "source"
    Then verify that "targetCombineString" contains "2.0-1---3"

    #Test change od input data
    When set preview data
      | sourceInteger | sourceFloat | sourceString |
      | -1000         | 200.547     | Some String  |
    And click on "targetCombineString"
    And sleep for "30000"
    Then verify that "targetCombineString" contains "200.547--1000---Some String"

@PreviewSeparate
  Scenario: simple Separate with separator change
    When click on "sourceCombineString"
    And click on "targetInteger" holding cmd button
    And click on "targetLong" holding cmd button
    And click on "targetString" holding cmd button

    And set "1 2 3" for "sourceCombineString" field
    And click on "targetString"
    Then verify preview data
      | targetInteger | targetLong | targetString |
      | 1             | 2          | 3            |

    When change index of "targetInteger" to "3" on "target"
    And change index of "targetString" to "3" on "target"
    And set "2-1-3" for "sourceCombineString" field
    And select "Dash [-]" separator


    Then verify preview data
      | targetInteger | targetLong | targetString |
      | 1             | 2          | 3            |

#
#  #TODO investigate DATE conversions
#  #TODO different formating of number fields
#
  Scenario Outline: conversion from <source> to primitive types
    When set "9" value in source's "sourceString"
    And set mapping to "targetInteger" from "<source>"
    And set mapping to "targetString" from "<source>"
  #  When set mapping to "targetDate" from "<source>"
    And set mapping to "targetLong" from "<source>"
    And set mapping to "targetDouble" from "<source>"
    And set mapping to "targetFloat" from "<source>"
    And  set mapping to "targetBoolean" from "<source>"
    And set mapping to "targetShort" from "<source>"
    And set mapping to "targetChar" from "<source>"
    And set mapping to "targetByte" from "<source>"
    Then verify conversion from "<source>" in preview

    Examples:
      | source        |
      | sourceFloat   |
      | sourceInteger |
      | sourceShort   |
      | sourceChar    |
   #   | sourceByte    |
      | sourceDouble  |
      | sourceLong    |
      | sourceBoolean |
      | sourceString  |


    @debug
  Scenario: String transfomation from preview
    When set mapping from "sourceString" to "targetString"
  #  And add click "Add Transformation" link
    Then verify preview of "Capitalize" transformation from "sourceString" with value "foo" is transformed to "Foo" in "targetString"
    Then verify preview of "Lowercase" transformation from "sourceString" with value "fOo" is transformed to "foo" in "targetString"
    Then verify preview of "Separate By Dash" transformation from "sourceString" with value "this:is_foo=bar+expression" is transformed to "this-is-foo-bar-expression" in "targetString"
    Then verify preview of "Separate By Underscore" transformation from "sourceString" with value "this:is_foo=bar+expression" is transformed to "this_is_foo_bar_expression" in "targetString"
