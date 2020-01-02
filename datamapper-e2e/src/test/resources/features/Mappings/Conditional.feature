@Mappings
@Conditional

Feature: conditional mappings

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
    And open all subfolders

  @smoke
  Scenario Outline: basic conditional mapping by Control key with <expression>
    When click on "<target>"
    And set mapping condition to "<expression>" by Control key
    And Show mapping preview

    And click on "<target>"
    And set source data
      | sourceString |
      |              |


    And set expected data
      | <target>      |
      | <targetValue> |

    And sleep for "500"
    And save mapping as "conditional_ctrl.json"
    And verify "conditional_ctrl.json"

    Examples:
      | expression                                                                       | target        | targetValue |
      | if(ISEMPTY(@{sourceString}), @{sourceInteger}, @{sourceShort})                  | targetInteger | 1           |
      | if(!ISEMPTY(@{sourceString}), @{sourceShort}, @{sourceLong})                    | targetString  | 2           |
      | if( @{sourceBigDecimal} < @{sourceBigInteger} , @{sourceLong}, @{sourceDouble} ) | targetString  | 4.0         |
      | if( @{sourceFloat} > @{sourceBigInteger} , @{sourceLong}, @{sourceBigDecimal} )  | targetString  | 12345       |

  Scenario: basic conditional mapping by auto completion
    When click on "targetInteger"
    And set mapping condition to "if(ISEMPTY(@{sourceString}), @{sourceInteger}, @{sourceShort} )" by selecting sources

    And Show mapping preview
    And click on "targetInteger"

    And set source data
      | sourceString | sourceInteger | sourceShort |
      |              | 1             | 2           |

    And set expected data
      | targetInteger |
      | 1             |

    And sleep for "30000"
    And save mapping as "conditional_auto_completion.json"
    And verify "conditional_auto_completion.json"


  @SmokeTest
  @ConditionalJson
  @MappingsPreview
  Scenario Outline: basic conditional mapping by Control key on Json objects with expressions <expression>
    When click on "targetJsonString"
    And set mapping condition to "<expression>" by selecting sources
    And Show mapping preview
    And set preview data
      | sourceJsonInteger | sourceJsonString | sourceJsonDouble |
      | 100               | TesT             | 2                |

    #for loosing focus
    And click on "targetJsonString"
    And verify that "targetJsonString" contains "<value>"

    Then save mapping as "ConditionalJSon.json" and verify "targetJson" with
      | "targetJsonString":"<realValue>" |

    #real value comes from real executed mapping
    Examples:
      | expression                                                                                          | value | realValue        |
      | if(ISEMPTY(@{sourceJsonInteger}), @{sourceJsonString}, @{sourceJsonDouble} )                        | 2     | 40.0             |
        # doesn't work
      #| if(ISEMPTY(@{sourceJsonInteger}), @{sourceJsonString}+@{sourceJsonDouble},@{sourceJsonInteger} + 2) | 200   | dsf              |
      | if(LT(@{sourceJsonInteger},-10), @{sourceJsonString}, @{sourceJsonDouble} )                         | 2     | 40.0             |
      | if(LT(@{sourceJsonInteger},140), @{sourceJsonString}, @{sourceJsonDouble} )                         | TesT  | sourceJsonString |
      | if(@{sourceJsonInteger} > 50, @{sourceJsonString}, @{sourceJsonDouble} )                            | TesT  | 40.0             |
      | if(@{sourceJsonInteger} > 140, @{sourceJsonString}, @{sourceJsonDouble} )                           | 2     | 40.0             |
      | if(@{sourceJsonInteger} < -10, @{sourceJsonString}, @{sourceJsonDouble} )                           | 2     | 40.0             |
      | if(@{sourceJsonInteger} < 140, TOLOWER(@{sourceJsonString}), @{sourceJsonDouble} )                  | test  | sourcejsonstring |
      #doesn't work due to bug
      #| if(@{sourceJsonInteger} > 140, @{sourceJsonString}+@{sourceJsonDouble}, @{sourceJsonInteger} + 2  ) | 4     | 33               |


  @ConditionalJsonMath
  Scenario Outline: Conditional mapping with mathematical expressions: <expression>
    When click on "targetJsonString"
    And set mapping condition to "<expression>" by Control key
    And Show mapping preview
    And set preview data
      | sourceJsonInteger | sourceJsonDouble |
      | 100               | 2                |

    #for loosing focus
    And click on "targetJsonString"
    And verify that "targetJsonString" contains "<value>"
    Then save mapping as "ConditionalJSon.json" and verify "targetJson" with
      | "targetJsonString":"<realValue>" |

    Examples:
      | expression                                                            | value | realValue           |
      | @{sourceJsonInteger} + @{sourceJsonDouble}                            | 102   | 50                  |
      | @{sourceJsonInteger} - @{sourceJsonDouble}                            | 98    | -30                 |
      | @{sourceJsonInteger} * @{sourceJsonDouble}                            | 200   | 400                 |
      | @{sourceJsonInteger} / @{sourceJsonDouble}                            | 50.0  | 0.25                |
      | 2 *  @{sourceJsonInteger} /  ( @{sourceJsonDouble} + 2 ) - 10 * 3 + 4 | 24.0  | -25.523809523809526 |

