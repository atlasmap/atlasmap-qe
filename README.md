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
    * add `-Dcucumber.options='--tags @SmokeTest'` if you want to run only tests tagged with `@SmokeTest`

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

By default the testsuite will not download any drivers when running tests. To download drivers, you need to use profile download-drivers, for example `mvn clean install -DskipTests -Pdownload-drivers`. By default the testsuite will download latest drivers which may not work with older browsers. To use older webdriver, find supported version for your browser and set following maven property:

### Chrome

Find supported driver version for chrome browser here: http://chromedriver.chromium.org/downloads

Use following maven parameter when starting the tests: `-Dchrome.driver.version=<selected_version>`

For Chrome version 75, the parameter would be `75.0.3770.8`

For Chrome version 74, the parameter would be `74.0.3729.6`

For Chrome version 73, the parameter would be `73.0.3683.68`

### Firefox

Find supported driver version for your firefox browser here: https://firefox-source-docs.mozilla.org/testing/geckodriver/geckodriver/Support.html

Use following maven parameter when starting the tests: ``-Dfirefox.driver.version=<selected_version>``

For firefox 57 the parameter would be `0.24.0`
