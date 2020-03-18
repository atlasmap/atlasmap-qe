@FieldActions
@NumberFieldActions
Feature: number related transformations

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
    And set mapping from "sourceDouble" to "targetDouble"

  @NumberFieldActionsBasic
  Scenario Outline: Simple number transformations: <transformation> on <source/target>
    When add "<transformation>" transformation on "<source/target>"
    Then save and verify "<transformation>.json" with
      | sourceDouble | targetDouble |
      | <source>     | <target>     |

    Examples:
      | transformation | source | target | source/target |
      | Absolute Value | -2.0   | 2.0    | source        |
      | Absolute Value | 2.0    | 2.0    | target        |
      | Round          | 4.9    | 5.0    | source        |
      | Round          | 5.2    | 5.0    | target        |
      | Ceiling        | 4.9    | 5.0    | source        |
      | Ceiling        | 5.2    | 6.0    | target        |
      | Floor          | 5.2    | 5.0    | source        |
      | Floor          | 4.9    | 4.0    | target        |
      | Is Null        | 0.0    | 0.0    | source        |
      | Length         | 100.0  | 5      | source        |

  @NumberFieldActionsWithInputs
  Scenario Outline: transformations with inputs: <transformation> on <source/target>
    When add "<transformation>" transformation on "<source/target>"
    And set "<input-1>" for transformation to "<input-1-value>"
    Then save and verify "<transformation>.json" with
      | sourceDouble | targetDouble |
      | <source>     | <target>     |

    Examples:
      | source/target | transformation   | input-1       | input-1-value | source | target |
      | source        | Equals           | Value         | 100.0         | 100.0  | 1.0    |
      | source        | Equals           | Value         | 100.0         | 99.0   | 0.0    |

  @UnitConversions
  Scenario Outline: unit conversions: <from> to <to>
    When add "<transformation>" transformation on "<source/target>"
    And set from "<from>" to "<to>" units on "<source/target>"
   # And
    Then save and verify "<transformation><from><to>.json" with
      | sourceDouble | targetDouble |
      | <fromValue>  | <toValue>    |

    Examples:
      | transformation        | from              | to                | fromValue             | toValue               | source/target |
    # Area units
      | Convert Area Unit     | Square Meter      | Square Mile       | 5                     | 1.9305107927122295E-6 | source        |
      | Convert Area Unit     | Square Meter      | Square Foot       | 5                     | 53.819552083548615    | target        |
      | Convert Area Unit     | Square Meter      | Square Meter      | 5                     | 5                     | source        |
      | Convert Area Unit     | Square Mile       | Square Meter      | 1.9305107927122295E-6 | 5                     | target        |
      | Convert Area Unit     | Square Mile       | Square Foot       | 5                     | 1.39392E8             | source        |
      | Convert Area Unit     | Square Mile       | Square Mile       | 5                     | 5                     | target        |
      | Convert Area Unit     | Square Foot       | Square Meter      | 53.819552083548615    | 5                     | source        |
      | Convert Area Unit     | Square Foot       | Square Foot       | 5                     | 5                     | target        |
      | Convert Area Unit     | Square Foot       | Square Mile       | 1.39392E8             | 5                     | source        |
      #Distance units
      | Convert Distance Unit | Meter (m)         | Mile (mi)         | 1000                  | 0.6213711922373341    | source        |
      | Convert Distance Unit | Meter (m)         | Yard (yd)         | 1                     | 1.0936132983377079    | target        |
      | Convert Distance Unit | Meter (m)         | Foot (ft)         | 1                     | 3.2808398950131235    | source        |
      | Convert Distance Unit | Meter (m)         | Inch (in)         | 1                     | 39.37007874015748     | target        |
      | Convert Distance Unit | Mile (mi)         | Meter (m)         | 0.6213711922373341    | 1000                  | source        |
      | Convert Distance Unit | Mile (mi)         | Yard (yd)         | 1                     | 1760.0                | target        |
      | Convert Distance Unit | Mile (mi)         | Foot (ft)         | 1                     | 5280.0                | source        |
      | Convert Distance Unit | Mile (mi)         | Inch (in)         | 1                     | 63360.0               | target        |
      | Convert Distance Unit | Yard (yd)         | Meter (m)         | 1.0936132983377079    | 1                     | source        |
      | Convert Distance Unit | Yard (yd)         | Mile (mi)         | 1760.0                | 1                     | target        |
      | Convert Distance Unit | Yard (yd)         | Foot (ft)         | 1                     | 3.0                   | source        |
      | Convert Distance Unit | Yard (yd)         | Inch (in)         | 1                     | 36.0                  | target        |
      | Convert Distance Unit | Foot (ft)         | Meter (m)         | 3.2                   | 0.9753599999999999    | source        |
      | Convert Distance Unit | Foot (ft)         | Mile (mi)         | 5280                  | 1                     | target        |
      | Convert Distance Unit | Foot (ft)         | Yard (yd)         | 3                     | 1.0                   | source        |
      | Convert Distance Unit | Foot (ft)         | Inch (in)         | 1                     | 12                    | target        |
      | Convert Distance Unit | Inch (in)         | Meter (m)         | 39.37007874015748     | 1                     | source        |
      | Convert Distance Unit | Inch (in)         | Mile (mi)         | 63360.0               | 1                     | target        |
      | Convert Distance Unit | Inch (in)         | Yard (yd)         | 36                    | 1                     | source        |
      | Convert Distance Unit | Inch (in)         | Foot (ft)         | 12                    | 1                     | target        |
#       #Mass Units
      | Convert Mass Unit     | Kilogram (kg)     | Pound (lb)        | 17                    | 37.47858457142919     | source        |
      | Convert Mass Unit     | Pound (lb)        | Kilogram (kg)     | 37.47858457142919     | 17                    | target        |
        #Volume Units
      | Convert Volume Unit   | Cubic Meter       | Liter             | 1                     | 1000                  | source        |
      | Convert Volume Unit   | Cubic Meter       | Cubic Foot        | 1                     | 35.31466672148859     | target        |
      | Convert Volume Unit   | Cubic Meter       | Gallon (US Fluid) | 1                     | 264.17205236          | source        |
      | Convert Volume Unit   | Liter             | Cubic Meter       | 1000                  | 1                     | target        |
      | Convert Volume Unit   | Liter             | Cubic Foot        | 28.316846591999997    | 1                     | source        |
      | Convert Volume Unit   | Liter             | Gallon (US Fluid) | 3.785411783973468     | 1                     | target        |
      | Convert Volume Unit   | Cubic Foot        | Cubic Meter       | 35.31466672148859     | 1                     | source        |
      | Convert Volume Unit   | Cubic Foot        | Liter             | 1                     | 28.316846591999997    | target        |
      | Convert Volume Unit   | Cubic Foot        | Gallon (US Fluid) | 1                     | 7.480519480571911     | source        |
      | Convert Volume Unit   | Gallon (US Fluid) | Cubic Meter       | 264.17205236          | 1                     | target        |
      | Convert Volume Unit   | Gallon (US Fluid) | Liter             | 1                     | 3.785411783973468     | source        |
      | Convert Volume Unit   | Gallon (US Fluid) | Cubic Foot        | 1                     | 0.1336805555546186    | target        |
