package cn.xiaojianzheng.xiaoxin.selenium.listener;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * log event listener for marking border of element
 *
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class FocusColorListener implements WebDriverListener {

    private final String BORDER_STYLE = "'3px solid red'";

    private final List<WebElement> previousElements = new ArrayList<>();

    private By preLocator;

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        border(driver, locator, Collections.singletonList(result));
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        border(driver, locator, result);
    }

    private void border(WebDriver driver, By locator, List<WebElement> result) {
        if (preLocator != locator) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            if (CollectionUtil.isNotEmpty(previousElements)) {
                javascriptExecutor.executeScript("arguments[0].style.border = 'none'", previousElements.toArray());
                previousElements.clear();
            }
            javascriptExecutor.executeScript("arguments[0].style.border = " + BORDER_STYLE, result.toArray());
            previousElements.addAll(result);
            preLocator = locator;
        }
    }

}
