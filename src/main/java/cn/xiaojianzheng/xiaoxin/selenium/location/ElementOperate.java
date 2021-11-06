package cn.xiaojianzheng.xiaoxin.selenium.location;

import lombok.Data;
import org.openqa.selenium.By;

import java.time.Duration;

/**
 * @author JIAHE
 * @since 1.0
 */
@Data
public class ElementOperate {

    // base
    private By by;
    private Duration waitTime;
    private Duration pollTime;
    private boolean noWait;

    // exception
    private String describe;

    // click
    private int clickTimes;
    private boolean actionClick = false;
    private boolean rightClick = false;
    private boolean moveToClick = false;
    private boolean doubleClick = false;
    private boolean jsClick = false;

    // input
    private boolean removeReadonly = false;
    private String inputText;
    private boolean clear = false;

    // alert
    private boolean acceptAlert = false;

    public static LocationBuilder location() {
        return new LocationBuilder(new ElementOperate());
    }

    public static class LocationBuilder {

        private final ElementOperate elementOperate;

        public LocationBuilder(final ElementOperate elementOperate) {
            this.elementOperate = elementOperate;
        }

        public ElementOperate build() {
            return elementOperate;
        }

        public LocationBuilder by(By by) {
            this.elementOperate.setBy(by);
            return this;
        }

        public LocationBuilder waitTime(Duration waitTime) {
            this.elementOperate.setWaitTime(waitTime);
            return this;
        }

        public LocationBuilder pollTime(Duration pollTime) {
            this.elementOperate.setPollTime(pollTime);
            return this;
        }

        /**
         * 查找元素不等待
         */
        public LocationBuilder noWait() {
            this.elementOperate.setNoWait(true);
            return this;
        }

        /**
         * 用于描述当前操作的含义
         *
         * @param describe some text
         * @return this
         */
        public LocationBuilder as(String describe) {
            this.elementOperate.setDescribe(describe);
            return this;
        }

        /**
         * 删除 readonly 属性
         *
         * @return this
         */
        public LocationBuilder removeReadonly() {
            this.elementOperate.setRemoveReadonly(true);
            return this;
        }

        /**
         * 输入的内容
         */
        public LocationBuilder inputText(String inputText) {
            this.elementOperate.setInputText(inputText);
            return this;
        }

        /**
         * 清空输入框
         */
        public LocationBuilder clear() {
            this.elementOperate.setClear(true);
            return this;
        }

        /**
         * 确定alert
         */
        public LocationBuilder dismissAlert() {
            this.elementOperate.setAcceptAlert(false);
            return this;
        }

        /**
         * 取消alert
         */
        public LocationBuilder acceptAlert() {
            this.elementOperate.setAcceptAlert(true);
            return this;
        }

        /**
         * 点击次数
         */
        public LocationBuilder clickTimes(int clickTimes) {
            this.elementOperate.setClickTimes(Math.max(clickTimes, 1));
            return this;
        }

        /**
         * action点击
         */
        public LocationBuilder actionClick() {
            this.elementOperate.resetClick();
            this.elementOperate.setActionClick(true);
            return this;
        }

        /**
         * 右击
         */
        public LocationBuilder rightClick() {
            this.elementOperate.resetClick();
            this.elementOperate.setRightClick(true);
            return this;
        }

        /**
         * 移动点击
         */
        public LocationBuilder moveToClick() {
            this.elementOperate.resetClick();
            this.elementOperate.setMoveToClick(true);
            return this;
        }

        /**
         * 双击
         */
        public LocationBuilder doubleClick() {
            this.elementOperate.resetClick();
            this.elementOperate.setDoubleClick(true);
            return this;
        }

        /**
         * js点击
         */
        public LocationBuilder jsClick() {
            this.elementOperate.resetClick();
            this.elementOperate.setJsClick(true);
            return this;
        }

        /**
         * {@link By.ByName }
         */
        public LocationBuilder name(String name) {
            this.elementOperate.setBy(ByLocation.name(name));
            return this;
        }

