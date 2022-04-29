@CSV
@CsvFormatting
@Mappings
Feature: importing CSV files with different formats

  Background: Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

  Scenario: Test CSV with duplicate header name
    And import CSV file "csv/sourceCsvDuplicateHeaderNames.csv" formatted as "MongoDBCsv" with parameters
      | First Record As Header       | true |
      | Allow Duplicate Header Names | true |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvDuplicateHeaderNamesDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvDuplicateHeaderNames.json"
    And remove "source" document called "sourceCsvDuplicateHeaderNames"

  Scenario: Test CSV with missing column names
    And import CSV file "csv/sourceCsvMissingColumnNames.csv" formatted as "Default" with parameters
      | First Record As Header     | true |
      | Allow Missing Column Names | true |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvMissingColumnNamesDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvMissingColumnNames.json"
    And remove "source" document called "sourceCsvMissingColumnNames"

  Scenario: Test CSV with custom comment marker
    And import CSV file "csv/sourceCsvCommentMarker.csv" formatted as "Default" with parameters
      | First Record As Header | true |
      | Comment Marker         | #    |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvCommentMarkerDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvCommentMarker.json"
    And remove "source" document called "sourceCsvCommentMarker"

  Scenario: Test CSV with custom delimiter
    And import CSV file "csv/sourceCsvCustomDelimiter.csv" formatted as "Default" with parameters
      | First Record As Header | true |
      | Delimiter              | +    |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvCustomDelimiterDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvCustomDelimiter.json"
    And remove "source" document called "sourceCsvCustomDelimiter"

  Scenario: Test CSV with custom escape character
    And import CSV file "csv/sourceCsvCustomEscapeCharacter.csv" formatted as "Default" with parameters
      | First Record As Header | true |
      | Delimiter              | 4    |
      | Escape                 | _    |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfStrings"
    And add "/<>/sourceCsvCustomEscapeCharacterString" as "source"
    Then save and verify that "listOfStrings" contains "csvStrings" as "csvCustomEscapeCharacter.json"
    And remove "source" document called "sourceCsvCustomEscapeCharacter"

  Scenario: Test CSV with manually added header names and skip header record
    And import CSV file "csv/sourceCsvHeaders.csv" formatted as "Default" with parameters
      | Headers            | sourceCsvHeadersString,sourceCsvHeadersNumber,sourceCsvHeadersDecimal,sourceCsvHeadersDate,sourceCsvHeadersBoolean |
      | Skip Header Record | true                                                                                                               |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvHeadersDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvHeaders.json"
    And remove "source" document called "sourceCsvHeaders"

  Scenario: Test CSV while ignoring empty lines
    And import CSV file "csv/sourceCsvIgnoreEmptyLines.csv" formatted as "Excel" with parameters
      | First Record As Header | true |
      | Ignore Empty Lines     | true |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvIgnoreEmptyLinesDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvIgnoreEmptyLines.json"
    And remove "source" document called "sourceCsvIgnoreEmptyLines"

  Scenario: Test CSV while ignoring header case
    And import CSV file "csv/sourceCsvIgnoreHeaderCase.csv" formatted as "Default" with parameters
      | First Record As Header | true |
      | Ignore Header Case     | true |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvIgnoreHeaderCaseDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvCustomDelimiter.json"
    And remove "source" document called "sourceCsvIgnoreHeaderCase"

  Scenario: Test CSV while ignoring surrounding spaces
    And import CSV file "csv/sourceCsvIgnoreSurroundingSpaces.csv" formatted as "Default" with parameters
      | First Record As Header    | true |
      | Ignore Surrounding Spaces | true |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvIgnoreSurroundingSpacesDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvIgnoreSurroundingSpaces.json"
    And remove "source" document called "sourceCsvIgnoreSurroundingSpaces"

  Scenario: Test CSV with custom quote character
    And import CSV file "csv/sourceCsvNullString.csv" formatted as "Default" with parameters
      | First Record As Header | true |
      | Null String            | TEST |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfStrings"
    And add "/<>/sourceCsvNullStringString" as "source"
    Then save and verify that "listOfStrings" contains "csvStringsWithNull" as "sourceCsvNullString.json"
    And remove "source" document called "sourceCsvNullString"

  Scenario: Test CSV with non-default format (TDF)
    And import CSV file "csv/sourceCsvTdfFormat.csv" formatted as "TDF" with parameters
      | First Record As Header | true |
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfStrings"
    And add "/<>/sourceCsvTdfFormatString" as "source"
    Then save and verify that "listOfStrings" contains "csvStrings" as "csvTdfFormatStrings.json"
    And click on create new mapping from target "/targetSmallMappingTestClass/listOfDoubles"
    And add "/<>/sourceCsvTdfFormatDecimal" as "source"
    Then save and verify that "listOfDoubles" contains "csvDoubles" as "csvTdfFormatDoubles.json"
    And remove "source" document called "sourceCsvTdfFormat"
