export const jsonResources = {
    sourceSchema: `{
          "type" : "object",
 "properties" : {
          "sourceJsonString" : {
            "type" : "string"
          },
          "sourceJsonInteger" : {
            "type" : "integer"
          },
          "sourceJsonLong" : {
            "type" : "number"
          },
          "sourceJsonFloat" : {
            "type" : "number"
          },
          "sourceJsonDouble" : {
            "type" : "number"
          },
          "sourceJsonDate" : {
            "type" : "number"
          },
          "sourceJsonBoolean" : {
            "type" : "boolean",
            "required" : true
          },
          "sourceJsonShort" : {
            "type" : "integer"
          },
          "sourceJsonByte" : {
            "type" : "integer"
          },
          "sourceJsonChar" : {
            "type" : "string"
          },
          "smallMappingTestClass" : {
            "type" : "object",
            "properties" : {
              "objectField1" : {
                "type" : "string"
              },
              "objectField2" : {
                "type" : "string"
              },
              "listOfStrings" : {
                "type" : "array",
                "items" : {
                  "type" : "string"
                }
              },
              "listOfIntegers" : {
                "type" : "array",
                "items" : {
                  "type" : "integer"
                }
              },
              "listOfDoubles" : {
                "type" : "array",
                "items" : {
                  "type" : "number"
                }
              }
            }
          }
        }
      }`,
      targetSchema: `
      {
        "type" : "object",
        "properties" : {
          "targetJsonString" : {
            "type" : "string"
          },

          "targetJsonInteger" : {
            "type" : "integer"
          },
          "targetJsonLong" : {
            "type" : "number"
          },
          "targetJsonFloat" : {
            "type" : "number"
          },
          "targetJsonDouble" : {
            "type" : "number"
          },
          "targetJsonDate" : {
            "type" : "number"
          },
          "targetJsonBoolean" : {
            "type" : "boolean",
            "required" : true
          },
          "targetJsonShort" : {
            "type" : "integer"
          },
          "targetJsonByte" : {
            "type" : "integer"
          },
          "targetJsonChar" : {
            "type" : "string"
          },
          "smallMappingTestClass" : {
            "type" : "object",
            "properties" : {
              "objectField1" : {
                "type" : "string"
              },
              "objectField2" : {
                "type" : "string"
              },
              "listOfStrings" : {
                "type" : "array",
                "items" : {
                  "type" : "string"
                }
              },
              "listOfIntegers" : {
                "type" : "array",
                "items" : {
                  "type" : "integer"
                }
              },
              "listOfDoubles" : {
                "type" : "array",
                "items" : {
                  "type" : "number"
                }
              }
            }
          }
        }
      }      `,
      sourceInstance: `
      {
        "jsonIntegers": [0,1,2,3,4,5,6,7,8,9],
        "jsonStrings": ["json0","json1","json2","json3","json4","json5","json6","json7","json8","json9"],
        "jsonObjects": [
            {"key":0,"value":"json0"},
            {"key":1,"value":"json1"},
            {"key":2,"value":"json2"},
            {"key":3,"value":"json3"},
            {"key":4,"value":"json4"},
            {"key":5,"value":"json5"},
            {"key":6,"value":"json6"},
            {"key":7,"value":"json7"},
            {"key":8,"value":"json8"},
            {"key":9,"value":"json9"}
        ]
    }
      `
};
