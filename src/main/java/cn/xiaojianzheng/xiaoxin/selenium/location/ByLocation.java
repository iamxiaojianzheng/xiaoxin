package cn.xiaojianzheng.xiaoxin.selenium.location;

import cn.hutool.core.util.StrUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Quotes;

/**
 * For convenient positioning
 *
 * @author JIAHE
 * @since 1.0
 */
public interface ByLocation {

    static By name(String name) {
        return By.name(name);
    }

    static By xpath(String xpath) {
        return By.xpath(xpath);
    }

    static By linkText(String linkText) {
        return By.linkText(linkText);
    }

    static By partialLinkText(String partialLinkText) {
        return By.partialLinkText(partialLinkText);
    }

    static By id(String id) {
        return By.id(id);
    }

    static By cssSelector(String css) {
        return By.cssSelector(css);
    }

    static By className(String className) {
        return By.className(className);
    }

    /**
     * Format Xpath
     *
     * @param format 字符串模板
     * @param params 需替换的参数
     */
    static By xpathFormat(String format, String... params) {
        return xpath(StrUtil.format(format, (Object[]) params));
    }

    /**
     * html标签文本内容包含text
     *
     * @param tag  html标签，eg. [button, div, span...]
     * @param text 标签文本内容
     */
    static By containsText(String tag, String text) {
        String expression = ".//*/text()[contains(normalize-space(.), " + Quotes.escape(text) + ")]/parent::" + tag;
        return xpath(expression);
    }

    static By containsText(String text) {
        return containsText("*", text);
    }

    /**
     * 文本内容等于
     *
     * @param tag  html标签，eg. [button, div, span...]
     * @param text 标签文本内容
     */
    static By equalsText(String tag, String text) {
        String expression = ".//*/text()[normalize-space(.) = " + Quotes.escape(text) + "]/parent::" + tag;
        return xpath(expression);
    }

    static By equalsText(String text) {
        return equalsText("*", text);
    }

    /**
     * 通过属性名等于属性值定位元素
     *
     * @param tag       html标签，eg. [button, div, span...]
     * @param attribute 属性名 eg. [text(), class, value...]
     * @param text      属性值
     */
    static By equalsAttributeValue(String tag, String attribute, String text) {
        String format = "//%s[@%s='%s']";
        return xpathFormat(format, tag, attribute, text);
    }

    /**
     * 通过属性名包含属性值定位元素
     *
     * @param tag       html标签，eg. [button, div, span...]
     * @param attribute 属性名 eg. [text(), class, value...]
     * @param text      属性值
     */
    static By containsAttributeValue(String tag, String attribute, String text) {
        String format = "//%s[contains(@%s, '%s')]";
        return xpathFormat(format, tag, attribute, text);
    }

    /**
     * *[title=title]
     *
     * @param title title属性值
     */
    static By title(String title) {
        return equalsAttributeValue("*", "title", title);
    }

    /**
     * *[value=value]
     *
     * @param value value属性值
     */
    static By value(String value) {
        return equalsAttributeValue("*", "value", value);
    }

}
