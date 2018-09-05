@FieldActions
@StringFieldActions
Feature: conversion from string

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "true"
    And set mapping from "sourceString" to "targetString"
    And add transformation on target


   #  APPEND
  Scenario: Append to input string
    When select "Append" transformation
    And for "input-string" input set "bar"
    And set "FOO" value in source's "sourceString"
    And set "FOObar" value in target's "targetString"
    And save and verify mapping as "Append.xml"

    # CAPITALIZE
  Scenario: Capitalize input string
    Given verify in "Capitalize" transformation that  "foo" is transformed to "Foo"

#    # CONCATENATE
#  Scenario: Concatenate to inputString
#    When verify in "Concatenate" transformation that  "foo" is transformed to "foo"

    # FILE EXTENSION
  Scenario:  Verify simple string actions
    Given verify in "FileExtension" transformation that  "bar.jpg" is transformed to "jpg"

    # GGNERATE UUID
  Scenario:  Generate UUID
    When select "GenerateUUID" transformation
    And set "test" value in source's "sourceString"
    And set "jpg" value in target's "targetString"
    Then save mapping as "uuid.xml"
    And  verify if "targetString" is not "jpg" in "uuid.xml"
    And  verify if "targetString" is not "test" in "uuid.xml"

    # LOWERCASE
  Scenario: Lowercase from input string
    When  verify in "Lowercase" transformation that  "BAR" is transformed to "bar"

    # NORMALIZE
  Scenario: Normalize inputString
    When verify in "Normalize" transformation that  "foo \t bar" is transformed to "foo bar"

  # PAD STRING LEFT
  Scenario: Pad string left
    When select "PadStringLeft" transformation
    And for "input-padCharacter" input set "-"
    And for "input-padCount" input set "3"
    Then save and verify "PadStringLeft.xml" with
      | sourceString | targetString |
      | FooBar       | ---FooBar    |
    When for "input-padCount" input set "0"
    Then save and verify "PadStringLeft.xml" with
      | sourceString | targetString |
      | FooBar       | FooBar    |

    # PAD STRING RIGHT
  Scenario: Pad string right
    When select "PadStringRight" transformation
    And for "input-padCharacter" input set "-"
    And for "input-padCount" input set "3"
    Then save and verify "PadStringRight.xml" with
      | sourceString | targetString |
      | FooBar       | FooBar---    |
#
    # PREPEND
  Scenario: Prepend to input string
    When select "Prepend" transformation
    And for "input-string" input set "bar"
    Then save and verify "Prepend.xml" with
      | sourceString | targetString |
      | FOO          | barFOO       |
      |              | bar          |

    # REMOVE FILE EXTENSION
  Scenario: Remove file extension from input string
    When  verify in "RemoveFileExtension" transformation that  "bar.jpg" is transformed to "bar"

    # REPLACE ALL
  Scenario: Replacing all in input string
    When select "ReplaceAll" transformation
    And for "input-match" input set "DASH"
    And for "input-newString" input set " "
    And set "ThisDASHisDASHtestDASHstring" value in source's "sourceString"
    And set "This is test string" value in target's "targetString"
    And save and verify mapping as "ReplaceAll.xml"

    # REPLACE FIRST
  Scenario: Replacing first in input string
    When select "ReplaceFirst" transformation
    And for "input-match" input set "DASH"
    And for "input-newString" input set " "
    Then save and verify "ReplaceFirst.xml" with
      | sourceString      | targetString   |
      | FooDASHBarDASHFoo | Foo BarDASHFoo |

    # SEPARATE BY DASH
  Scenario: Separate by dash from input string
    And verify in "SeparateByDash" transformation that  "this:is_foo=bar+expression" is transformed to "this-is-foo-bar-expression"

    # SEPARATE BY UNDERSCORE
  Scenario: Separate by dash from input string
    And verify in "SeparateByUnderscore" transformation that  "this:is_foo=bar+expression" is transformed to "this_is_foo_bar_expression"

    # SUBSTRING
  Scenario: Substring from input string
    When select "SubString" transformation
    And for "input-endIndex" input set "4"
    And for "input-startIndex" input set "1"
    Then save and verify "Substring.xml" with
      | sourceString | targetString |
      | FFoobarff    | Foo          |

    # SUBSTRING AFTER
  Scenario: Substring after from input string
    When select "SubStringAfter" transformation
    And for "input-endIndex" input set "5"
    And for "input-startIndex" input set "0"
    And for "input-match" input set "middle"
    Then save and verify "SubstringAfter.xml" with
      | sourceString          | targetString |
      | fooleftmiddlerightbar | right        |

    # SUBSTRING BEFORE
  Scenario: Substring before
    When select "SubStringBefore" transformation
    And for "input-endIndex" input set "6"
    And for "input-startIndex" input set "3"
    And for "input-match" input set "blah"
    Then save and verify "SubstringBefore.xml" with
      | sourceString | targetString |
      | foobarblah   | bar          |

    # TRIM
  Scenario: Trim input string
    When  verify in "Trim" transformation that  "  String for   trim  " is transformed to "String for   trim"

    # TRIM LEFT
  Scenario: Trim left input string
    When  verify in "TrimLeft" transformation that  "  String for   trim  " is transformed to "String for   trim  "

    # TRIM RIGHT
  Scenario: Trim right input string
    When  verify in "TrimRight" transformation that  "  String for   trim  " is transformed to "  String for   trim"

    # UPPERCASE
  Scenario: Uppercase from input string
    When verify in "Uppercase" transformation that  "foo" is transformed to "FOO"





