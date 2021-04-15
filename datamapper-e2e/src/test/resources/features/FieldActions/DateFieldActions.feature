@FieldActions
@DateFieldActions
Feature: Date related field actions

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"

  @SimpleDateDayValue
  Scenario Outline: <transformation> transformation
    When add mapping from "<source>" to "targetInteger"
    And add "<transformation>" transformation on "source"
    And set "<targetValue>" value in target's "targetInteger"
    And set source data
      | <source>      |
      | 2012-12-21-09:00 |

    Then save and verify mapping as "<transformation>.json"

    Examples:
      | transformation | source        | targetValue  |
      | Day Of Week    | sourceDate    | 5            |
      | Day Of Week    | localDateTime | 5            |
      | Day Of Week    | timestamp     | 5            |
      | Day Of Month   | sourceDate    | 21           |
      | Day Of Month   | localDateTime | 21           |
      | Day Of Month   | timestamp     | 21           |
      | Day Of Year    | sourceDate    | 356          |
      | Day Of Year    | localDateTime | 356          |
      | Day Of Year    | timestamp     | 356          |

  @SimpleDateTransformation
  Scenario Outline: simple date transformation: <transformation> on <source/target>
    When add mapping from "<source>" to "targetDate"
    And add "<transformation>" transformation on "<source/target>"
    And set "<input>" for transformation to "<input-value>"
    And set "<targetValue>" value in target's "targetDate"
    And init DateObject "21-12-2012"
    Then save and verify mapping as "<transformation>.json"

    Examples:
      | transformation | source         | input   | input-value | targetValue      | source/target |
      | Add Days       | sourceDate     | Days    | 5           | 1970-01-06-00:00 | source        |
      | Add Days       | /localDateTime | Days    | 5           | 2012-12-26-00:00 | source        |
      | Add Days       | /timestamp     | Days    | 5           | 2012-12-26-00:00 | source        |
      | Add Seconds    | /localDateTime | Seconds | 86400       | 2012-12-22-00:00 | source        |
      | Add Seconds    | sourceDate     | Seconds | 86400       | 1970-01-02-00:00 | source        |
      | Add Seconds    | /timestamp     | Seconds | 86400       | 2012-12-22-00:00 | source        |
