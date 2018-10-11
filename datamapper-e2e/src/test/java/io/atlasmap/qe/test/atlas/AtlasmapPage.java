package io.atlasmap.qe.test.atlas;

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


    public void openBrowser() throws InterruptedException {
        System.setProperty("window-size", "1920,1080");
        System.setProperty("selenide.chrome.switches", "--disable-web-security");

        open(Constants.UI_INDEX_PATH);
        $("#SourceMappingTestClass").waitUntil(visible, 5000);
        $("#TargetMappingTestClass").waitUntil(Condition.appear, 5000);
    }

    public void clickOn(String elementID) {
        $(By.id(elementID)).$("label").click();
    }


    public boolean checkWarning(String exceptionType, String fromType, String toType) {
        LOG.debug("looking ...");
        $(".alert-warning").shouldBe(Condition.appears);
        for (String s : $$(".alert-warning").texts()) {
            if (s.equals("Conversion from '" + fromType + "' to '" + toType + "' can cause " + exceptionType)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWarningContainMessage(String containsMessage) {
        LOG.debug("looking ...");
        $(".alert-warning").shouldBe(Condition.appears);
        for (String s : $$(".alert-warning").texts()) {
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
        $(".alert-warning").shouldNot(Condition.appears);
    }

    public void clickOnButtonByText(String elementName) {
        $$(By.tagName("button")).filter(Condition.text(elementName)).get(0).shouldBe(visible).click();
    }

    public void clickOnElementByText(String elementName, String text) {
        $$(By.tagName(elementName)).filter(Condition.text(text)).get(0).shouldBe(visible).click();
    }

    public void selectTransformation(String transformation, String deafaultValue) {
        $$(By.tagName("select")).filter(Condition.exactValue(deafaultValue)).get(0).selectOption(transformation);
    }

    public void changeSelectValue(String from, String to) {
        $$(By.tagName("select")).filter(Condition.exactValue(from)).get(0).selectOption(to);
    }

    public void setInputValueByClass(String inputSelector, String inputValue) {
        $(By.className(inputSelector)).setValue(inputValue);
    }

    public void setInputValueByIdAndDefaultValue(String inputSelector, String def, String inputValue) {
        SelenideElement e = $$(By.id(inputSelector)).filter(Condition.value(def)).get(0);
        e.clear();
        e.setValue(inputValue);
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
        $((".fa.fa-trash.link")).click();
        //Thread.sleep(10100);
        $(".pull-right.btn.btn-primary").shouldBe(visible).isDisplayed();
        clickOnButtonByText("Remove");
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
        $(By.id(field)).$("textarea").setValue(value);
    }

    public String getFieldPreviewValue(String field) {
        return $(By.id(field)).$("textarea").getValue();
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

    public String getFromMappingTable(int number, String type) {
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
        String text = "";
        for (SelenideElement e : row.$(className).$$(By.tagName("label"))) {
            if (text.toCharArray().length > 0) {
                text += ",";
            }
            text += e.getText();
        }

        return text;
    }

    public void clickOnRowInMappingTable(int index) {
        $$(".itemRow").get(index).click();
    }

    public void verifyThatIpnutExist(String id) {
        $(By.id(id)).should(Condition.exist);
    }
}
