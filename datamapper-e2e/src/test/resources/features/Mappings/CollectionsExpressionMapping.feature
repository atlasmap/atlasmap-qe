@Mappings
@Expression

# Coverage for:
# https://issues.redhat.com/browse/ENTESB-12394
# reproducer for: https://issues.redhat.com/browse/ENTESB-12698

Feature: collections expressions mappings

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
    And open all subfolders


  @CollectionExpression
  Scenario Outline: collections expression mapping by selecting sources <expression>
    When click on create new mapping from target "<target>"
    And set mapping condition to "<expression>" by selecting sources
#    And click on "<target>"

    And set expected data
      | <target>      |
      | <targetValue> |

    And sleep for "500"
    And save mapping as "collection_expression.json"
    And verify "collection_expression.json"

    Examples:
      | expression                                                       | target        | targetValue                                                                                     |
      | Average(@{/integers<>})                                          | targetDouble  | 5                                                                                               |
      | Add(@{/integers<>})                                              | targetFloat   | 45                                                                                              |
      | Multiply(@{/integers<>})                                         | targetLong    | 362880                                                                                          |
      | Subtract(@{/integers<>})                                         | targetLong    | -43                                                                                             |
      | Divide(@{/integers<>})                                           | targetDouble  | 2.7557319223985893E-6                                                                           |
      | Minimum(@{/integers<>})                                          | targetShort   | 1                                                                                               |
      | Maximum(@{/integers<>})                                          | targetShort   | 9                                                                                               |
      | Concatenate(',',@{/strings<>})                                   | targetString  | String1,String2,String3,String4,String5,String6,String7,String8,String9                         |
      | Concatenate(',',@{/<>/arrayString})                              | targetString  | 1,2,3                                                                                           |
      | Average(@{/<>/arrayNumber})                                      | targetDouble  | 2                                                                                               |
      | Add(@{/<>/arrayNumber})                                          | targetFloat   | 6                                                                                               |
      | Multiply(@{/<>/arrayNumber})                                     | targetLong    | 6                                                                                               |
      | Subtract(@{/<>/arrayNumber})                                     | targetInteger | -4                                                                                              |
      | Divide(@{/<>/arrayNumber})                                       | targetDouble  | 0.16666666666666666                                                                             |
      | Minimum(@{/<>/arrayNumber})                                      | targetShort   | 1                                                                                               |
      | Maximum(@{/<>/arrayNumber})                                      | targetShort   | 3                                                                                               |
      | Concatenate(',',@{/sourceJsonNestedArray<>/secondArray<>/value}) | targetString  | jsonSecondArrayValue0-0,jsonSecondArrayValue0-1,jsonSecondArrayValue1-0,jsonSecondArrayValue1-1 |

  @CollectionsFieldActions
  Scenario Outline: basic collection fields actions with nested collections in expressions
    When click on create new mapping from target "<target>"
    And set mapping condition to "<expression>" by selecting sources
#    And click on "<target>"

    And set expected data
      | <target>      |
      | <targetValue> |

    And sleep for "500"
    And save mapping as "collections_expressions_field_actions.json"
    And verify "collections_expressions_field_actions.json"

    Examples:
      | expression                                                       | target       | targetValue                                 |
      | Concatenate(',',capitalize(@{/sourceJsonNestedArray<>/value}))   | targetString | JsonFirstArrayValue0,JsonFirstArrayValue1   |
      | Concatenate(',',lowercase(@{/sourceJsonNestedArray<>/value}))    | targetString | jsonfirstarrayvalue0,jsonfirstarrayvalue1   |
      | Concatenate(',',uppercase(@{/sourceJsonNestedArray<>/value}))    | targetString | JSONFIRSTARRAYVALUE0,JSONFIRSTARRAYVALUE1   |
      | Concatenate(',',length(@{/sourceJsonNestedArray<>/value}))       | targetString | 20,20                                       |
      | Concatenate(',',append('x',@{/sourceJsonNestedArray<>/value}))   | targetString | jsonFirstArrayValue0x,jsonFirstArrayValue1x |
      | Concatenate(',',prepend('x',@{/sourceJsonNestedArray<>/value}))  | targetString | xjsonFirstArrayValue0,xjsonFirstArrayValue1 |
      | Concatenate(',',endswith('1',@{/sourceJsonNestedArray<>/value})) | targetString | false,true                                  |