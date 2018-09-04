@FieldActions
@NumberFieldActions
Feature: number related field actions

  Background:
    Given atlasmap is clean
    And atlasmap contains TestClass
    And browser is opened
    And internal mapping is set to "false"
    And set mapping from "sourceDouble" to "targetDouble"
  #  And reveal mapping details
    And add click "Add Transformation" link

  Scenario: absoluteValue transformation
    When select "AbsoluteValue" number transformation
    Then save and verify "AbsoluteValue.xml" with
      | sourceDouble | targetDouble |
      | -2.0         | 2.0          |
      | 2            | 2.0          |

    #Round
  Scenario: Round number
    When change transformation from "AbsoluteValue" to "Round"
    Then save and verify "Round.xml" with
      | sourceDouble | targetDouble |
      | 4.9          | 5.0          |
      | 5.2          | 5.0          |

    #Celiling
  Scenario: Celiling
    When change transformation from "AbsoluteValue" to "Ceiling"
    Then save and verify "Ceiling.xml" with
      | sourceDouble | targetDouble |
      | 4.9          | 5.0          |
      | 5.2          | 6.0          |

  Scenario: Floor number
    When change transformation from "AbsoluteValue" to "Floor"
    Then save and verify "Floor.xml" with
      | sourceDouble | targetDouble |
      | 4.9          | 4.0          |
      | 5.2          | 5.0          |

  #Convert area unit
  Scenario: Convert area unit
    When change transformation from "AbsoluteValue" to "ConvertAreaUnit"
    #square meter to square mile
    Then save and verify "Area.xml" with
      | sourceDouble | targetDouble          |
      | 5            | 1.9305107927122295E-6 |

    #meter to foot
    When change select from "Square Mile" to "Square Foot"
    Then save and verify "Area2.xml" with
      | sourceDouble | targetDouble       |
      | 5            | 53.819552083548615 |

    # meter to meter
    When change select from "Square Foot" to "Square Meter"
    And check if danger warning contains "select differing 'from' and 'to' units in your conversion transformation." message

    Then save and verify "Area3.xml" with
      | sourceDouble | targetDouble |
      | 5            | 5            |
    #mile to meter
    When change select from "Square Meter" to "Square Mile"
    Then save and verify "Area4.xml" with
      | sourceDouble          | targetDouble |
      | 1.9305107927122295E-6 | 5            |
    #  foot to meter
    When change select from "Square Mile" to "Square Foot"
    Then save and verify "Area5.xml" with
      | sourceDouble       | targetDouble |
      | 53.819552083548615 | 5            |

    #foot to foot
    When change select from "Square Meter" to "Square Foot"
    And check if danger warning contains "select differing 'from' and 'to' units in your conversion transformation." message
    Then save and verify "Area6.xml" with
      | sourceDouble | targetDouble |
      | 5            | 5            |
    #mile to foot
    When change select from "Square Foot" to "Square Mile"
    Then save and verify "Area7.xml" with
      | sourceDouble | targetDouble |
      | 5            | 1.39392E8    |

    #mile to mile
    When change select from "Square Foot" to "Square Mile"
    And check if danger warning contains "select differing 'from' and 'to' units in your conversion transformation." message
    Then save and verify "Area8.xml" with
      | sourceDouble | targetDouble |
      | 5            | 5            |

     #foot to mile
    When change select from "Square Mile" to "Square Foot"
    Then save and verify "Area9.xml" with
      | sourceDouble | targetDouble |
      | 1.39392E8    | 5            |



  Scenario: Convert Distance unit - from meter
    When change transformation from "AbsoluteValue" to "ConvertDistanceUnit"
    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble       |
      | 1000         | 0.6213711922373341 |
#
    When change select from "Mile (mi)" to "Yard (yd)"
    Then save and verify "Distance2.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 1.0936132983377079 |

    When change select from "Yard (yd)" to "Foot (ft)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 3.2808398950131235 |

       When change select from "Foot (ft)" to "Inch (in)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 39.37007874015748 |

   Scenario: Convert Distance unit - from mile
    When change transformation from "AbsoluteValue" to "ConvertDistanceUnit"
         When change select from "Mile (mi)" to "Meter (m)"
         When change select from "Meter (m)" to "Mile (mi)"
    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble       |
      | 0.6213711922373341         | 1000 |
#
    When change select from "Meter (m)" to "Yard (yd)"
    Then save and verify "Distance2.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 1760.0 |

    When change select from "Yard (yd)" to "Foot (ft)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 5280.0 |

       When change select from "Foot (ft)" to "Inch (in)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 63360.0 |


  Scenario: Convert Distance unit - from yard
    When change transformation from "AbsoluteValue" to "ConvertDistanceUnit"
         When change select from "Meter (m)" to "Yard (yd)"
    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble       |
      | 1760.0       | 1 |
#
    When change select from "Mile (mi)" to "Meter (m)"
    Then save and verify "Distance2.xml" with
      | sourceDouble | targetDouble       |
      | 1.0936132983377079            | 1 |

    When change select from "Meter (m)" to "Foot (ft)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 3.0 |

       When change select from "Foot (ft)" to "Inch (in)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 36.0 |

Scenario: Convert Distance unit - from Foot
    When change transformation from "AbsoluteValue" to "ConvertDistanceUnit"
         When change select from "Meter (m)" to "Foot (ft)"
    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble       |
      | 5280.0       | 1 |
