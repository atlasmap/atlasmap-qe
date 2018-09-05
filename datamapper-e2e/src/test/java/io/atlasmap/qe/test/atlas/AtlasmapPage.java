package io.atlasmap.qe.test.atlas;

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
        $("#SourceMappingTestClass").waitUntil(Condition.visible,5000);
        $("#TargetMappingTestClass").waitUntil(Condition.appear,5000);
    }

    public void clickOn(String elementID) {
        $(By.id(elementID)).$("label").click();
    }


    public boolean checkWarning(String exceptionType, String fromType, String toType) {
        LOG.info("looking ...");
        $(".alert-warning").shouldBe(Condition.appears);
        for (String s : $$(".alert-warning").texts()) {
            if (s.equals("Conversion from '" + fromType + "' to '" + toType + "' can cause " + exceptionType)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWarningContainMessage(String containsMessage) {
        LOG.info("looking ...");
        $(".alert-warning").shouldBe(Condition.appears);
        for (String s : $$(".alert-warning").texts()) {
            if (s.contains(containsMessage)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDangerWarningContainMessage(String containsMessage) {
        LOG.info("looking ...");
        $(".alert-danger").shouldBe(Condition.appears);
        for (String s : $$(".alert-danger").texts()) {
            if (s.contains(containsMessage)) {
                return true;
            }
        }
        return false;
    }

    public void checkWarnings() {
        LOG.info("looking ...");
        $(".alert-warning").shouldNot(Condition.appears);
    }

    public void clickOnButtonByText(String elementName) {
        $$(By.tagName("button")).filter(Condition.text(elementName)).get(0).shouldBe(Condition.visible).click();
    }

    public void clickOnElementByText(String elementName, String text) {
        $$(By.tagName(elementName)).filter(Condition.text(text)).get(0).shouldBe(Condition.visible).click();
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
        SelenideElement e = $$(By.id(inputSelector)).filter(Condition.exactValue(def)).get(0);
        e.clear();
        e.setValue(inputValue);
    }

    public void setInputValueByClassAndDefaultValue(String inputSelector, String def, String inputValue) {
        SelenideElement e = $$(By.className(inputSelector)).filter(Condition.exactValue(def)).get(0);
        e.clear();
        e.setValue(inputValue);
    }

    public void setInputValueById(String inputId, String newValue) throws InterruptedException {
        $(By.id(inputId)).setValue(newValue);
        Thread.sleep(500);
        $(By.id(inputId)).sendKeys(Keys.ENTER);
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
        $(classSelector).shouldBe(Condition.visible);
        $(classSelector).click();
    }

    public void deleteCurrent() throws InterruptedException {
        $((".fa.fa-trash.link")).click();
        Thread.sleep(10100);
        $(".pull-right.btn.btn-primary").shouldBe(Condition.visible).isDisplayed();
        clickOnButtonByText("Remove");
    }

    public void clickOnWhileHolding(String id, String cmd) {

        SelenideElement e = $(By.id(id)).shouldBe(Condition.visible);
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
        WebElement dr = $$(By.id(drag)).get(1).shouldBe(Condition.visible);
        WebElement dro = $$(By.id(drop)).get(1).shouldBe(Condition.visible);
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
}
