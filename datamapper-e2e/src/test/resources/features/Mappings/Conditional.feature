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
  Scenario Outline: basic conditional mapping by Control key
    When click on "<target>"
    And set mapping condition to "<expression>" by Control key

    And set source data
      | sourceString |
      |              |

    And set expected data
      | <target>      |
      | <targetValue> |

    And save mapping as "conditional_ctrl.xml"
    And verify "conditional_ctrl.xml"

    Examples:
      | expression                                                                       | target        | targetValue |
      | if(ISEMPTY(@{sourceString}), @{sourceInteger}, @{sourceShort} )                  | targetInteger | 1           |
      | if(!ISEMPTY(@{sourceString}), @{sourceShort}, @{sourceLong} )                    | targetString  | 2           |
      | if( @{sourceBigDecimal} < @{sourceBigInteger} , @{sourceLong}, @{sourceDouble} ) | targetString  | 4.0         |
      | if( @{sourceFloat} > @{sourceBigInteger} , @{sourceLong}, @{sourceBigDecimal} )  | targetString  | 12345       |

  Scenario: basic conditional mapping by auto completion
    When click on "targetInteger"
    And set mapping condition to "if(ISEMPTY(@{sourceString}), @{sourceInteger}, @{sourceShort} )" by auto completion

    And set source data
      | sourceString | sourceInteger | sourceShort |
      |              | 1             | 2           |

    And set expected data
      | targetInteger |
      | 1             |

    And save mapping as "conditional_auto_completion.xml"
    And verify "conditional_auto_completion.xml"


  @SmokeTest
  @ConditionalJson
  @MappingsPreview
  Scenario Outline: basic conditional mapping by Control key on Json
    When click on "targetJsonString"
    And set mapping condition to "<expression>" by auto completion
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
      | expression                                                                         | value | realValue        |
      | if(ISEMPTY(@{sourceJsonInteger}), @{sourceJsonString}, @{sourceJsonDouble} )       | 2     | 40.0             |
        # doesn't work
      | if(ISEMPTY(@{sourceJsonInteger}), @{sourceJsonString}+@{sourceJsonDouble},@{sourceJsonInteger} + 2) | 102   | 12              |
      | if(LT(@{sourceJsonInteger},-10), @{sourceJsonString}, @{sourceJsonDouble} )        | 2     | 40.0             |
      | if(LT(@{sourceJsonInteger},140), @{sourceJsonString}, @{sourceJsonDouble} )        | TesT  | sourceJsonString |
      | if(@{sourceJsonInteger} > 50, @{sourceJsonString}, @{sourceJsonDouble} )           | TesT  | 40.0             |
      | if(@{sourceJsonInteger} > 140, @{sourceJsonString}, @{sourceJsonDouble} )          | 2     | 40.0             |
      | if(@{sourceJsonInteger} < -10, @{sourceJsonString}, @{sourceJsonDouble} )          | 2     | 40.0             |
      | if(@{sourceJsonInteger} < 140, TOLOWER(@{sourceJsonString}), @{sourceJsonDouble} ) | test  | sourcejsonstring |
      #doesn't work due to bug
      | if(@{sourceJsonInteger} > 140, @{sourceJsonString}+@{sourceJsonDouble}, @{sourceJsonInteger} + 2  ) | 102     | 12              |


  @ConditionalJsonMath
  Scenario Outline: basic conditional mapping by Control key on Json
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

