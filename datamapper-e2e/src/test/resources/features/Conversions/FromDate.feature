@Conversions
@FromDate
Feature: Cover all dates conversion

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And internal mapping is set to "false"
    And browser is opened

  Scenario: Conversion from standardJavaDate (DATE_TIME) to al types
   # When click on "dateObjectVariable"
    And add mapping from "/standardJavaDate" to "/standardJavaDate"
    And add mapping from "/standardJavaDate" to "/dateObjectVariable/calendar"
    And add mapping from "/standardJavaDate" to "/dateObjectVariable/gregorianCalendar"
    And add mapping from "/standardJavaDate" to "/dateObjectVariable/sqlDate"
    And add mapping from "/standardJavaDate" to "/dateObjectVariable/localDate"
    And add mapping from "/standardJavaDate" to "/dateObjectVariable/localDateTime"
    And add mapping from "/standardJavaDate" to "/dateObjectVariable/localTime"
    And add mapping from "/standardJavaDate" to "/dateObjectVariable/time"
    And add mapping from "/standardJavaDate" to "/dateObjectVariable/timestamp"
    And add mapping from "/standardJavaDate" to "/dateObjectVariable/zonedDateTime"


    And set mapping to "targetString" from "sourceDate"
    And set mapping to "targetLong" from "sourceDate"
    And set mapping to "targetInteger" from "sourceDate"
    And set mapping to "targetDouble" from "sourceDate"
    And set mapping to "targetFloat" from "sourceDate"
    And set mapping to "targetByte" from "sourceDate"
    And set mapping to "targetShort" from "sourceDate"


    And set expected data
      | targetInteger | targetString         | targetLong | targetDouble | targetFloat | targetByte | targetShort |
      | 0             | 1970-01-01T00:00:00Z | 0          | 0            | 0           | 0          | 0           |

    And take a screenshot
    And init DateObject "22-12-2012"
    Then save and verify mapping from "standardJavaDate" to datetypes as "dates_fromxJavaDate.xml"

#
  Scenario: Conversion from LocalDateTime (DATE_TIME) to al types
 #   When click on "dateObjectVariable"
    And add mapping from "/localDateTime" to "/standardJavaDate"
    And add mapping from "/localDateTime" to "/dateObjectVariable/calendar"
    And add mapping from "/localDateTime" to "/dateObjectVariable/gregorianCalendar"
    And add mapping from "/localDateTime" to "/dateObjectVariable/sqlDate"
    And add mapping from "/localDateTime" to "/dateObjectVariable/localDate"
    And add mapping from "/localDateTime" to "/dateObjectVariable/localDateTime"
    And add mapping from "/localDateTime" to "/dateObjectVariable/localTime"
    And add mapping from "/localDateTime" to "/dateObjectVariable/time"
    And add mapping from "/localDateTime" to "/dateObjectVariable/timestamp"
    And add mapping from "/localDateTime" to "/dateObjectVariable/zonedDateTime"


    And init DateObject "22-12-2012"
    Then save and verify mapping from "localDateTIme" to datetypes as "dates_fromLocalDateTime.xml"

  Scenario: Conversion from zonedDT  (DATE) to al types
  #  When click on "dateObjectVariable"
    And add mapping from "/zonedDateTime" to "/standardJavaDate"
    And add mapping from "/zonedDateTime" to "/dateObjectVariable/sqlDate"
    And add mapping from "/zonedDateTime" to "/dateObjectVariable/localDate"
    And add mapping from "/zonedDateTime" to "/dateObjectVariable/localDateTime"
    And add mapping from "/zonedDateTime" to "/dateObjectVariable/localTime"
    And add mapping from "/zonedDateTime" to "/dateObjectVariable/time"
    And add mapping from "/zonedDateTime" to "/dateObjectVariable/timestamp"
    And add mapping from "/zonedDateTime" to "/dateObjectVariable/zonedDateTime"
    And add mapping from "/zonedDateTime" to "/dateObjectVariable/calendar"
    And add mapping from "/zonedDateTime" to "/dateObjectVariable/gregorianCalendar"

    And init DateObject "22-12-2012"
    Then save and verify mapping from "localDateTIme" to datetypes as "dates_fromLocalDateTime.xml"

  Scenario: Conversion from timestamp (DATE_TIME) to al types
   # When click on "dateObjectVariable"
    And add mapping from "/timestamp" to "/standardJavaDate"
    And add mapping from "/timestamp" to "/dateObjectVariable/sqlDate"
    And add mapping from "/timestamp" to "/dateObjectVariable/localDate"
    And add mapping from "/timestamp" to "/dateObjectVariable/localDateTime"
    And add mapping from "/timestamp" to "/dateObjectVariable/localTime"
    And add mapping from "/timestamp" to "/dateObjectVariable/time"
    And add mapping from "/timestamp" to "/dateObjectVariable/timestamp"
    And add mapping from "/timestamp" to "/dateObjectVariable/zonedDateTime"
    And add mapping from "/timestamp" to "/dateObjectVariable/calendar"
    And add mapping from "/timestamp" to "/dateObjectVariable/gregorianCalendar"

    And init DateObject "22-12-2012"
    Then save and verify mapping from "standardJavaDate" to datetypes as "dates_fromxJavaDate.xml"

  Scenario: Conversion from calendar (DATE) to al types
    When click on "dateObjectVariable"
    And add mapping from "/calendar" to "/standardJavaDate"
    And add mapping from "/calendar" to "/dateObjectVariable/sqlDate"
    And add mapping from "/calendar" to "/dateObjectVariable/localDate"
    And add mapping from "/calendar" to "/dateObjectVariable/localDateTime"
    And add mapping from "/calendar" to "/dateObjectVariable/localTime"
    And add mapping from "/calendar" to "/dateObjectVariable/time"
    And add mapping from "/calendar" to "/dateObjectVariable/timestamp"
    And add mapping from "/calendar" to "/dateObjectVariable/zonedDateTime"
    And add mapping from "/calendar" to "/dateObjectVariable/calendar"
    And add mapping from "/calendar" to "/dateObjectVariable/gregorianCalendar"

    And init DateObject "22-12-2012"
    Then save and verify mapping from "standardJavaDate" to datetypes as "dates_fromxJavaDate.xml"

