@Mappings
@ConstantsAndProperties

Feature: Map from constant and properties

  Background:  Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

  @ConstantToString
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

  @ConstantToVariousVariousTypes
  Scenario Outline: Create  <ConstantType> constant and map to relevant type
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

  # TODO(tplevko): add test for target properties
  # TODO(tplevko): add test for other properties scopes?

  @PropertyToString
  Scenario Outline: Create <type> property and map to string
    When set "source" property of type "<type>", name "<name>", scope "<scope>"
    And add mapping from "<name>" to "targetString"
    Then save and verify "<type>Property.json" with
      | sourceString | targetString |
      | nothing      | <value>      |

    Examples:
      | type    | name    | value       | scope                  |
      | String  | string  | test_string | Current Message Header |
      | Char    | char    | A           | Current Message Header |
      | Decimal | decimal | 10          | Current Message Header |
      | Double  | double  | 1234.56     | Current Message Header |
      | Float   | float   | 4567.86     | Current Message Header |
      | Integer | integer | 1234        | Current Message Header |
      | Long    | long    | 1234567890  | Current Message Header |
      | Short   | short   | 1234        | Current Message Header |
      | Boolean | boolean | true        | Current Message Header |

  @PropertyToVariousVariousTypes
  Scenario Outline: Create <type> property and map to  target <type>
    When set "source" property of type "<type>", name "<name>", scope "<scope>"
    And add mapping from "<name>" to "target<type>"
    Then save and verify "<type>Property.json" with
      | sourceString | target<type> |
      | nothing      | <value>      |

    Examples:
      | type    | name    | value         | scope                  |
      | String  | string  | test_string | Current Message Header |
      | Char    | char    | A             | Current Message Header |
      | Double  | double  | 1234.56       | Current Message Header |
      | Float   | float   | 4567.86       | Current Message Header |
      | Integer | integer | 1234          | Current Message Header |
      | Long    | long    | 1234567890    | Current Message Header |
      | Short   | short   | 1234          | Current Message Header |
      | Boolean | boolean | true          | Current Message Header |

  @SmokeTest
  @CombineConstants
  Scenario: combine of constant and property to string
    When set "String" constant with "Units" value
    When set "source" property of type "Float", name "float", scope "Current Message Header"

    And add mapping from "float" to "targetString"
    And add "Units" to combine
    Then save and verify "CombineConstantAndProperty.json" with
      | sourceString | targetString   |
      | nothing      | 4567.86 Units |

