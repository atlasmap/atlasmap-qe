@FieldActions
@DateFieldActions
Feature: Date related field actions

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
#  CURRENTLY NOT USED IN ATLASMAP
#  Scenario: Day of week transformation
#    When set mapping from "sourceDate" to "targetInteger"
#    And add click "Add Transformation" link
#    And change transformation from "AbsoluteValue" to "DayOfWeek"
#    And set "4" value in target's "targetInteger"
#    Then save and verify mapping as "dayOfWeek.json"
#
#    When change transformation from "DayOfWeek" to "DayOfYear"
#    And set "1" value in target's "targetInteger"
#    Then save and verify mapping as "dayOfYear.json"

  Scenario Outline: simple date transformation: <transformation> on <source/target>
    When add mapping from "<source>" to "targetDate"
    And add "<transformation>" transformation on "<source/target>"
    And set "<input>" for transformation to "<input-value>"
    And set "<targetValue>" value in target's "targetDate"
    And init DateObject "21-12-2012"
    Then save and verify mapping as "<transformation>.json"

    #Add seconds not present currently
    Examples:
      | transformation | source          | input | input-value | targetValue      | source/target |
      | Add Days        | sourceDate     | Days  | 5           | 1970-01-06-00:00 | source        |
      | Add Days        | /localDateTime | Days  | 5           | 2012-12-26-00:00 | source        |
      | Add Days        | /timestamp     | Days  | 5           | 2012-12-26-00:00 | source        |
  #   | Add Seconds     | /localDateTime | input-seconds | 86400      | 2012-12-22-00:00 | target        |
  #   | Add Seconds     | sourceDate     | input-seconds | 86400      | 1970-01-02-00:00 | target        |
  #   | Add Seconds     | /timestamp     | input-seconds | 86400      | 2012-12-22-00:00 | target        |
