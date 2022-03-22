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

1. `cd ${ATLASMAP_QE}`

2. download correct drivers (see section [Use correct webdriver version for selected browser](https://github.com/atlasmap/atlasmap-qe#use-correct-webdriver-version-for-selected-browser))

3. Update test.properties file located in the root `${ATLASMAP_QE}` directory, e.g.
```
mapping.path=target/test-classes
ui.url=http://localhost:8585
backend.url=http://localhost:8585
test.timeout=5000
atlasmap.config.ui.browser=chrome
```
4. run tests with: `../mvnw clean test` and you can use also following properties:
    * add `-Datlasmap.version=${VERSION}` to specify AtlasMap version
    * add `-Dselenide.headless=true` if you want to run tests in the background
    * add `-Dselenide.holdBrowserOpen=true` if you want to keep browser opened after tests
    * add `-Dtags='@SmokeTest'` if you want to run only tests tagged with `@SmokeTest`
    * add `-Datlasmap.mappings.root.directory=${mappings_home_dir}` in case you want explicitly specify the mappings root directory, in case it's not inside the test suite - this is set as default
    * add `-Datlasmap.fast.init=true` in case you want to setup the tests using
       * add `-Datlasmap.adm.resource=${adm_file_path}` in case you want to specify specify the adm file to be used (default is set for _test-resources/src/main/resources/atlasmap-qe.adm_)

## How does it work?
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

## Use correct webdriver version for selected browser


The correct version of browser driver is automatically detected and downloaded by [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)
