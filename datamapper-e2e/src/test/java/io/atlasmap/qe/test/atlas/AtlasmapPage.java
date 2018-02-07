package io.atlasmap.qe.test.atlas;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.Condition;

import io.atlasmap.qe.test.atlas.utils.Constants;



public class AtlasmapPage {

    public static final String TEST_CLASS = "SourceMappingTestClass";
    private static final Logger LOG = LogManager.getLogger(AtlasmapPage.class);


    public void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/mmelko/Downloads/chromedriver");
        System.setProperty("selenide.browser", "Chrome");

        open(Constants.UI_INDEX_PATH);
        $("#SourceMappingTestClass").shouldBe(Condition.appear);
        $("#TargetMappingTestClass").shouldBe(Condition.appear);
    }

    public void clickOn(String elementID) {
        $(By.id(elementID)).$("label").click();
    }


    public boolean checkWarning(String exceptionType, String fromType, String toType) {
        LOG.info("looking ...");
        $(".alert-warning").shouldBe(Condition.appears);
        for (String s : $$(".alert-warning").texts()) {
            if (s.equals("Conversion from '"+fromType+"' to '"+toType+"' can cause "+exceptionType)) {
                return true;
            }
        }
        return  false;
    }

    public void checkWarnings() {
        LOG.info("looking ...");
        $(".alert-warning").shouldNot(Condition.appears);
    }

    public void clickOnButtonByText(String elementName) {
        $$(By.tagName("button")).filter(Condition.text(elementName)).get(0).shouldBe(Condition.visible).click();
    }

    public void selectTransformation(String transformation, String deafaultValue){
        $$(By.tagName("select")).filter(Condition.exactValue(deafaultValue)).get(0).selectOption(transformation);
    }

    public void setInputValueByClass(String inputSelector, String inputValue) {
        $(By.className(inputSelector)).setValue(inputValue);
    }

    public void setInputValueByClassAndDefaultValue(String inputSelector, String def, String inputValue){
        $$(By.className(inputSelector)).filter(Condition.exactValue(def)).get(0).setValue(inputValue);
    }
    public void setInputValueById(String inputId,String newValue) throws InterruptedException {
        $(By.id(inputId)).setValue(newValue);
        Thread.sleep(500);
        $(By.id(inputId)).sendKeys(Keys.ENTER);
    }

    public void selectAction(String action) {
        $("#select-action").selectOption(action);
    }
    public void selectSeparator(String action) {
        $("#select-separator").selectOption(action);
    }

    public void openMappingDetails() {
        $((".fa.fa-plus.link")).click();
    }

    public  void deleteCurrent() throws InterruptedException {
        $((".fa.fa-trash.link")).click();
        Thread.sleep(10100);
        $(".pull-right.btn.btn-primary").shouldBe(Condition.visible).isDisplayed();
        clickOnButtonByText("Remove");
    }
}
