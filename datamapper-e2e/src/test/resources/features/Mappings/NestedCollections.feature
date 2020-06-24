@NestedCollection
@CollectionMappings

# All Java scenarios are blocked by: https://issues.redhat.com/browse/ENTESB-13649
# They are commented out, should be reintroduced once the above is fixed.

Feature: Mappings of nested collections

  Background: Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened
    And open all subfolders
#    And open all data buckets named "nestedArray"

  Scenario Outline: All levels of <from> nested collections to all levels of xml nested collections
    When add mapping from "<root><3rd>" to "/TargetXmlInstance/targetFirstArray/targetSecondArray/targetThirdArray/value"
    When add mapping from "<root><2nd>" to "/TargetXmlInstance/targetFirstArray/targetSecondArray/value"
    When add mapping from "<root><1st>" to "/TargetXmlInstance/targetFirstArray/value"
    Then save and verify mapping from nested "<from>" collection to "xml" as "nestedxmlto<from>.json"

    Examples:
      | from | root                                | 3rd                                       | 2nd                      | 1st    |
      | json | /sourceJsonNestedArray              | /secondArray/thirdArray/value             | /secondArray/value       | /value |
#      | java | /rootArray[]                          | /nestedArray[]/nestedArray[]/nestedField      | /nestedArray[]/nestedField | /nestedField |
      | xml  | /SourceXmlInstance/sourceFirstArray | /sourceSecondArray/sourceThirdArray/value | /sourceSecondArray/value | /value |

#  Scenario Outline: All levels of <from> nested collections to all levels of java nested collections
#    When add mapping from "<root><3rd>" to "/rootArray[]/nestedArray[]/nestedArray[]/nestedField"
#    When add mapping from "<root><2nd>" to "/rootArray[]/nestedArray[]/nestedField"
#    When add mapping from "<root><1st>" to "/rootArray[]/nestedField"
#    Then save and verify mapping from nested "<from>" collection to "java" as "nestedjavato<from>.json"
#
#    Examples:
#      | from | root                                  | 3rd                                           | 2nd                        | 1st          |
#      | json | /sourceJsonNestedArray<>              | /secondArray<>/thirdArray<>/value             | /secondArray<>/value       | /value       |
#      | java | /rootArray[]                          | /nestedArray[]/nestedArray[]/nestedField      | /nestedArray[]/nestedField | /nestedField |
#      | xml  | /SourceXmlInstance/sourceFirstArray<> | /sourceSecondArray<>/sourceThirdArray<>/value | /sourceSecondArray<>/value | /value       |

  Scenario Outline: All levels of <from> nested collections to all levels of json nested collections
    When add mapping from "<root><3rd>" to "/targetJsonNestedArray/secondArray/thirdArray/value"
    When add mapping from "<root><2nd>" to "/targetJsonNestedArray/secondArray/value"
    When add mapping from "<root><1st>" to "/targetJsonNestedArray/value"
    Then save and verify mapping from nested "<from>" collection to "json" as "nestedjsonto<from>.json"

    Examples:
      | from | root                                | 3rd                                       | 2nd                      | 1st    |
      | json | /sourceJsonNestedArray              | /secondArray/thirdArray/value             | /secondArray/value       | /value |
#      | java | /rootArray[]                          | /nestedArray[]/nestedArray[]/nestedField      | /nestedArray[]/nestedField | /nestedField |
      | xml  | /SourceXmlInstance/sourceFirstArray | /sourceSecondArray/sourceThirdArray/value | /sourceSecondArray/value | /value |


  # https://issues.redhat.com/browse/ENTESB-12396
  # scenario C4
  Scenario Outline: Mapping from 2nd and 3rd level of nested collection to non-nested
    When add mapping from "<element>" to "/jsonStrings"
    Then save and verify mapping from nested "<from>" collection level "<lvl>" to first level collection as "nested<from>level<lvl>toFirstLevelCollection.json"

    Examples:
      | from | lvl | element                                                                      |
      | json | 3   | /sourceJsonNestedArray/secondArray/thirdArray/value                          |
#      | java | 3   | /rootArray[]/nestedArray[]/nestedArray[]/nestedField                               |
      | xml  | 3   | /SourceXmlInstance/sourceFirstArray/sourceSecondArray/sourceThirdArray/value |
      | json | 2   | /sourceJsonNestedArray/secondArray/value                                     |
#      | java | 2   | /rootArray[]/nestedArray[]/nestedField                                             |
      | xml  | 2   | /SourceXmlInstance/sourceFirstArray/sourceSecondArray/value                  |

    #  Scenario: Asymetric nested collections support
    # tests:
    # 2. - correct mapping
    #      - 3rd level to 2nd
    #      - 2nd level to 3rd
    #      - xml, java, json - json
    #      - xml, java, json - java
    #      - xml, java, json - xml

  # https://github.com/atlasmap/atlasmap/pull/1900
  # Coverage for scenario C6 - https://issues.redhat.com/browse/ENTESB-12399
  @AsymmetricMapping
  Scenario Outline: Asymmetric nested collections support 3rd level to 2nd level
    When add mapping from "<element>" to "/targetJsonNestedArray/secondArray/value"
    And check if asymmetric mapping warning from "3" level to "2" level is displayed
    Then save and verify asymetric nested collection from third level "<from>" to second level json collection as "asymNested<from>leveltoSecondLevelCollection.json"

    Examples:
      | from | element                                                                      |
      | json | /sourceJsonNestedArray/secondArray/thirdArray/value                          |
#      | java | /rootArray[]/nestedArray[]/nestedArray[]/nestedField                               |
      | xml  | /SourceXmlInstance/sourceFirstArray/sourceSecondArray/sourceThirdArray/value |

  # https://github.com/atlasmap/atlasmap/pull/1900
  # Coverage for scenario C6 - https://issues.redhat.com/browse/ENTESB-12397
  @AsymmetricMapping
  Scenario Outline: Asymmetric nested collections support 2rd level to 3nd level
    When add mapping from "<element>" to "/TargetXmlInstance/targetFirstArray/targetSecondArray/targetThirdArray/value"
    And check if asymmetric mapping warning from "2" level to "3" level is displayed
    Then save and verify asymetric nested collection from second level "<from>" to third level xml collection as "asymNested<from>leveltoSecondLevelCollection.json"

    Examples:
      | from | element                                                     |
      | json | /sourceJsonNestedArray/secondArray/value                    |
#      | java | /rootArray[]/nestedArray[]/nestedField                          |
      | xml  | /SourceXmlInstance/sourceFirstArray/sourceSecondArray/value |

  #TODO: asymmetric mapping to Java