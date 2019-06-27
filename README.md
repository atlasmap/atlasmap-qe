# AtlasMap tests

E2E tests of standalone _AtlasMap_ based on _Selenide_ and _Cucumber_ framework.

* _test-resources_ contains test mapping classes that are loaded into _AtlasMap_.
* _mapping-validator_ is used for processing mapping files with _atlasmap-camel_.
* _datamapper-e2e_ contains _gherkin_ test scenarios and run tests.

## How to build project?
1. download project via `git clone https://github.com/atlasmap/atlasmap-qe ${ATLASMAP_QE}`
2. `cd ${ATLASMAP_QE}`
3. build project with `./mvnw clean install -DskipTests`

## How to run tests?
1. `cd ${ATLASMAP_QE}/datamapper-e2e/`
2. run tests with: `../mvnw clean test -Dselenide.browser-size=1920x1080 -Dselenide.browser=Chrome`
    * add `-Datlasmap.version=${VERSION}` to specify AtlasMap version
    * add `-Dselenide.headless=true` if you want to run tests in the background
    * add `-Dselenide.holdBrowserOpen=true` if you want to keep browser opened after tests
    * add `-Dcucumber.options='--tags @SmokeTest'` if you want to run only tests tagged with `@SmokeTest`

## How it works?
1. _maven-dependency-plugin_ downloads _JAR_ with standalone _AtlasMap_
2. _process-exec-maven-plugin_ runs the _JAR_
3. Tests initialization:
    1. resets _AtlasMap_
    2. enables classes from _test-resources_
    3. loads _json_ and _xml_ documents
4. Test execution:
    1. sets mappings via _Selenide_
    2. saves _mapping.json_
    3. processes and verifies _mapping.json_ in _camel-route_ via _mapping-validator_
    4. removes mappings
5. Tests passed