        /**
         * {@link By.ByXPath }
         */
        public LocationBuilder xpath(String xpath) {
            this.elementOperate.setBy(ByLocation.xpath(xpath));
            return this;
        }

        /**
         * {@link By.ByLinkText }
         */
        public LocationBuilder linkText(String linkText) {
            this.elementOperate.setBy(ByLocation.linkText(linkText));
            return this;
        }

        /**
         * {@link By.ByPartialLinkText }
         */
        public LocationBuilder partialLinkText(String partialLinkText) {
            this.elementOperate.setBy(ByLocation.partialLinkText(partialLinkText));
            return this;
        }

        /**
         * {@link By.ById }
         */
        public LocationBuilder id(String id) {
            this.elementOperate.setBy(ByLocation.id(id));
            return this;
        }

        /**
         * {@link By.ByCssSelector }
         */
        public LocationBuilder cssSelector(String css) {
            this.elementOperate.setBy(ByLocation.cssSelector(css));
            return this;
        }

        /**
         * {@link By.ByClassName }
         */
        public LocationBuilder className(String className) {
            this.elementOperate.setBy(ByLocation.className(className));
            return this;
        }

        /**
         * format xpath
         */
        public LocationBuilder xpathFormat(String format, String... params) {
            this.elementOperate.setBy(ByLocation.xpathFormat(format, params));
            return this;
        }

        /**
         * 根据元素内容定位，包含关系。
         *
         * @param tag  标签名称
         * @param text 标签的内容
         */
        public LocationBuilder containsText(String tag, String text) {
            this.elementOperate.setBy(ByLocation.containsText(tag, text));
            return this;
        }

        /**
         * 根据元素内容定位，包含关系。
         *
         * @param text 标签的内容
         */
        public LocationBuilder containsText(String text) {
            this.elementOperate.setBy(ByLocation.containsText(text));
            return this;
        }

        /**
         * 根据元素内容定位，等于关系。
         *
         * @param tag  标签名称
         * @param text 标签的内容
         */
        public LocationBuilder equalsText(String tag, String text) {
            this.elementOperate.setBy(ByLocation.equalsText(tag, text));
            return this;
        }

        /**
         * 根据元素内容定位，等于关系。
         *
         * @param text 标签的内容
         */
        public LocationBuilder equalsText(String text) {
            this.elementOperate.setBy(ByLocation.equalsText(text));
            return this;
        }

        /**
         * 根据<b>标签名</b>、<b>属性名</b>、<b>属性值</b>定位元素，属性名和属性值是等于关系。
         *
         * @param tag       标签名称
         * @param attribute 属性名
         * @param value     属性值
         */
        public LocationBuilder equalsAttributeValue(String tag, String attribute, String value) {
            this.elementOperate.setBy(ByLocation.equalsAttributeValue(tag, attribute, value));
            return this;
        }

        /**
         * 根据<b>标签名</b>、<b>属性名</b>、<b>属性值</b>定位元素，属性名和属性值是包含关系。
         *
         * @param tag       标签名称
         * @param attribute 属性名
         * @param value     属性值
         */
        public LocationBuilder containsAttributeValue(String tag, String attribute, String value) {
            this.elementOperate.setBy(ByLocation.containsAttributeValue(tag, attribute, value));
            return this;
        }

        /**
         * 根据title属性定位元素
         *
         * @param title title
         */
        public LocationBuilder title(String title) {
            this.elementOperate.setBy(ByLocation.title(title));
            return this;
        }

        /**
         * 根据value属性定位元素
         *
         * @param value value对应的值
         */
        public LocationBuilder value(String value) {
            this.elementOperate.setBy(ByLocation.value(value));
            return this;
        }

    }

    private void resetClick() {
        actionClick = false;
        rightClick = false;
        moveToClick = false;
        doubleClick = false;
        jsClick = false;
    }

}
