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
  @CollectionTest
  Scenario Outline: Collection -> simple mapping with <transformation> transformation
    When add mapping from "<source>" to "<target>"
    And add "<transformation>" collection transformation
    And set "<value>" value in target's "<target>"
    And sleep for "2000"
    Then save and verify mapping as "collection_<transformation>.json"

    Examples:
      | transformation | value                                                                   | target        | source        |
      | Average        | 5                                                                       | targetDouble  | integers      |
      | Add            | 45                                                                      | targetFloat   | integers      |
      | Multiply       | 362880                                                                  | targetLong    | integers      |
      | Subtract       | -43                                                                     | targetInteger | integers      |
      | Divide         | 2.7557319223985893E-6                                                   | targetDouble  | integers      |
      | Minimum        | 1                                                                       | targetShort   | integers      |
      | Maximum        | 9                                                                       | targetShort   | integers      |
      | Concatenate    | String1 String2 String3 String4 String5 String6 String7 String8 String9 | targetString  | strings       |

  @CollectionTestNested
  Scenario Outline: Collection -> simple mapping with <transformation> transformation
    And open all subfolders
    When add mapping from "<source>" to "<target>"
    And add "<transformation>" collection transformation
    And set "<value>" value in target's "<target>"
    Then save and verify mapping as "collection_nested_<transformation>.json"

    Examples:
      | transformation | value                                                                   | target        | source       |
      | Concatenate    | 1 2 3                                                                   | targetString  | /arrayString |
      | Average        | 2                                                                       | targetDouble  | /arrayNumber |
      | Add            | 6                                                                       | targetFloat   | /arrayNumber |
      | Multiply       | 6                                                                       | targetLong    | /arrayNumber |
      | Subtract       | -4                                                                      | targetInteger | /arrayNumber |
      | Divide         | 0.16666666666666666                                                     | targetDouble  | /arrayNumber |
      | Minimum        | 1                                                                       | targetShort   | /arrayNumber |
      | Maximum        | 3                                                                       | targetShort   | /arrayNumber |


  @MultipleSelectionTest
  Scenario Outline: Multiple selection mapping with <transformation> transformation
    And click on create new mapping from source "sourceInteger"
    And set "<target>" as "target"

    And add "/sourceDouble" to combine
    And add "/sourceFloat" to combine
    And add "/sourceLong" to combine
    And add "/sourceShort" to combine
    And add "<transformation>" collection transformation
    And set "<value>" value in target's "<target>"
    #And sleep for "3000"
    Then save and verify mapping as "mupltiple_<transformation>.json"


    Examples:
      | transformation | value                | target        |
      | Average        | 3                    | targetDouble  |
      | Add            | 15                   | targetFloat   |
      | Multiply       | 120                  | targetLong    |
      | Subtract       | -13                  | targetInteger |
      | Divide         | 0.008333333333333333 | targetDouble  |
      | Minimum        | 1                    | targetShort   |
      | Maximum        | 5                    | targetShort   |

  @Concatenate
  Scenario Outline: <transformation> with input
    And open all subfolders
    When add mapping from "<source>" to "<target>"
    And add "<transformation>" collection transformation
    And set "<value>" value in target's "<target>"
    And select "<delimiter>" separator
    Then save and verify mapping as "collection_<transformation>.json"

    Examples:
      | transformation | delimiter | value | target       | source          |
      | Concatenate    | Colon [:] | 1:2:3 | targetString | /arrayString |

  @ItemAt
  Scenario: item at transformation
    When add mapping from "integers" to "targetString"
    When add "ItemAt" collection transformation
    And set "Index" for transformation to "5"
    And set "6" value in target's "targetString"
    Then save and verify mapping as "collection_itemAt.json"

##single -> array (split for example)
  @SingleFieldToCollectionTransform
  Scenario Outline: single field -> collection transformations
    When add mapping from "<source>" to "<target>"
    And add "<transformation>" collection transformation
    When select "<delimiter>" separator
    And set "<value>" value in source's "<source>"
    Then save and verify collections mappings in "<transformation>Collection.json" "<expectedValue>" value is presented in "<target>" collection

    Examples:
      | transformation | source       | target    | value     | delimiter | expectedValue   |
      | Split          | sourceString | /strings  | 1:2:3:4:5 | Colon [:] | [1, 2, 3, 4, 5] |
      | Split          | sourceString | /integers | 1:2:3:4:5 | Colon [:] | [1, 2, 3, 4, 5] |

  @SimpleCollectionTransform
  Scenario Outline: simple  between collections
    When add mapping from "<from>" to "<to>"
    And add "<transformation>" transformation on "<source/target>"
    And set "<input-field>" for transformation to "<input-value>"
    And sleep for "2000"
    Then save and verify collections mappings in "<transformation>Collection.json" "<expectedValue>" value is presented in "<to>" collection

    Examples:
      | transformation | from     | to        | input-field | input-value | expectedValue                                                                                                         | source/target |
      | Prepend        | /strings | /strings  | String      | blah        | [blahString1, blahString2, blahString3, blahString4, blahString5, blahString6, blahString7, blahString8, blahString9] | target        |
      | Append         | /floats  | /strings  | String      | ->          | [1.0->, 2.0->, 3.0->, 4.0->, 5.0->, 6.0->, 7.0->, 8.0->, 9.0->]                                                       | target        |
      | Replace First  | /strings | /integers | Match       | String      | [1, 2, 3, 4, 5, 6, 7, 8, 9]                                                                                           | source        |
      | Index Of       | /strings | /floats   | String      | t           | [1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0]                                                                         | source        |
  #TODO investigate Contains, Equals, length etc
     # | Contains       | /strings | /strings  | input-value  | 2           | [1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0]                                                                         | source        |


  @collectionTransformations
  Scenario Outline: transformations between collections
    When add mapping from "<source>" to "/<target>"
    And add "<transformation>" transformation on "<source/target>"
    And set from "<from>" to "<to>" units
    Then save and verify collections mappings in "<transformation><from><to>.json" "<expected>" value is presented in "<target>" collection

    Examples:
      | transformation        | from      | to        | source/target | source    | target   | expected                                            |
    # Area units
      | Convert Distance Unit | Yard (yd) | Foot (ft) | source        | /integers | doubles  | [3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0, 24.0, 27.0] |
      | Convert Distance Unit | Yard (yd) | Foot (ft) | source        | /floats   | integers | [3, 6, 9, 12, 15, 18, 21, 24, 27]                   |
      | Convert Distance Unit | Yard (yd) | Foot (ft) | source        | /integers | strings  | [3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0, 24.0, 27.0] |



#  Add, Average, Divide Lenght, Maximum, Minimum Multiply, Substract
