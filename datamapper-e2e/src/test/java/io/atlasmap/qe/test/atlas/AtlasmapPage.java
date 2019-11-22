package io.atlasmap.qe.test.atlas;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import io.atlasmap.qe.test.atlas.steps.CucumberGlue;
import io.atlasmap.qe.test.atlas.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtlasmapPage {

    public static final String TEST_CLASS = "SourceMappingTestClass";
    private static final Logger LOG = LogManager.getLogger(AtlasmapPage.class);

    public void openBrowser() {
        System.setProperty("window-size", "1920,1080");
        System.setProperty("selenide.chrome.switches", "--disable-web-security");

        open(Constants.UI_INDEX_PATH);
        $(".pficon.pficon-export.link").waitUntil(appear, 15000);
        $(".fa.fa-plus.link").waitUntil(appear, 15000);
    }

    public void clickOn(String elementID) {
        $(By.id(elementID)).$("label").click();
    }

    public void toggleConditionalMapping() {
        $(By.xpath("/html/body/div[2]/atlasmap-dev-root/data-mapper-example-host/data-mapper/div/div/div[4]/toolbar/" +
            "div/div/div[1]/i")).click();
    }

    public boolean checkWarning(String exceptionType, String fromType, String toType) {
        LOG.debug("looking ...");

        $("*[ class=\"alert alert-danger alert-dismissable\"").shouldBe(Condition.appears);
        List<SelenideElement> se = $$(".modal-message");
        // LOG.info();
        for (String s : $$(".modal-message").texts()) {
            if (s.equals("Conversion from '" + fromType + "' to '" + toType + "' can cause " + exceptionType)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWarningContainMessage(String containsMessage) {
        LOG.debug("looking ...");
        $(".alert-warn").shouldBe(Condition.appears);
        for (String s : $$(".alert-warn").texts()) {
            if (s.contains(containsMessage)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDangerWarningContainMessage(String containsMessage) {
        LOG.debug("looking ...");
        $(".alert-danger").shouldBe(Condition.appears);
        for (String s : $$(".alert-danger").texts()) {
            if (s.contains(containsMessage)) {
                return true;
            }
        }
        return false;
    }

    public void checkWarnings() {
        LOG.debug("looking ...");
        $(".alert-warn").shouldNot(Condition.appears);
    }

    public void clickOnButtonByText(String elementName) {
        $$(By.tagName("button")).filter(text(elementName)).get(0).shouldBe(visible).click();
    }

    public void clickOnElementByText(String elementName, String text) {
        $$(By.tagName(elementName)).filter(text(text)).get(0).shouldBe(visible).click();
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

    public void setInputValueByClass(String inputSelector, String inputValue) {
        $(By.className(inputSelector)).setValue(inputValue);
    }

    public void setInputValueByIdAndDefaultValue(String inputSelector, String def, String inputValue) {
        SelenideElement e = $$(By.id(inputSelector)).filter(Condition.value(def)).get(0);
        e.scrollIntoView(true);
        e.clear();
        e.setValue(inputValue);
        e.waitUntil(Condition.value(inputValue), 1000);
    }

    public void setInputValueByClassAndDefaultValue(String inputSelector, String def, String inputValue) {
        SelenideElement e = $$(By.className(inputSelector)).filter(Condition.exactValue(def)).get(0);
        e.clear();
        e.setValue(inputValue);
    }

    public void setInputValueById(String inputId, String newValue) throws InterruptedException {
        SelenideElement e = $(By.id(inputId));
        e.clear();
        e.scrollIntoView(true);
        Thread.sleep(500);
        e.sendKeys(newValue);
        //  Thread.sleep(15000);
        $(By.id(inputId)).parent().$$("h5").filter(text(newValue)).get(0).click();
    }

    public void addToMapping(String value, boolean isSource) throws InterruptedException {
        final String cssSelector = String.format("mapping-field-container[ng-reflect-is-source=\"%s\"]", isSource);
        SelenideElement container = $(By.cssSelector(cssSelector)).waitUntil(visible, 5000);
        SelenideElement input = container.$(By.id("source"));
        input.clear();
        input.scrollIntoView(true);
        Thread.sleep(500);
        input.sendKeys(value);
        input.parent().$$(By.tagName("a")).filter(text(value)).get(0).click();
    }

    public void clickOnValueFromPicker(String pickerClass, String value) {
        SelenideElement pickerValue = $(By.className(pickerClass)).$$("div").filter(text(value)).get(0);

        pickerValue.hover();

        // wait until tooltip disappears
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 5);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class, '" + pickerClass +
            "')]/../bs-tooltip-container")));

        pickerValue.click();
    }

    public void addToConditionalMapping(String condition) {
        $(By.id("expressionMarkup")).sendKeys(condition);
    }

    public void selectAction(String action) {
        $("#selectAction").selectOption(action);
    }

    public void selectSeparator(String action) {
        $("#separator").selectOption(action);
    }

    public void openMappingDetails() {
        $((".fa.fa-plus.link")).click();
    }

    public void clickOnLinkByClass(String classSelector) {
        $(classSelector).shouldBe(visible);
        $(classSelector).click();
    }

    public void deleteCurrent() throws InterruptedException {
        $(By.className("fieldMappingDetail")).$(By.cssSelector(".fa.fa-trash.link")).click();

        $(By.xpath("//button[text()='Remove ']")).waitUntil(appear, 20000);
        clickOnButtonByText("Remove");
    }

    public void resetAll() {
        $(".fa.fa-cog.link").click();
        clickOnElementByText("label", "Reset All ");
        clickOnButtonByText("Reset");
    }

    public void importJAR(String path) {
        $(By.id("usermappingsfile")).sendKeys(path);
        $(By.id("DataMapperLoadingMessage")).waitUntil(disappear, 1000);
    }

    public void enableSourceClass(String className) {
        $$(".fa.fa-plus-square").first().click();
        enableClass(className);
    }

    public void enableTargetClass(String className) {
        $$(".fa.fa-plus-square").last().click();
        enableClass(className);
    }

    private void enableClass(String className) {
        setInputValueByClass("form-control", className);
        clickOnButtonByText("OK");
        $(By.id(className)).waitUntil(appear, 15000);
        LOG.info("Class successfully enabled: " + className);
    }

    public void enableSourceDocument(String path) {
        $$(By.id("userfile")).first().sendKeys(path);
        checkIfDocumentAppeared(path);
    }

    public void enableTargetDocument(String path) {
        $$(By.id("userfile")).last().sendKeys(path);
        checkIfDocumentAppeared(path);
    }

    /**
     * Gets filename from {@code path}. And checks if element with this filename appeared.
     */
    private void checkIfDocumentAppeared(String path) {
        $(By.id(path.substring(path.lastIndexOf("/") + 1).split("\\.")[0])).waitUntil(appear, 15000);
        LOG.info("File successfully imported: " + path);
    }

    public void clickOnWhileHolding(String id, String cmd) {

        SelenideElement e = $(By.id(id)).shouldBe(visible);
        Keys k = Keys.LEFT_CONTROL;

        if (CucumberGlue.isMac) {
            k = Keys.COMMAND;
        }

        new Actions(WebDriverRunner.getWebDriver())
            .moveToElement(e)
            .keyDown(k)
            .click()
            .keyUp(k)
            .build()
            .perform();
    }

    public void dragNDrop(String drag, String drop) {
        $(By.id(drag)).dragAndDropTo($(By.id(drop)));
        $(By.id(drop)).click();
    }

    public void setInputValueForFieldPreview(String field, String value) {
        SelenideElement e = $(By.id(field)).$("textarea");
        e.scrollTo().shouldBe(visible);
        e.clear();
        e.sendKeys(value);
        e.waitUntil(Condition.value(value), 1000);
    }

    public String getFieldPreviewValue(String field) {
        SelenideElement textarea = $(By.id(field)).$("textarea");
        int timeout = 5000;
        do {
            try {
                Thread.sleep(200);
                timeout -= 200;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ("".equals(textarea.getValue()) && timeout > 0);
        return textarea.getValue();
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
        SelenideElement e = $(By.id("Constants"));
        e.$(".fa.fa-plus.link").click();
        SelenideElement textInput = $(By.id("name")).waitUntil(visible, Constants.WAIT_TIMEOUT);
        textInput.sendKeys(value);
        SelenideElement select = $(By.tagName("select")).shouldHave(Condition.value("String"));
        // System.out.println(select.toString());
        select.selectOption(type);
        $$(By.tagName("button")).findBy(text("Save")).click();
    }

    public void addProperty(String type, String name, String value) {
        SelenideElement e = $(By.id("Properties"));
        e.$(".fa.fa-plus.link").click();
        $(By.id("name")).waitUntil(visible, Constants.WAIT_TIMEOUT).sendKeys(name);
        $(By.id("value")).waitUntil(visible, Constants.WAIT_TIMEOUT).sendKeys(value);
        $(By.tagName("select")).shouldHave(Condition.value("String")).selectOption(type);
        $$(By.tagName("button")).findBy(text("Save")).click();
    }

    public void addTransformationToTargetOrSource(String transformation, boolean isSource) {
        addTransformationOnField(transformation,isSource,false);
    }

    public void selectOptionOnIndex(String option, int index, boolean isSource) {
        SelenideElement e = $(By.xpath("//mapping-field-action[@ng-reflect-is-source=\"" + isSource + "\"]")).waitUntil(visible, 5000);
        e.$$(By.tagName("select")).get(index).selectOption(option);
    }

    private SelenideElement getFromMappingTable(int number, String type) {
        SelenideElement row = $$(".itemRow").get(number);
        String className = "";
        switch (type) {
            case "sources":
                className = ".sourceFieldNames.fieldNames";
                break;
            case "targets":
                className = ".targetFieldNames.fieldNames";
                break;
            case "type":
                className = ".transition";
        }
        return row.$(className);
    }

    public String getTextFromMappingTable(int number, String type, String tag) {
        SelenideElement record = getFromMappingTable(number, type);
        String text = "";
        for (SelenideElement e : record.$$(By.tagName(tag))) {
            if (text.toCharArray().length > 0) {
                text += ",";
            }
            if ("label".equals(tag)) {
                text += e.getText();
            } else {
                text += e.getValue();
            }
        }

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
        $$(".itemRow").get(index).click();
    }

    public void verifyThatIpnutExist(String id) {
        $(By.id(id)).should(Condition.exist);
    }

    public void openAllSubfolders() {
        List<SelenideElement> subfolders = $$(By.className("parentFolder")).shouldHave(CollectionCondition.sizeGreaterThan(0));
        subfolders.forEach(sf -> {
            sf.click();
        });
    }

    public void deleteFromMapping(String field, boolean source) {
        final String cssSelector = String.format("mapping-field-detail[ng-reflect-is-source=\"%s\"]", source);
        SelenideElement fieldDetail = $(cssSelector).shouldHave(text(field));
        fieldDetail.$(".pficon.pficon-delete.link").click();
    }

    public void changeIndexValue(String field, int value, boolean source) {
        final String cssSelector = String.format("mapping-field-detail[ng-reflect-is-source=\"%s\"]", source);
        SelenideElement fieldDetail = $$(cssSelector).filter(text(field)).first();
        fieldDetail.$(".index-value").$(By.tagName("input")).setValue(value + "");
    }

    public void addCollectionTransformation(String transformation) {
        addTransformationOnField(transformation,true,true);
    }

    private void addTransformationOnField(String transformation,boolean isSource, boolean isCollection) {
        final String cssSelector = String.format("mapping-field-container[ng-reflect-is-source=\"%s\"]", isSource);
        SelenideElement e = $(By.cssSelector(cssSelector)).waitUntil(visible, 5000);

        if(!isCollection) {
            log.debug(e.toString());
            SelenideElement link = e.$$(By.tagName("label")).filter(text("Add Transformation")).first();
            log.debug(link.toString());
            if(e.$$(By.tagName("select")).size()<1) {
                link.click();
            }
        }
        List<SelenideElement> selects = e.$$(By.tagName("select"));
        if (selects.size() > 1 && !isCollection) {
          selects.get(1).selectOption(transformation);
        } else {
            selects.get(0).selectOption(transformation);
        }
    }
}
