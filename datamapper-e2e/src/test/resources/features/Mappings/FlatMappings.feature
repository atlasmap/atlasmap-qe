@Mappings
@FlatMappings

Feature: flat mappings between primitives, objects, JSON,XML ..

  Background:  Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened

  @SmokeTest
  Scenario: mapping from JSON to Java
    When set mapping from "sourceJsonInteger" to "targetInteger"
    And set mapping from "sourceJsonString" to "targetString"
    And set mapping from "sourceJsonShort" to "targetDouble"
    And set mapping from "sourceJsonDouble" to "targetFloat"

    And set expected data
      | targetString     | targetInteger | targetDouble | targetFloat |
      | sourceJsonString | 10            | -50          | 40.0        |

    Then save and verify mapping as "flatJsonToJava.json"

  Scenario: mapping from JSON to JSON
    When set mapping from "sourceJsonInteger" to "targetJsonInteger"
    And set mapping from "sourceJsonString" to "targetJsonString"
    And set mapping from "sourceJsonShort" to "targetJsonDouble"
    And set mapping from "sourceJsonDouble" to "targetJsonFloat"

    Then save mapping as "flatJavaToJSon.json" and verify "targetJson" with
      | "targetJsonInteger":10 | targetJsonString":"sourceJsonString" |

  @SmokeTest
  Scenario: mapping from Java to JSON
    When set mapping from "sourceInteger" to "targetJsonInteger"
    And set mapping from "sourceString" to "targetJsonString"
    And set mapping to "targetJsonDouble" from "sourceInteger"
    And set mapping from "sourceShort" to "targetJsonFloat"

    Then save mapping as "flatJavaToJSon.json" and verify "targetJson" with
      | "targetJsonInteger":1 | "targetJsonDouble":1 |


  Scenario: mapping from XML instance to Java
    When add mapping from "/SourceXmlInstance/sourceXmlInteger" to "targetInteger"
    And add mapping from "/SourceXmlInstance/sourceXmlString" to "targetString"
    And add mapping from "/SourceXmlInstance/sourceXmlShort" to "targetDouble"
    And add mapping from "/SourceXmlInstance/sourceXmlDouble" to "targetFloat"

    And set expected data
      | targetString | targetInteger | targetDouble | targetFloat |
      | XmlString    | 300           | 500          | 100.1       |

    Then save and verify mapping as "flatXmlToJava.json"

  Scenario: mapping from XML schema to Java
    When add mapping from "/SourceXmlMappingSchema/sourceXMLInteger" to "targetInteger"
    And add mapping from "/SourceXmlMappingSchema/sourceXMLString" to "targetString"
    And add mapping from "/SourceXmlMappingSchema/sourceXMLShort" to "targetDouble"
    And add mapping from "/SourceXmlMappingSchema/sourceXMLDouble" to "targetFloat"

    And set expected data
      | targetString | targetInteger | targetDouble | targetFloat |
      | XmlString    | 300           | 500          | 100.1       |

    Then save and verify mapping as "flatXmlToJava.json"

  @SmokeTest
  Scenario:  mapping from XML to XML
    When add mapping from "/SourceXmlInstance/sourceXmlInteger" to "/TargetXmlMappingTestClass/targetXmlInteger"
    And add mapping from "/SourceXmlInstance/sourceXmlString" to "/TargetXmlMappingTestClass/targetXmlString"
    And add mapping from "/SourceXmlInstance/sourceXmlShort" to "/TargetXmlMappingTestClass/targetXmlDouble"
    And add mapping from "/SourceXmlInstance/sourceXmlDouble" to "/TargetXmlMappingTestClass/targetXmlFloat"

    Then save mapping as "flatJavaToXmlSchema.json" and verify "targetXMLSchema" with
      | <targetXmlString>XmlString</targetXmlString> | <targetXmlInteger>300</ | <targetXmlDouble>500.0</targetXmlDouble> | <targetXmlFloat>100.1</ |

  @SmokeTest
  @XmlToJava
  Scenario: mapping from Java to XML

    When add mapping from "sourceInteger" to "/TargetXmlMappingTestClass/targetXmlInteger"
    And add mapping from "sourceString" to "/TargetXmlMappingTestClass/targetXmlString"
    And add mapping from "sourceShort" to "/TargetXmlMappingTestClass/targetXmlDouble"
    And add mapping from "sourceDouble" to "/TargetXmlMappingTestClass/targetXmlFloat"

    Then save mapping as "flatJavaToXmlSchema.json" and verify "targetXMLSchema" with
      | <targetXmlString>sourceString</targetXmlString> | <targetXmlInteger>1 | <targetXmlDouble>5.0</targetXmlDouble> |  |

  @SmokeTest
  @XmlToJson
  Scenario: mapping from XML to JSON
    When add mapping from "/SourceXmlMappingSchema/sourceXMLInteger" to "targetJsonInteger"
    And add mapping from "/SourceXmlMappingSchema/sourceXMLString" to "targetJsonString"
    And add mapping from "/SourceXmlMappingSchema/sourceXMLShort" to "targetJsonFloat"
    And add mapping from "/SourceXmlMappingSchema/sourceXMLDouble" to "targetJsonDouble"
    Then save mapping as "flatJavaToJSon.json" and verify "targetJson" with
      | "targetJsonInteger":300 | "targetJsonString":"XmlString" |

  @JsonToXml
  Scenario: mapping from JSON to XML
    When add mapping from "sourceJsonInteger" to "/TargetXmlMappingTestClass/targetXmlInteger"
    And add mapping from "sourceJsonString" to "/TargetXmlMappingTestClass/targetXmlString"
    And add mapping from "sourceJsonShort" to "/TargetXmlMappingTestClass/targetXmlDouble"
    And add mapping from "sourceJsonDouble" to "/TargetXmlMappingTestClass/targetXmlFloat"

    Then save mapping as "flatJavaToXmlSchema.json" and verify "targetXMLSchema" with
      | <targetXmlString>sourceJsonString</targetXmlString> | <targetXmlInteger>10 | <targetXmlDouble>-50.0</targetXmlDouble> |  |
