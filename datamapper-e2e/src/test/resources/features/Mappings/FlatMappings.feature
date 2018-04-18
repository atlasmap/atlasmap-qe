@Mappings
@FlatMappings

  Feature: flat mappings between primitives, objects, JSON,XML ..


    Background:  Given atlasmap contains TestClass
      And atlasmap is clean
      And internal mapping is set to "false"
      And browser is opened

    Scenario: mapping from JSON to java
      When set mapping from "sourceJsonInteger" to "targetInteger"
      And set mapping from "sourceJsonString" to "targetString"
      And set mapping from "sourceJsonShort" to "targetDouble"
      And set mapping from "sourceJsonDouble" to "targetFloat"

      And set expected data
        |targetString|targetInteger|targetDouble|targetFloat|
        |sourceJsonString |      10     |  -50     |  40.0          |

      Then save and verify mapping as "flatJsonToJava.xml"





