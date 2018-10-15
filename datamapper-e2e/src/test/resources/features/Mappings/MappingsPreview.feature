@Mappings
@MappingPreviews
Feature: Atlasmap is able to create preview

  Background:
    Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened
    And Show mapping preview

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
    When for "input-source-sourceInteger" id input with "1" set "3"
    And for "input-source-sourceString" id input with "2" set "5"
    Then verify that "targetCombineString" contains "2.0-1---3"

    #Test change od input data
    When set preview data
      | sourceInteger | sourceFloat | sourceString |
      | -1000         | 200.547     | Some String  |
    And click on "targetCombineString"
    Then verify that "targetCombineString" contains "200.547--1000---Some String"


  Scenario: simple Separate with separator change
    When click on "sourceCombineString"
    And click on "targetInteger" holding cmd button
    And click on "targetLong" holding cmd button
    And click on "targetString" holding cmd button

    And set "1 2 3" for "sourceCombineString" field
    And click on "targetInteger"
    Then verify preview data
      | targetInteger | targetLong | targetString |
      | 1             | 2          | 3            |

    When for "input-target-targetInteger" id input with "1" set "3"
    And for "input-target-targetString" id input with "2" set "3"
    And set "2-1-3" for "sourceCombineString" field
    And select "Dash [-]" separator


    Then verify preview data
      | targetInteger | targetLong | targetString |
      | 1             | 2          | 3            |


  #TODO investigate DATE conversions
  #TODO different formating of number fields

  Scenario Outline: conversion fromto primitive types
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
      | sourceByte    |
      | sourceDouble  |
      | sourceLong    |
      | sourceBoolean |
      | sourceString  |

  Scenario: Examples of string transfomation
    When set mapping from "sourceString" to "targetString"
    And add click "Add Transformation" link
    Then verify preview of "Capitalize" transformation from "sourceString" with value "foo" is transformed to "Foo" in "targetString"
    Then verify preview of "Lowercase" transformation from "sourceString" with value "fOo" is transformed to "foo" in "targetString"
    Then verify preview of "SeparateByDash" transformation from "sourceString" with value "this:is_foo=bar+expression" is transformed to "this-is-foo-bar-expression" in "targetString"
    Then verify preview of "SeparateByUnderscore" transformation from "sourceString" with value "this_is_foo_bar_expression" is transformed to "this_is_foo_bar_expression" in "targetString"
