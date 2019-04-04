package io.atlasmap.qe.test.atlas;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import io.atlasmap.qe.test.atlas.steps.CucumberGlue;
import io.atlasmap.qe.test.atlas.utils.Constants;

public class AtlasmapPage {

    public static final String TEST_CLASS = "SourceMappingTestClass";
    private static final Logger LOG = LogManager.getLogger(AtlasmapPage.class);

    public void openBrowser() {
        System.setProperty("window-size", "1920,1080");
        System.setProperty("selenide.chrome.switches", "--disable-web-security");

        open(Constants.UI_INDEX_PATH);
        $(".pficon.pficon-export.link").waitUntil(appear, 15000);
        $(".fa.fa-plus-square").waitUntil(appear, 15000);
    }

    public void clickOn(String elementID) {
        $(By.id(elementID)).$("label").click();
    }


    public boolean checkWarning(String exceptionType, String fromType, String toType) {
        LOG.debug("looking ...");
        $(".alert-warn").shouldBe(Condition.appears);
        for (String s : $$(".alert-warn").texts()) {
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
        $$(By.tagName("button")).filter(Condition.text(elementName)).get(0).shouldBe(visible).click();
    }

    public void clickOnElementByText(String elementName, String text) {
        $$(By.tagName(elementName)).filter(Condition.text(text)).get(0).shouldBe(visible).click();
    }

    public void selectTransformation(String transformation, String defaultValue) {
        $$(By.tagName("select")).filter(Condition.value(defaultValue.replaceAll(" ",""))).get(0).selectOption(transformation);
    }

    public void changeSelectValue(String from, String to) {
        $$(By.tagName("select")).filter(Condition.exactValue(from)).get(0).selectOption(to);
    }

    public void setInputValueByClass(String inputSelector, String inputValue) {
        $(By.className(inputSelector)).setValue(inputValue);
    }

    public void setInputValueByIdAndDefaultValue(String inputSelector, String def, String inputValue) {
        SelenideElement e = $$(By.id(inputSelector)).filter(Condition.value(def)).get(0);
        e.scrollIntoView(true);
        e.clear();
        e.setValue(inputValue);
        e.waitUntil(Condition.value(inputValue),1000);
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
        $(By.id(inputId)).parent().$$("h5").filter(Condition.text(newValue)).get(0).click();

    }

    public void selectAction(String action) {
        $("#selectAction").selectOption(action);
    }

    public void selectSeparator(String action) {
        $("#select-separator").selectOption(action);
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

        $(".pull-right.btn.btn-primary").shouldBe(visible).isDisplayed();
        clickOnButtonByText("Remove");
    }

    public void resetAll () {
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
        LOG.info("File successfully imported:" + path);
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
        WebElement dr = $$(By.id(drag)).get(1).shouldBe(visible);
        WebElement dro = $$(By.id(drop)).get(1).shouldBe(visible);
        // dr
        System.out.println(dr.toString());
        System.out.println(dro.toString());

        Actions a = new Actions(WebDriverRunner.getWebDriver());
//        a.clickAndHold(dr)
//                .moveByOffset(-1, -1)
//                .moveToElement(dro)
//                .release(dro)
//                .build()
//                .perform();
        a.dragAndDrop(dr, dro).build().perform();

    }

    public void dragAndDropElement(String dragElement, String dropElement) {

    }

    public void setInputValueForFieldPreview(String field, String value) {
       SelenideElement e = $(By.id(field)).$("textarea");
       e.setValue(value);
       e.waitUntil(Condition.value(value),1000);
    }

    public String getFieldPreviewValue(String field) {
        SelenideElement textarea = $(By.id(field)).$("textarea");
        do {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ("".equals(textarea.getValue()));
        return textarea.getValue();
    }

    public void clickOnTargets(String classSelector) {
        addTransformationOnTargetOrSource(classSelector, false);
    }

    public void addTransformationOnTargetOrSource(String classSelector, boolean isSource) {
        System.out.println("Class selector " + classSelector);
        SelenideElement e = $(By.xpath("//mapping-field-detail[@ng-reflect-is-source=\"" + isSource + "\"]")).waitUntil(visible, 5000).$(classSelector);
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
        $$(By.tagName("button")).findBy(Condition.text("Save")).click();
    }

    public void addProperty(String type, String name, String value) {
        SelenideElement e = $(By.id("Properties"));
        e.$(".fa.fa-plus.link").click();
        $(By.id("name")).waitUntil(visible, Constants.WAIT_TIMEOUT).sendKeys(name);
        $(By.id("value")).waitUntil(visible, Constants.WAIT_TIMEOUT).sendKeys(value);
        $(By.tagName("select")).shouldHave(Condition.value("String")).selectOption(type);
        $$(By.tagName("button")).findBy(Condition.text("Save")).click();
    }

    public void addTransformationToTargetOrSource(String transformation, boolean isSource) {
        SelenideElement e = $(By.xpath("//mapping-field-detail[@ng-reflect-is-source=\"" + isSource + "\"]")).waitUntil(visible, 5000);
        e.$(".fa.fa-long-arrow-right").click();
        SelenideElement parent = e.parent();
        parent.$(By.tagName("select")).selectOption(transformation);
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
        return getTextFromMappingTable(number,type,"label");
    }

    public String getPreviewValueInTable(int number,String type,String... values) {
        return getTextFromMappingTable(number,type,"textarea");
    }

    public void setPreviewValueInTable(int number,String type,String... values) {
        SelenideElement record = getFromMappingTable(number,type);
        SelenideElement[] areas = record.$$(By.tagName("textarea")).toArray(new SelenideElement[0]);
        for (int i=0; i< areas.length; i++) {
            areas[i].sendKeys(values[i]);
        }
    }

    public void clickOnRowInMappingTable(int index) {
        $$(".itemRow").get(index).click();
    }

    public void verifyThatIpnutExist(String id) {
        $(By.id(id)).should(Condition.exist);
    }
}
