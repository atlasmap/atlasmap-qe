@FieldActions
@StringFieldActions
Feature: conversion from string
  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "true"
    And set mapping from "sourceString" to "targetString"
    And add click "Add Transformation" button

  Scenario: Capitalize input string
    When select "Capitalize" transformation
    And set source data
      | sourceString |
      | foo          |
    And set expected data
      | targetString |
      | Foo          |
    Then save mapping as "capitalizeString.xml"
    And  verify "capitalizeString.xml"


  Scenario: Remove file extension from input string
    When select "RemoveFileExtension" transformation
    And set source data
      | sourceString |
      | bar.jpg      |
    And set expected data
      | targetString |
      | bar          |
    Then save mapping as "removeFileExtension.xml"
    And  verify "removeFileExtension.xml"

    #needs to create
  Scenario: Concatenate input string
    When select "Concatenate" transformation
    And set source data
      | sourceString |
      | foo          |
    And set expected data
      | targetString |
      | foo          |
    Then save mapping as "Concatenate.xml"
    And  verify "Concatenate.xml"


  Scenario: Uppercase from input string
    When select "Uppercase" transformation
    And set source data
      | sourceString |
      | foo          |
    And set expected data
      | targetString |
      | FOO          |
    Then save mapping as "Uppercase.xml"
    And  verify "Uppercase.xml"

  Scenario: Lowercase from input string
    When select "Lowercase" transformation
    And set source data
      | sourceString |
      | BAR          |
    And set expected data
      | targetString |
      | bar          |
    Then save mapping as "Lowercase.xml"
    And  verify "Lowercase.xml"

  Scenario:  file extension from input string
    When select "FileExtension" transformation
    And set source data
      | sourceString |
      | bar.jpg      |
    And set expected data
      | targetString |
      | jpg          |
    Then save mapping as "fileExtension.xml"
    And  verify "fileExtension.xml"

  Scenario:  Generate UUID
    When select "GenerateUUID" transformation
    And set source data
      | sourceString |
      | test         |
    And set expected data
      | targetString |
      | jpg          |
    Then save mapping as "uuid.xml"
    And  verify if "targetString" is not "jpg" in "uuid.xml"
