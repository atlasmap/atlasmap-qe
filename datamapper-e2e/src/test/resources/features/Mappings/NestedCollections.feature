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

#  Scenario: unable to map from 1,2 - to 3. 2 to 2 and so on...
#  Scenario: able to map from all types from 2nd and 3rt level - to 1st level

#  Scenario: transformation of nested collections
