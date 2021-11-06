package cn.xiaojianzheng.xiaoxin.selenium.driver;

/**
 * 表示不接受参数且不返回结果的操作。与大多数其他功能接口不同，ConditionDo用于流程控制。
 * <p>
 * 这是一个<a href="package-summary.html">功能接口</a>，其功能方法是执行{@link #done()}.
 *
 * @author JIAHE
 * @since 1.0
 */
public interface ConditionDo {
    /**
     * 直接执行操作
     */
    void done();
}
