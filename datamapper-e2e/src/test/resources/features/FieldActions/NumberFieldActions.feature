@FieldActions
@NumberFieldActions
Feature: number related field actions

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
    And set mapping from "sourceDouble" to "targetDouble"

  Scenario Outline: Simple number transformations
    When add "<transformation>" transformation on "<source/target>"
    Then save and verify "<transformation>.xml" with
      | sourceDouble | targetDouble |
      | <source>     | <target>     |

    Examples:
      | transformation | source | target | source/target |
      | AbsoluteValue  | -2.0   | 2.0    | source        |
      | AbsoluteValue  | 2.0    | 2.0    | target        |
      | Round          | 4.9    | 5.0    | source        |
      | Round          | 5.2    | 5.0    | target        |
      | Ceiling        | 4.9    | 5.0    | source        |
      | Ceiling        | 5.2    | 6.0    | target        |
      | Floor          | 5.2    | 5.0    | source        |
      | Floor          | 4.9    | 4.0    | target        |

    Scenario: Index of
    When add "IndexOf" transformation on "source"
    And for "input-string" input set "1"

    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble |
      | 654321       | 5            |

  Scenario: Last Index of
    When add "LastIndexOf" transformation on "target"
    And for "input-string" input set "1"

    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble |
      | 212121       | 5            |

  Scenario Outline: unit conversions
    When add "<transformation>" transformation on "<source/target>"
    And set from "<from>" to "<to>" units on "<source/target>"
   # And
    Then save and verify "<transformation><from><to>.xml" with
      | sourceDouble | targetDouble |
      | <fromValue>  | <toValue>    |

    Examples:
      | transformation    | from              | to                | fromValue          | toValue            | source/target |
    # Area units
      | ConvertAreaUnit     | Square Meter  | Square Mile   | 5                     | 1.9305107927122295E-6 | source        |
      | ConvertAreaUnit     | Square Meter  | Square Foot   | 5                     | 53.819552083548615    | target        |
      | ConvertAreaUnit     | Square Meter  | Square Meter  | 5                     | 5                     | source        |
      | ConvertAreaUnit     | Square Mile   | Square Meter  | 1.9305107927122295E-6 | 5                     | target        |
      | ConvertAreaUnit     | Square Mile   | Square Foot   | 5                     | 1.39392E8             | source        |
      | ConvertAreaUnit     | Square Mile   | Square Mile   | 5                     | 5                     | target        |
      | ConvertAreaUnit     | Square Foot   | Square Meter  | 53.819552083548615    | 5                     | source        |
      | ConvertAreaUnit     | Square Foot   | Square Foot   | 5                     | 5                     | target        |
      | ConvertAreaUnit     | Square Foot   | Square Mile   | 1.39392E8             | 5                     | source        |
      #Distance units
      | ConvertDistanceUnit | Meter (m)     | Mile (mi)     | 1000                  | 0.6213711922373341    | source        |
      | ConvertDistanceUnit | Meter (m)     | Yard (yd)     | 1                     | 1.0936132983377079    | target        |
      | ConvertDistanceUnit | Meter (m)     | Foot (ft)     | 1                     | 3.2808398950131235    | source        |
      | ConvertDistanceUnit | Meter (m)     | Inch (in)     | 1                     | 39.37007874015748     | target        |
      | ConvertDistanceUnit | Mile (mi)     | Meter (m)     | 0.6213711922373341    | 1000                  | source        |
      | ConvertDistanceUnit | Mile (mi)     | Yard (yd)     | 1                     | 1760.0                | target        |
      | ConvertDistanceUnit | Mile (mi)     | Foot (ft)     | 1                     | 5280.0                | source        |
      | ConvertDistanceUnit | Mile (mi)     | Inch (in)     | 1                     | 63360.0               | target        |
      | ConvertDistanceUnit | Yard (yd)     | Meter (m)     | 1.0936132983377079    | 1                     | source        |
      | ConvertDistanceUnit | Yard (yd)     | Mile (mi)     | 1760.0                | 1                     | target        |
      | ConvertDistanceUnit | Yard (yd)     | Foot (ft)     | 1                     | 3.0                   | source        |
      | ConvertDistanceUnit | Yard (yd)     | Inch (in)     | 1                     | 36.0                  | target        |
      | ConvertDistanceUnit | Foot (ft)     | Meter (m)     | 3.2                   | 0.9753599999999999    | source        |
      | ConvertDistanceUnit | Foot (ft)     | Mile (mi)     | 5280                  | 1                     | target        |
      | ConvertDistanceUnit | Foot (ft)     | Yard (yd)     | 3                     | 1.0                   | source        |
      | ConvertDistanceUnit | Foot (ft)     | Inch (in)     | 1                     | 12                    | target        |
      | ConvertDistanceUnit | Inch (in)     | Meter (m)     | 39.37007874015748     | 1                     | source        |
      | ConvertDistanceUnit | Inch (in)     | Mile (mi)     | 63360.0               | 1                     | target        |
      | ConvertDistanceUnit | Inch (in)     | Yard (yd)     | 36                    | 1                     | source        |
      | ConvertDistanceUnit | Inch (in)     | Foot (ft)     | 12                    | 1                     | target        |
#       #Mass Units
      | ConvertMassUnit   | Kilogram (kg)     | Pound (lb)        | 17                 | 37.47858457142919  | source        |
      | ConvertMassUnit   | Pound (lb)        | Kilogram (kg)     | 37.47858457142919  | 17                 | target        |
        #Volume Units
      | ConvertVolumeUnit | Cubic Meter       | Liter             | 1                  | 1000               | source        |
      | ConvertVolumeUnit | Cubic Meter       | Cubic Foot        | 1                  | 35.31466672148859  | target        |
      | ConvertVolumeUnit | Cubic Meter       | Gallon (US Fluid) | 1                  | 264.17205236       | source        |
      | ConvertVolumeUnit | Liter             | Cubic Meter       | 1000               | 1                  | target        |
      | ConvertVolumeUnit | Liter             | Cubic Foot        | 28.316846591999997 | 1                  | source        |
      | ConvertVolumeUnit | Liter             | Gallon (US Fluid) | 3.785411783973468  | 1                  | target        |
      | ConvertVolumeUnit | Cubic Foot        | Cubic Meter       | 35.31466672148859  | 1                  | source        |
      | ConvertVolumeUnit | Cubic Foot        | Liter             | 1                  | 28.316846591999997 | target        |
      | ConvertVolumeUnit | Cubic Foot        | Gallon (US Fluid) | 1                  | 7.480519480571911  | source        |
      | ConvertVolumeUnit | Gallon (US Fluid) | Cubic Meter       | 264.17205236       | 1                  | target        |
      | ConvertVolumeUnit | Gallon (US Fluid) | Liter             | 1                  | 3.785411783973468  | source        |
      | ConvertVolumeUnit | Gallon (US Fluid) | Cubic Foot        | 1                  | 0.1336805555546186 | target        |

#    And check if danger warning contains "select differing 'from' and 'to' units in your conversion transformation." message

#
