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
      }      `
};
