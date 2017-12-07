package io.atlasmap.qe.test.atlas;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;

import com.codeborne.selenide.Condition;

import io.atlasmap.qe.test.atlas.utils.Constants;

public class AtlasmapPage {

    public static final String TEST_CLASS = "SourceMappingTestClass";

    public void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/mmelko/Downloads/chromedriver");
        System.setProperty("selenide.browser", "Chrome");

        open(Constants.UI_INDEX_PATH);
        $(By.id("SourceMappingTestClass")).shouldBe(Condition.appear);
        $(By.id("TargetMappingTestClass")).shouldBe(Condition.appear);
    }

    public void clickOn(String elementID) {
        $(By.id(elementID)).$("label").click();
    }
}
