@NestedCollection
@CollectionMappings

Feature: Mappings of nested collections

  Background: Given atlasmap contains TestClass
    And atlasmap is clean
    And internal mapping is set to "false"
    And browser is opened
    And open all subfolders
    And open all data buckets named "nestedArray"


  Scenario Outline: All levels of <from> nested collections to all levels of xml nested collections
    When add mapping from "<root><3rd>" to "/TargetXmlInstance/targetFirstArray<>/targetSecondArray<>/targetThirdArray<>/value"
    When add mapping from "<root><2nd>" to "/TargetXmlInstance/targetFirstArray<>/targetSecondArray<>/value"
    When add mapping from "<root><1st>" to "/TargetXmlInstance/targetFirstArray<>/value"
    Then save and verify mapping from nested "<from>" collection to "xml" as "nestedxmlto<from>.json"

    Examples:
      | from    | root                                  | 3rd                                            | 2nd                         | 1st          |
      | json    | /sourceJsonNestedArray<>              | /secondArray<>/thirdArray<>/value              | /secondArray<>/value        | /value       |
      | java    | /rootArray[]                          | /nestedArray[]/nestedArray[]/nestedField       | /nestedArray[]/nestedField  | /nestedField |
      | xml     | /SourceXmlInstance/sourceFirstArray<> | /sourceSecondArray<>/sourceThirdArray<>/value  | /sourceSecondArray<>/value  | /value       |

  Scenario Outline: All levels of <from> nested collections to all levels of java nested collections
    When add mapping from "<root><3rd>" to "/rootArray[]/nestedArray[]/nestedArray[]/nestedField"
    When add mapping from "<root><2nd>" to "/rootArray[]/nestedArray[]/nestedField"
    When add mapping from "<root><1st>" to "/rootArray[]/nestedField"
    Then save and verify mapping from nested "<from>" collection to "java" as "nestedjavato<from>.json"

    Examples:
      | from    | root                                  | 3rd                                            | 2nd                         | 1st          |
      | json    | /sourceJsonNestedArray<>              | /secondArray<>/thirdArray<>/value              | /secondArray<>/value        | /value       |
      | java    | /rootArray[]                          | /nestedArray[]/nestedArray[]/nestedField       | /nestedArray[]/nestedField  | /nestedField |
      | xml     | /SourceXmlInstance/sourceFirstArray<> | /sourceSecondArray<>/sourceThirdArray<>/value  | /sourceSecondArray<>/value  | /value       |

  Scenario Outline: All levels of <from> nested collections to all levels of json nested collections
    When add mapping from "<root><3rd>" to "/targetJsonNestedArray<>/secondArray<>/thirdArray<>/value"
    When add mapping from "<root><2nd>" to "/targetJsonNestedArray<>/secondArray<>/value"
    When add mapping from "<root><1st>" to "/targetJsonNestedArray<>/value"
    Then save and verify mapping from nested "<from>" collection to "json" as "nestedjsonto<from>.json"

    Examples:
      | from    | root                                  | 3rd                                            | 2nd                         | 1st          |
      | json    | /sourceJsonNestedArray<>              | /secondArray<>/thirdArray<>/value              | /secondArray<>/value        | /value       |
      | java    | /rootArray[]                          | /nestedArray[]/nestedArray[]/nestedField       | /nestedArray[]/nestedField  | /nestedField |
      | xml     | /SourceXmlInstance/sourceFirstArray<> | /sourceSecondArray<>/sourceThirdArray<>/value  | /sourceSecondArray<>/value  | /value       |


  # https://issues.redhat.com/browse/ENTESB-12396
  # scenario C4
  Scenario Outline: Mapping from 2nd and 3rd level of nested collection to non-nested
    When add mapping from "<root><element>" to "/jsonStrings<>"
    Then save and verify mapping from nested "<from>" collection level "<lvl>" to first level collection as "nested<from>level<lvl>toFirstLevelCollection.json"

    Examples:
      | from    | root                                  | lvl | element                                        |
      | json    | /sourceJsonNestedArray<>              | 3   | /secondArray<>/thirdArray<>/value              |
      | java    | /rootArray[]                          | 3   | /nestedArray[]/nestedArray[]/nestedField       |
      | xml     | /SourceXmlInstance/sourceFirstArray<> | 3   | /sourceSecondArray<>/sourceThirdArray<>/value  |
      | json    | /sourceJsonNestedArray<>              | 2   | /secondArray<>/value                           |
      | java    | /rootArray[]                          | 2   | /nestedArray[]/nestedField                     |
      | xml     | /SourceXmlInstance/sourceFirstArray<> | 2   | /sourceSecondArray<>/value                     |

