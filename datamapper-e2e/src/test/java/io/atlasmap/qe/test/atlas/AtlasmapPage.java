package io.atlasmap.qe.test.atlas;

import static org.assertj.core.api.Assertions.fail;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.atlasmap.qe.test.atlas.utils.ByUtils;
import io.atlasmap.qe.test.atlas.utils.HoverAction;
import io.atlasmap.qe.test.atlas.utils.TestConfiguration;
import io.atlasmap.qe.test.atlas.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtlasmapPage {

    public static final String TEST_CLASS = "SourceMappingTestClass";

    public void refreshPage() {
        Selenide.open(TestConfiguration.getUiIndexPath());
        $(ByUtils.dataTestId("atlasmap-menu-button")).waitUntil(appear, 15000);
    }

    public void toggleConditionalMapping() {
        $(ByUtils.dataTestId("enable-disable-conditional-mapping-expression-button")).click();
    }

    public boolean checkMultipleWarnings(List<String> sourceMappingData, String fromType, String toType) {

        $(By.className("pf-c-alert__icon")).waitUntil(visible, 5000);

        List<String> texts = $$(By.className("pf-c-alert__title")).texts();
        List<String> updatedSourceMappingData =
            sourceMappingData.stream().map(s -> "Warning alert:\n" +
                "Conversion from '" + fromType + "' to '" + toType + "' can cause " + s).collect(
                Collectors.toList());

        for (String s : texts) {
            log.info(s);
        }

        return CollectionUtils.isEqualCollection(texts, updatedSourceMappingData);
    }

    public boolean checkAsymmetricMappingWarning(int sourceLevel, int targetLevel) {
        $(By.className("pf-c-alert__icon")).waitUntil(visible, 5000);

        String warningText = $(By.className("pf-c-alert__title")).text();
        if (sourceLevel > targetLevel) {
            String sourceLowerMessage = "has " + targetLevel + " collection(s) on the path, whereas source has " + sourceLevel;
            return warningText.contains(sourceLowerMessage);
        } else {
            String sourceHigherMessage = "since target has " + targetLevel + " collections on the path, whereas source has " + sourceLevel + ".";
            return warningText.contains(sourceHigherMessage);
        }
    }

    public boolean checkWarning(String exceptionType, String fromType, String toType) {
        boolean containsWaringMesage = false;

        $(By.className("pf-c-alert__icon")).waitUntil(visible, 5000);
        List<String> texts = $$(By.className("pf-c-alert__description")).texts();
        if (texts.size() == 1) {
            containsWaringMesage = texts.get(0).equals("Conversion from '" + fromType + "' to '" + toType + "' can cause " + exceptionType);
        }

        return containsWaringMesage;
    }

    public boolean checkWarningContainMessage(String containsMessage) {
        log.debug("looking ...");
        $(".alert-warn").shouldBe(Condition.appears);
        for (String s : $$(".alert-warn").texts()) {
            if (s.contains(containsMessage)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDangerWarningContainMessage(String containsMessage) {
        log.debug("looking ...");
        $(".pf-m-danger").shouldBe(Condition.appears);
        for (String s : $$(".pf-c-alert__title").texts()) {
            if (s.contains("Danger alert:\n" + containsMessage)) {
                return true;
            }
        }
        return false;
    }

    public void checkWarnings() {

        log.debug("looking ...");
        $(ByUtils.dataTestId("expand-collapse-Warnings-button")).shouldNot(Condition.appears);
    }

    public void selectTransformation(String transformation, String defaultValue) {
        $$(By.tagName("select")).filter(Condition.value(defaultValue.replaceAll(" ", ""))).get(0).selectOption(transformation);
    }

    public void changeSelectValue(String from, String to) {
        $$(By.tagName("select"))
            .filter(Condition.exactValue(from))
            .get(0)
            .selectOption(to);
    }

    public void setInputValueByDataTestid(String inputSelector, String inputValue) {
        Utils.insertCharByChar(inputValue, $(ByUtils.dataTestId(inputSelector)));
    }

    /**
     * Use field panels for searching the field to be mapped
     *
     * @param value
     * @param isSource
     */
    public void addToMappingUsingFieldPanel(String value, boolean isSource) {
        hoverAndSelectOperation(value, HoverAction.CONNECT_TO_SELECTED_MAPPING, isSource ? "source" : "target");
    }

    /**
     * Add new source/target field to the mapping using the mapping detail panel field search
     *
     * @param value
     * @param isSource
     */
    public void addToMappingUsingMappingDetails(String value, boolean isSource) {
        final String sourcesTargetsSelector = String.format("mapping-fields-detail-%s-toggle", isSource ? "Sources" : "Targets");
        SelenideElement sourcesTargetsToggle = $(ByUtils.dataTestId(sourcesTargetsSelector)).waitUntil(visible, 5000);
        SelenideElement input = sourcesTargetsToggle.$(By.className("pf-c-form-control"));
        input.clear();
        input.sendKeys(value);
        $(ByUtils.dataTestId("add-field-option-" + value)).click();
    }

    public void clickOnLinkByDataTestId(String dataTestId) {
        $(ByUtils.dataTestId(dataTestId)).shouldBe(visible).click();
    }

    public void clickOnValueFromPicker(String value) {

        SelenideElement dropdown = $(ByUtils.dataTestId("expression-field-search"));
        dropdown.$(By.cssSelector(String.format("*[label=\"%s\"]", value))).click();
    }

    public void addToConditionalMapping(String condition) {
        Utils.insertCharByChar(condition,$(By.id("expressionMarkup")));
    }

    public void selectSeparator(String action) {
        $(ByUtils.dataTestId("delimiter")).selectOption(action);
    }

    public void clickOnLinkByClass(String classSelector) {
        $(classSelector).shouldBe(visible);
        $(classSelector).click();
    }

    public void deleteCurrentMapping() {

        $(ByUtils.dataTestId("remove-current-mapping-button")).shouldBe(visible).click();
        $(ByUtils.dataTestId("confirmation-dialog-confirm-button"))
            .waitUntil(visible, TestConfiguration.getWaitTimeout()).click();
    }

    public void resetAll() {
        $(ByUtils.dataTestId("atlasmap-menu-button")).shouldBe(visible).click();
        $(ByUtils.dataTestId("reset-all-button")).shouldBe(visible).click();
        $(ByUtils.dataTestId("confirmation-dialog-confirm-button"))
            .waitUntil(visible, TestConfiguration.getWaitTimeout()).click();
    }

    public void importJAR(String path) {
        $(ByUtils.dataTestId("atlasmap-menu-button")).shouldBe(visible).click();
        $(ByUtils.dataTestId("import-archive-button")).parent().$(By.tagName("input")).sendKeys(path);
        $(By.className("pf-c-spinner__tail-ball")).waitUntil(disappear, 5000);
    }

    public void importAdmFile(String path) {
        $(ByUtils.dataTestId("atlasmap-menu-button")).shouldBe(visible).click();
        $(ByUtils.dataTestId("import-mappings-button")).parent().$(By.tagName("input")).sendKeys(path);
        $(ByUtils.dataTestId("confirmation-dialog-confirm-button")).click();
        $(By.className("pf-c-spinner__tail-ball")).waitUntil(disappear, 5000);
    }

    public void enableSourceClass(String className) {
        $(ByUtils.dataTestId("enable-specific-java-classes-Source-button")).shouldBe(visible).click();
        enableClass(className);
    }

    public void enableTargetClass(String className) {
        $(ByUtils.dataTestId("enable-specific-java-classes-Target-button")).shouldBe(visible).click();
        enableClass(className);
    }

    private void enableClass(String className) {

        $(ByUtils.dataTestId("class-package-name-text-input")).waitUntil(visible, TestConfiguration.getWaitTimeout())
            .sendKeys(className);
        $(ByUtils.dataTestId("collection-type-form-select")).selectOption("None");
        $(ByUtils.dataTestId("confirmation-dialog-confirm-button")).click();

        $(ByUtils.dataTestId("expand-collapse-" + className + "-button")).waitUntil(visible, 15000);
        log.info("Class successfully enabled: " + className);
    }

    public void enableDocument(String path, boolean isSource) {
        final String buttonLocation = String.format("import-instance-or-schema-file-%s-button", isSource ? "Source" : "Target");
        $(ByUtils.dataTestId(buttonLocation)).parent().$(By.tagName("input"))
            .sendKeys(path);
    }

    public void enableCsvDocument(String path, boolean isSource, String format, Map<String, String> additionalParameters) {

        String pathExtension = FilenameUtils.getExtension(path).toUpperCase();
        if(pathExtension.equals("CSV")) {
            enableDocument(path, isSource);

            $(ByUtils.dataTestId("format-parameter-form-select")).shouldHave(Condition.value("Default"))
                .selectOption(format);

            for(String key: additionalParameters.keySet()) {
                $(By.id("selected-paramater")).shouldBe(visible).selectOption(key);
                $(By.xpath(".//button[contains(.,'Add parameter')]")).click();
                SelenideElement parameterOption = $(ByUtils.dataTestIdStartsWith(key));

                if(parameterOption.is(Condition.type("select"))) {
                    parameterOption.selectOption(additionalParameters.get(key));
                } else {
                    parameterOption.sendKeys(additionalParameters.get(key));
                }
            }
            $(ByUtils.dataTestId("confirmation-dialog-confirm-button")).click();

        } else {
            fail("The input file needs to be in csv format with .csv file suffix.");
        }
    }

    public void enableCsvSourceDocument (String path, String format, Map<String, String> additionalParameters) {
        enableCsvDocument(path, true, format, additionalParameters);
        checkIfDocumentAppeared(path);
    }

    public void enableCsvTargetDocument (String path, String format, Map<String, String> additionalParameters) {
        enableCsvDocument(path, false, format, additionalParameters);
        checkIfDocumentAppeared(path);
    }

    public void enableSourceDocument(String path) {
        enableDocument(path, true);
        checkIfDocumentAppeared(path);
    }

    public void enableTargetDocument(String path) {
        enableDocument(path, false);
        checkIfDocumentAppeared(path);
    }

    /**
     * Gets filename from {@code path}. And checks if element with this filename appeared.
     */
    private void checkIfDocumentAppeared(String path) {
        String dataTestid = String.format("expand-collapse-%s-button", path.substring(path.lastIndexOf("/") + 1).split("\\.")[0]);
        $(ByUtils.dataTestId(dataTestid)).waitUntil(appear, 15000);
        log.info("File successfully imported: " + path);
    }

    public void dragNDrop(String drag, String drop) {
        $(By.id(drag)).dragAndDropTo($(By.id(drop)));
        $(By.id(drop)).click();
    }

    public void setInputValueForFieldPreview(String field, String value) {

        final String cssSelector = String.format("*[data-testid^=\"%s\"][data-testid$=\"%s\"]", "input-document-mapping-preview-", field + "-field");
        SelenideElement pickerValue = $(By.cssSelector(cssSelector));
        pickerValue.scrollTo().shouldBe(visible);

        pickerValue.clear();
        pickerValue.sendKeys(value);
        Utils.sleep(1000);
    }

    public String getFieldPreviewValue(String field) {
        final String cssSelector =
            String.format("*[data-testid^=\"%s\"][data-testid$=\"%s\"]", "results-document-mapping-preview-", field + "-field");
        SelenideElement pickerValue = $(By.cssSelector(cssSelector));
        pickerValue.scrollTo().shouldBe(visible);

        int timeout = 5000;
        do {
            Utils.sleep(200);
            timeout -= 200;
        } while ("".equals(pickerValue.getValue()) && timeout > 0);
        return pickerValue.getValue();
    }

    public void clickOnTargets(String classSelector) {
        addTransformationOnTargetOrSource(classSelector, false);
    }

    public void addTransformationOnTargetOrSource(String classSelector, boolean isSource) {
        log.debug("Class selector " + classSelector);
        SelenideElement e =
            $(By.xpath("//mapping-field-detail[@ng-reflect-is-source=\"" + isSource + "\"]")).waitUntil(visible, 5000).$(classSelector);
        e.click();
    }

    public void addConstant(String type, String value) {
        $(ByUtils.dataTestId("create-constant-button"))
            .waitUntil(visible, 5000).click();

        $(ByUtils.dataTestId("constant-name-text-input")).waitUntil(visible, TestConfiguration.getWaitTimeout())
            .sendKeys(value);
        $(ByUtils.dataTestId("constant-type-form-select")).shouldHave(Condition.value("Boolean"))
            .selectOption(type);
        $(ByUtils.dataTestId("confirmation-dialog-confirm-button")).click();
    }

    public void addProperty(String type, String name, String value) {
        $(ByUtils.dataTestId("create-property-button"))
            .waitUntil(visible, 5000).click();

        $(ByUtils.dataTestId("property-name-text-input")).waitUntil(visible, TestConfiguration.getWaitTimeout())
            .sendKeys(name);
        $(ByUtils.dataTestId("property-value-text-input")).waitUntil(visible, TestConfiguration.getWaitTimeout())
            .sendKeys(value);
        $(ByUtils.dataTestId("property-type-form-select")).shouldHave(Condition.value("Any"))
            .selectOption(type);
        $(ByUtils.dataTestId("confirmation-dialog-confirm-button")).click();
    }

    public void addTransformationToTargetOrSource(String transformation, boolean isSource) {
        addTransformationOnField(transformation, isSource);
    }

    public void selectUnitFromOption(String option) {
        $(ByUtils.dataTestId("fromUnit")).waitUntil(visible, 5000).selectOption(option);
    }

    public void selectUnitToOption(String option) {
        $(ByUtils.dataTestId("toUnit")).waitUntil(visible, 5000).selectOption(option);
    }

    private SelenideElement getFromMappingTable(int number, String type) {
        SelenideElement table = $(".pf-c-table").$(By.tagName("tbody"));
        SelenideElement row = table.$$(By.tagName("tr")).get(number);
        String className = "";
        switch (type) {
            case "sources":
                className = "Sources";
                break;
            case "targets":
                className = "Targets";
                break;
            case "type":
                className = "Types";
        }
        return row.$(By.cssSelector(String.format("*[data-label=\"%s\"]", className)));
    }

    public String getTextFromMappingTable(int number, String type, String tag) {
        SelenideElement record = getFromMappingTable(number, type);
        String text = record.text();
        text = text.replaceAll("\n", ",");

        return text;
    }

    public String getLabelFromMappingTable(int number, String type) {
        return getTextFromMappingTable(number, type, "label");
    }

    public String getPreviewValueInTable(int number, String type, String... values) {
        return getTextFromMappingTable(number, type, "textarea");
    }

    public void setPreviewValueInTable(int number, String type, String... values) {
        SelenideElement record = getFromMappingTable(number, type);
        SelenideElement[] areas = record.$$(By.tagName("textarea")).toArray(new SelenideElement[0]);
        for (int i = 0; i < areas.length; i++) {
            areas[i].sendKeys(values[i]);
        }
    }

    public void clickOnRowInMappingTable(int index) {
        SelenideElement table = $(".pf-c-table").$(By.tagName("tbody"));
        SelenideElement row = table.$$(By.tagName("tr")).get(index).shouldBe(visible);
        row.$(By.tagName("td")).click();
    }

    public void openAllSubfolders() {
        final String cssSelector = String.format("*[data-testid^=\"%s\"][data-testid$=\"%s\"]", "field-group-", "-expanded-false-field");
        List<SelenideElement> subfolders = $$(By.cssSelector(cssSelector));

        for (int i = 0; i < subfolders.size(); i++) {
            subfolders.get(i).click();
        }
    }

    public void openAllBucketsWithName(String bucketName) {
        List<SelenideElement> buckets = $$(By.id(bucketName)).shouldHave(CollectionCondition.sizeGreaterThan(0));
        for (int i = 0; i < buckets.size(); i++) {
            buckets.get(i).click();
        }
    }

    public void deleteFromMapping(String field, boolean source) {
        hoverAndSelectOperation(field, HoverAction.DISCONNECT_FROM_SELECTED_MAPPING, source ? "source" : "target");
    }

    public void changeIndexValue(String field, int value, boolean source) {
        SelenideElement fieldDetail = $(ByUtils.dataTestId("change-" + field + "-input-index")).shouldBe(visible);
        fieldDetail.sendKeys(Keys.chord(Keys.CONTROL, "a"), "");
        fieldDetail.sendKeys(value + "");
    }

    public void addCollectionTransformationOnField(String transformation) {
        SelenideElement mappingDetailField = $(ByUtils.dataTestIdStartsWith("user-field-action"));
        mappingDetailField.selectOption(transformation);
    }

    private void addTransformationOnField(String transformation, boolean isSource) {

        final String sourcesTargetsSelector = String.format("mapping-fields-detail-%s-toggle", isSource ? "Sources" : "Targets");
        SelenideElement sourcesTargetsToggle = $(ByUtils.dataTestId(sourcesTargetsSelector)).waitUntil(visible, 5000);
        SelenideElement mappingDetailField = sourcesTargetsToggle.$(ByUtils.dataTestIdStartsWith("mapping-field-")).waitUntil(visible, 5000);

        mappingDetailField.$(ByUtils.dataTestIdStartsWith("add-transformation-to-")).sendKeys(Keys.ENTER);
        mappingDetailField.$$(By.tagName("select")).last().selectOption(transformation);
        Utils.sleep(2000);
    }

    /**
     * unwrap the nested field group specified, in specified column
     *
     * @param column
     * @param nestedFieldGroupName
     */
    public void unwrapNestedFieldGroup(SelenideElement column, String nestedFieldGroupName) {

        SelenideElement nestedFieldGroup = column.$(ByUtils.dataTestId("field-group-" + nestedFieldGroupName + "-expanded-false-field"));

        if (nestedFieldGroup.exists()) {
            nestedFieldGroup.scrollIntoView(true);
            nestedFieldGroup.click();
        }
    }

    /**
     * Initialize simple mapping from source to target
     *
     * @param field
     */
    public void createNewMapping(String field, String sourceTarget) {
        hoverAndSelectOperation(field, HoverAction.CREATE_NEW_MAPPING, sourceTarget);
        $(By.className("pf-topology-side-bar__body")).waitUntil(visible, 5000);
    }

    public void hoverAndSelectOperation(String field, HoverAction fieldOperationName, String sourceTarget) {
        SelenideElement column = $(ByUtils.dataTestId("column-" + sourceTarget + "-area"));
        String[] fields = parseFieldsName(field);
        String fieldToSelect = unwrapNestedField(column, fields);

        SelenideElement pickerValue = selectPickerValueWithIndentLevel(column, fields.length, fieldToSelect);

        pickerValue.scrollIntoView(true);
        pickerValue.click();

        SelenideElement fieldOperation =
            pickerValue.parent().$(ByUtils.dataTestId(fieldOperationName.label))
                .waitUntil(visible, 5000);
        fieldOperation.click();
    }

    private String[] parseFieldsName(String field) {
        if (field.startsWith("/")) {
            field = field.replaceFirst("/", "");
        }

        String[] fields = field.split("/");
        return fields;
    }

    private String unwrapNestedField(SelenideElement column, String[] fields) {

        String fieldToSelect = "";

        if (fields.length > 1) {

            for (int i = 0; i < fields.length; i++) {
                if (i < fields.length - 1) {
                    unwrapNestedFieldGroup(column, fields[i]);
                } else {
                    fieldToSelect = fields[i];
                }
            }
        } else {
            fieldToSelect = fields[0];
        }
        return fieldToSelect;
    }

    private SelenideElement selectPickerValueWithIndentLevel(SelenideElement column, int fieldsLength, String fieldToSelect) {

        SelenideElement pickerValue = null;
        ElementsCollection values = column.$$(ByUtils.dataTestId("document-" + fieldToSelect + "-field"));
        if (values.size() > 1) {
            for (SelenideElement s : values) {
                if (Integer.parseInt(s.parent().parent().parent().getAttribute("aria-level")) == fieldsLength) {
                    pickerValue = s;
                }
            }
        } else {
            pickerValue = values.get(0).parent();
        }

        return pickerValue;
    }
}
