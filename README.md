# Atlasmap tests

 E2E tests of standalone atlasmap based on Selenide and Cucumber framework.

- _test-resources_ - test mapping classes that are loaded into atlasmap.
- _mapping-validator_ used for processing mapping files with atlasmap-camel
- _datamapper-e2e_ contains gherkin test scenarios and run tests.

## How to run tests
1. `cd {ATLASMAP_QE}/datamapper-e2e/`
2. run tests with: `../mvnw clean test -Dselenide.browser-size=1920x1080 -Dselenide.browser=Chrome -Dworkspace.dir=${DIR} -Dui.path=${URL}`
    * `${DIR}` is folder where is _AtlasMap_ installed
    * `${URL}` must point to running _AtlasMap Data Mapper UI_
    * add `-Dselenide.headless=true` if you want to run test in the background
    * add `-Dselenide.holdBrowserOpen=false` if you want to keep browser opened after tests
    * add `-Dcucumber.options='--tags @SmokeTest'` if you want to run only tests tagged with `@SmokeTest`

## How it works
During
`mvn clean install -Datlasui.path=../${PATH_TO_ATLASMAP_UI}/` following steps are executed:

1. _maven-resource-plugin_ copies modified _data.mapper.example.host.component.ts_ with source and target classes set into datamaper UI
2. _maven-frontend-plugin_ build datamapper UI
3. Built UI is copied into target/dist directory
4. _atlasmap-runtime_ is started
5. Test execution:
    - datamaper UI load SourceMappingTestClass and TargetMappingTestClass
    - Set mappings via selenide
    - save mapping.xml
    - process and verify mapping.xml in camel-route
6. Tests passed




