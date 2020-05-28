package io.atlasmap.qe.test.atlas.utils;

import org.openqa.selenium.By;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ByUtils {

    public static By dataTestId(String dataTestId) {
        return By.cssSelector(String.format("*[data-testid=\"%s\"]", dataTestId));
    }

    public static By dataTestIdStartsWith(String dataTestId) {
        return By.cssSelector(String.format("*[data-testid^=\"%s\"]", dataTestId));
    }
}
