package io.atlasmap.qe.test.atlas;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

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
        $$(By.tagName("button")).filter(Condition.text(elementName)).get(0).click();
    }

    public void selectTransformation(String transformation){
        $$(By.tagName("select")).filter(Condition.exactValue("Append")).get(0).selectOption(transformation);
    }

    public void setInputValueByClass(String inputSelector, String inputValue) {
        $(By.className(inputSelector)).setValue(inputValue);
    }
}
