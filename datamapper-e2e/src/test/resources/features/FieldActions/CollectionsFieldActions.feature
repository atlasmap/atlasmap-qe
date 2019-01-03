@FieldActions
@CollectionFieldActions

Feature: collection related field actions

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
    And init SourceListClass and add in sourceMap


#    Done Collection -> single
  Scenario Outline: <transformation>
    When set mapping from "<source>" to "<target>"
    And add "<transformation>" transformation on "source"
    And set "<value>" value in target's "<target>"
    Then save and verify mapping as "collection_<transformation>.xml"

    Examples:
      | transformation | value                                                           | target        | source   |
      | Average        | 5                                                               | targetDouble  | integers |
      | Add            | 45                                                              | targetFloat   | integers |
      | Multiply       | 362880                                                          | targetLong    | integers |
      | Subtract       | -43                                                             | targetInteger | integers |
      | Divide         | 2.7557319223985893E-6                                           | targetDouble  | integers |
      | Minimum        | 1                                                               | targetShort   | integers |
      | Maximum        | 9                                                               | targetShort   | integers |
      | Concatenate    | String1String2String3String4String5String6String7String8String9 | targetString  | strings  |
#
  Scenario: item at transformation
    When set mapping from "integers" to "targetString"
    When add "ItemAt" transformation on "source"
    And for "input-index" input set "5"
    And set "6" value in target's "targetString"
    Then save and verify mapping as "collection_itemAt.xml"

##single -> array (split for example)
  Scenario Outline: single field -> collection transformations
    When add mapping from "<source>" to "<target>"
    And add "<transformation>" transformation on "source"
    And for "<input>" input set "<input-value>"
    And set "<value>" value in source's "<source>"
    Then save and verify collections mappings in "<transformation>Collection.xml" "<expectedValue>" value is presented in "<target>" collection

    Examples:
      | transformation | source       | target    | value     | input           | input-value | expectedValue   |
      | Split          | sourceString | /strings  | 1,2,3,4,5 | input-delimiter | ,           | [1, 2, 3, 4, 5] |
      | Split          | sourceString | /integers | 1,2,3,4,5 | input-delimiter | ,           | [1, 2, 3, 4, 5] |


  Scenario Outline: simple  between collections
    When add mapping from "<from>" to "<to>"
    And add "<transformation>" transformation on "<source/target>"
    And for "<input>" input set "<input-value>"
    And sleep for "2000"
    Then save and verify collections mappings in "<transformation>Collection.xml" "<expectedValue>" value is presented in "<to>" collection

    Examples:
      | transformation | from     | to        | input        | input-value | expectedValue                                                                     | source/target |
      #| Prepend        | /strings | /strings  | input-string | blah        | [blah1.0, blah2.0, blah3.0, blah4.0, blah5.0, blah6.0, blah7.0, blah8.0, blah9.0] | target          |
      #| Append         | /floats  | /strings  | input-string | ->          | [1.0->, 2.0->, 3.0->, 4.0->, 5.0->, 6.0->, 7.0->, 8.0->, 9.0->]                   | target          |
      | ReplaceFirst   | /strings | /integers | input-match  | String      | [1, 2, 3, 4, 5, 6, 7, 8, 9]                                                       | source          |
      | IndexOf        | /strings | /floats   | input-string | t           | [1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0]                                     | source          |
  #TODO investigate Contains, Equals, length etc
#      | Contains         | /strings| /strings | input-value | 2           | [1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0] |

  Scenario Outline: transformations between collections
    When add mapping from "<source>" to "/<target>"
    When add "<transformation>" transformation on "<source/target>"
    And set from "<from>" to "<to>" units on "<source/target>"
    Then save and verify collections mappings in "<transformation><from><to>.xml" "<expected>" value is presented in "<target>" collection

    Examples:
      | transformation      | from      | to        | source/target | source    | target   | expected                                            |
    # Area units
      | ConvertDistanceUnit | Yard (yd) | Foot (ft) | source        | /integers | doubles  | [3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0, 24.0, 27.0] |
      | ConvertDistanceUnit | Yard (yd) | Foot (ft) | source        | /floats   | integers | [3, 6, 9, 12, 15, 18, 21, 24, 27]                   |
      | ConvertDistanceUnit | Yard (yd) | Foot (ft) | source        | /integers | strings  | [3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0, 24.0, 27.0] |

#

#Add, Average, Divide Lenght, Maximum, Minimum Multiply, Substract
