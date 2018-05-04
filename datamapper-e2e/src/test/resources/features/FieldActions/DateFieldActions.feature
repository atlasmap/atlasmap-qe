@FieldActions
@DateFieldActions
Feature: Date related field actions

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"

  Scenario: Day of week transformation
#    When set mapping from "sourceDate" to "targetInteger"
#    And add click "Add Transformation" button
#    And change transformation from "AbsoluteValue" to "DayOfWeek"
#    And set "4" value in target's "targetInteger"
#    Then save and verify mapping as "dayOfWeek.xml"
#
#    When change transformation from "DayOfWeek" to "DayOfYear"
#    And set "1" value in target's "targetInteger"
#    Then save and verify mapping as "dayOfYear.xml"

  Scenario:Add days transformation
    When set mapping from "sourceDate" to "targetDate"
    And add click "Add Transformation" button
    And for "input-days" input set "5"
    And set "1970-01-06-00:00" value in target's "targetDate"
    Then save and verify mapping as "addDays.xml"

    And change transformation from "AddDays" to "AddSeconds"
    And for "input-seconds" input set "86400"
    And set "1970-01-02-00:00" value in target's "targetDate"
    Then save and verify mapping as "addSeconds.xml"

  Scenario:Add days transformation
    When add mapping from "/localDateTime" to "/targetDate"
    And add click "Add Transformation" button
    And for "input-days" input set "5"
    And set "2012-12-26-00:00" value in target's "targetDate"
    And init DateObject "21-12-2012"
    Then save and verify mapping with multiple objects as "addDays.xml"

    And change transformation from "AddDays" to "AddSeconds"
    And for "input-seconds" input set "86400"
    And set "2012-12-22-00:00" value in target's "targetDate"
    And init DateObject "21-12-2012"
    Then save and verify mapping with multiple objects as "addSeconds.xml"

  Scenario:Add days transformation
    When add mapping from "/timestamp" to "/targetDate"
    And add click "Add Transformation" button
    And for "input-days" input set "5"
    And set "2012-12-26-00:00" value in target's "targetDate"
    And init DateObject "21-12-2012"
    Then save and verify mapping with multiple objects as "addDays.xml"

    And change transformation from "AddDays" to "AddSeconds"
    And for "input-seconds" input set "86400"
    And set "2012-12-22-00:00" value in target's "targetDate"
    And init DateObject "21-12-2012"
    Then save and verify mapping with multiple objects as "addSeconds.xml"