#
    When change select from "Mile (mi)" to "Meter (m)"
    Then save and verify "Distance2.xml" with
      | sourceDouble | targetDouble       |
      | 1         | 0.30479999999999996 |

    When change select from "Meter (m)" to "Yard (yd)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble       |
      | 3            | 1 |

       When change select from "Yard (yd)" to "Inch (in)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 12.0 |

  Scenario: Convert Distance unit - from Inch
    When change transformation from "AbsoluteValue" to "ConvertDistanceUnit"
    When change select from "Meter (m)" to "Inch (in)"
    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble |
      | 63360.0       | 1            |
#
    When change select from "Mile (mi)" to "Meter (m)"
    Then save and verify "Distance2.xml" with
      | sourceDouble | targetDouble        |
      | 39.37007874015748           | 1 |

    When change select from "Meter (m)" to "Yard (yd)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble |
      | 36.0           | 1            |

    When change select from "Yard (yd)" to "Foot (ft)"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble |
      | 12            | 1        |


#
 # =======================
  Scenario: Convert Mass unit
    When change transformation from "AbsoluteValue" to "ConvertMassUnit"
    Then save and verify "Mass.xml" with
      | sourceDouble | targetDouble      |
      | 17           | 37.47858457142919 |

    When change select from "Pound (lb)" to "Kilogram (kg)"
    And change select from "Kilogram (kg)" to "Pound (lb)"
    Then save and verify "Mass2.xml" with
      | sourceDouble      | targetDouble |
      | 37.47858457142919 | 17           |

#===========================
  Scenario: Convert Volume unit-from Cubic Meter
    When change transformation from "AbsoluteValue" to "ConvertVolumeUnit"
    #cubic meter to liter
    Then save and verify "Volume.xml" with
      | sourceDouble | targetDouble |
      | 1            | 1000         |
    #cubic meter to cubic foot

    When change select from "Liter" to "Cubic Foot"
    Then save and verify "Volume2.xml" with
      | sourceDouble | targetDouble      |
      | 1            | 35.31466672148859 |

    #cubilc meter to Gallon (US Fluid)
    When change select from "Cubic Foot" to "Gallon (US Fluid)"
    Then save and verify "Volume3.xml" with
      | sourceDouble | targetDouble |
      | 1            | 264.17205236 |

    When change select from "Gallon (US Fluid)" to "Cubic Meter"
    And check if danger warning contains "select differing 'from' and 'to' units in your conversion transformation." message
    Then save and verify "Volume4.xml" with
      | sourceDouble | targetDouble |
      | 1            | 1            |

  Scenario: Convert Volume unit-from US Galon
    When change transformation from "AbsoluteValue" to "ConvertVolumeUnit"
    And change select from "Cubic Meter" to "Gallon (US Fluid)"
    # to liter
    Then save and verify "Volume5.xml" with
      | sourceDouble | targetDouble      |
      | 1            | 3.785411783973468 |

    When change select from "Liter" to "Cubic Foot"
    Then save and verify "Volume6.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 0.1336805555546186 |


    When change select from "Cubic Foot" to "Cubic Meter"
    Then save and verify "Volume7.xml" with
      | sourceDouble | targetDouble |
      | 264.17205236 | 1            |

    When change select from "Cubic Meter" to "Gallon (US Fluid)"
    And check if danger warning contains "select differing 'from' and 'to' units in your conversion transformation." message
    Then save and verify "Volume8.xml" with
      | sourceDouble | targetDouble |
      | 1            | 1            |

  Scenario: Convert Volume unit-from Cubic Foot
    When change transformation from "AbsoluteValue" to "ConvertVolumeUnit"
    And change select from "Cubic Meter" to "Cubic Foot"
    # to liter
    Then save and verify "Volume9.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 28.316846591999997 |

    When change select from "Liter" to "Gallon (US Fluid)"
    Then save and verify "Volume10.xml" with
      | sourceDouble | targetDouble      |
      | 1            | 7.480519480571911 |


    When change select from "Gallon (US Fluid)" to "Cubic Meter"
    Then save and verify "Volume11.xml" with
      | sourceDouble      | targetDouble |
      | 35.31466672148859 | 1            |

    When change select from "Cubic Meter" to "Cubic Foot"
    And check if danger warning contains "select differing 'from' and 'to' units in your conversion transformation." message
    Then save and verify "Volume12.xml" with
      | sourceDouble | targetDouble |
      | 1            | 1            |


  Scenario: Convert Volume unit-from Liter
    When change transformation from "AbsoluteValue" to "ConvertVolumeUnit"
    And change select from "Liter" to "Cubic Foot"
    And change select from "Cubic Meter" to "Liter"

    # to cubic Foot
    Then save and verify "Volume13.xml" with
      | sourceDouble | targetDouble      |
      | 28.316846591999997            | 1 |

    When change select from "Cubic Foot" to "Cubic Meter"
    Then save and verify "Volume14.xml" with
      | sourceDouble | targetDouble       |
      | 1000            | 1 |


    When change select from "Cubic Meter" to "Gallon (US Fluid)"
    Then save and verify "Volume15.xml" with
      | sourceDouble | targetDouble |
      | 3.785411783973468 | 1            |

    When change select from "Gallon (US Fluid)" to "Liter"
    And check if danger warning contains "select differing 'from' and 'to' units in your conversion transformation." message
    Then save and verify "Volume16.xml" with
      | sourceDouble | targetDouble |
      | 1            | 1            |


  Scenario: Index of
    When change transformation from "AbsoluteValue" to "IndexOf"
    And for "input-string" input set "1"

    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble       |
      | 654321         | 5 |

  Scenario: Last Index of
    When change transformation from "AbsoluteValue" to "LastIndexOf"
    And for "input-string" input set "1"

    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble       |
      | 212121         | 5 |

