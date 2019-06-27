@Mappings
@ConstantsAndProperties



Feature: Map from constant and properties

  Background:  Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

  Scenario Outline: Create constant and map to string
    When set "<ConstantType>" constant with "<value>" value
    And add mapping from "<value>" to "targetString"
    Then save and verify "<ConstantType>Constant.json" with
      | sourceString | targetString |
      | nothing      | <value>      |

    Examples:
      | ConstantType | value         |
      | String       | custom String |
      | Char         | A             |
      | Decimal      | 10            |
      | Double       | 1234.56       |
      | Float        | 4567.86       |
      | Integer      | 1234          |
      | Long         | 1234567890    |
      | Short        | 1234          |


  Scenario Outline: Create constant and map to relevant type
    When set "<ConstantType>" constant with "<value>" value
    And add mapping from "<value>" to "target<ConstantType>"
    Then save and verify "<ConstantType>Constant.json" with
      | sourceString | target<ConstantType> |
      | nothing      | <value>              |

    Examples:
      | ConstantType | value         |
      | String       | custom String |
      | Char         | A             |
      | Double       | 1234.56       |
      | Float        | 4567.86       |
      | Integer      | 1234          |
      | Long         | 1234567890    |
      | Short        | 1234          |


  Scenario Outline: Create property and map to string
    When set "My<type>" property of "<type>" type and "<value>" value
    And add mapping from "My<type>" to "targetString"
    Then save and verify "<type>Property.json" with
      | sourceString | targetString |
      | nothing      | <value>      |

    Examples:
      | type    | value         |
      | String  | custom String |
      | Char    | A             |
      | Decimal | 10            |
      | Double  | 1234.56       |
      | Float   | 4567.86       |
      | Integer | 1234          |
      | Long    | 1234567890    |
      | Short   | 1234          |


  Scenario Outline: Create property and map to relevant type
    When set "My<type>" property of "<type>" type and "<value>" value
    And add mapping from "My<type>" to "target<type>"
    Then save and verify "My<type>Property.json" with
      | sourceString | target<type> |
      | nothing      | <value>      |

    Examples:
      | type    | value         |
      | String  | custom String |
      | Char    | A             |
      | Double  | 1234.56       |
      | Float   | 4567.86       |
      | Integer | 1234          |
      | Long    | 1234567890    |
      | Short   | 1234          |

  @SmokeTest
  @CombineConstants
  Scenario: combine of constant and property to string
    When set "String" constant with "Units" value
    And set "Property" property of "Float" type and "1234.567" value

    And add mapping from "Units" to "targetString"
    And add "Property" to combine
    Then save and verify "CombineConstantAndProperty.json" with
      | sourceString | targetString    |
      | nothing      | 1234.567 Units|

