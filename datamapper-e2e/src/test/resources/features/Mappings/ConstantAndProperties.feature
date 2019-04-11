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
    Then save and verify "<ConstantType>Constant.xml" with
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
    Then save and verify "<ConstantType>Constant.xml" with
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
    Then save and verify "<type>Property.xml" with
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
    Then save and verify "My<type>Property.xml" with
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
  Scenario: combine of constant and property to string
    When set "String" constant with "Value=" value
    And set "Property" property of "Float" type and "1234.567" value

    And add mapping from "Value=" to "targetString"
    And add select "Combine" action
    And add "Property" to combine
    Then save and verify "CombineConstantAndProperty.xml" with
      | sourceString | targetString    |
      | nothing      | Value= 1234.567 |

