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
    And add click "Add Transformation" button

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
    And for "input-fromUnit" input set "Square Meter"
    And for "input-toUnit" input set "Square Mile"
    Then save and verify "Area.xml" with
      | sourceDouble | targetDouble          |
      | 5            | 1.9305107927122295E-6 |

    When for "input-fromUnit" input set "Square Meter"
    And for "input-toUnit" input set "Square Foot"
    Then save and verify "Area2.xml" with
      | sourceDouble | targetDouble       |
      | 5            | 53.819552083548615 |

    When for "input-fromUnit" input set "Square Meter"
    And for "input-toUnit" input set "Square Meter"

    Then save and verify "Area3.xml" with
      | sourceDouble | targetDouble |
      | 5            | 5            |

    When for "input-fromUnit" input set "Square Mile"
    And for "input-toUnit" input set "Square Meter"

    Then save and verify "Area4.xml" with
      | sourceDouble          | targetDouble |
      | 1.9305107927122295E-6 | 5            |

    When for "input-fromUnit" input set "Square Mile"
    And for "input-toUnit" input set "Square Foot"

    Then save and verify "Area5.xml" with
      | sourceDouble          | targetDouble      |
      | 1.9305107927122295E-6 | 53.81955208354862 |

    When for "input-fromUnit" input set "Square Mile"
    And for "input-toUnit" input set "Square Mile"

    Then save and verify "Area6.xml" with
      | sourceDouble | targetDouble |
      | 5            | 5            |

    When for "input-fromUnit" input set "Square Foot"
    And for "input-toUnit" input set "Square Mile"

    Then save and verify "Area7.xml" with
      | sourceDouble      | targetDouble          |
      | 53.81955208354862 | 1.9305107927122295E-6 |

    When for "input-fromUnit" input set "Square Foot"
    And for "input-toUnit" input set "Square Meter"

    Then save and verify "Area8.xml" with
      | sourceDouble       | targetDouble      |
      | 53.819552083548615 | 5 |

    When for "input-fromUnit" input set "Square Foot"
    And for "input-toUnit" input set "Square Foot"

    Then save and verify "Area9.xml" with
      | sourceDouble      | targetDouble      |
      | 53.81955208354862 | 53.81955208354862 |


  Scenario: Convert Distance unit
    When change transformation from "AbsoluteValue" to "ConvertDistanceUnit"
    And for "input-fromUnit" input set "Meter"
    And for "input-toUnit" input set "Mile"
    Then save and verify "Distance.xml" with
      | sourceDouble | targetDouble       |
      | 1000         | 0.6213711922373341 |

    When for "input-fromUnit" input set "Mile"
    And for "input-toUnit" input set "Meter"
    Then save and verify "Distance2.xml" with
      | sourceDouble       | targetDouble |
      | 0.6213711922373341 | 1000         |

    When for "input-fromUnit" input set "Meter"
    And for "input-toUnit" input set "Yard"
    Then save and verify "Area.xml" with
      | sourceDouble | targetDouble       |
      | 1            | 1.0936132983377079 |

    When for "input-fromUnit" input set "Yard"
    And for "input-toUnit" input set "Meter"
    Then save and verify "Distance3.xml" with
      | sourceDouble | targetDouble |
      | 1            | 0.9144       |

    When for "input-fromUnit" input set "Yard"
    And for "input-toUnit" input set "Foot"
    Then save and verify "Distance4.xml" with
      | sourceDouble | targetDouble |
      | 1            | 3            |

    When for "input-fromUnit" input set "Inch"
    And for "input-toUnit" input set "Yard"
    Then save and verify "Distance5.xml" with
      | sourceDouble | targetDouble         |
      | 1            | 0.027777777777777776 |

  Scenario: Convert Mass unit
    When change transformation from "AbsoluteValue" to "ConvertMassUnit"
    And for "input-fromUnit" input set "Kilo Gram"
    And for "input-toUnit" input set "Pound"
    Then save and verify "Mass.xml" with
      | sourceDouble | targetDouble      |
      | 17           | 37.47858457142919 |

    When for "input-fromUnit" input set "Pound"
    And for "input-toUnit" input set "Kilo Gram"
    Then save and verify "Mass2.xml" with
      | sourceDouble      | targetDouble |
      | 37.47858457142919 | 17           |

#
#  Scenario: Convert Volume unit
#    When change transformation from "AbsoluteValue" to "ConvertVolumeUnit"
#    And for "input-fromUnit" input set "Litter"
#    And for "input-toUnit" input set "Litter"
#    Then save and verify "Volume.xml" with
#      | sourceDouble | targetDouble |
#      | 1            | 1000         |
#
#    When for "input-fromUnit" input set "Litter"
#    And for "input-toUnit" input set "Cubic Foot"
#    Then save and verify "Area.xml" with
#      | sourceDouble | targetDouble |
#      | 1000         | 35.3146667   |

#    When for "input-fromUnit" input set "Cubic Foot"
#    And for "input-toUnit" input set "Gallon US Fluid"
#    Then save and verify "Area.xml" with
#      | sourceDouble      | targetDouble |
#      | 37.47858457142919 | 17           |
#
#    When for "input-fromUnit" input set "Gallon US Fluid"
#    And for "input-toUnit" input set "Cubic Meter"
#    Then save and verify "Area.xml" with
#      | sourceDouble      | targetDouble |
#      | 37.47858457142919 | 17           |

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



  #TODO
#Add days
#Add seconds
#Average


#Contains

#Convert volume unit
#Current date
#Current daytime
#current time
#Equals

#Is null

