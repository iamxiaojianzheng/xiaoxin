package cn.xiaojianzheng.xiaoxin.selenium.base;

import cn.xiaojianzheng.xiaoxin.selenium.driver.ConditionDo;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Conditional flow control
 *
 * @author JIAHE
 * @since 1.0
 */
public interface UntilCondition<E> {

    /**
     * 重复将此实例的输入值应用于给定函数，直到发生以下情况之一：
     * <ol>
     * <li>该函数既不返回 null 也不返回 false</li>
     * <li>该函数抛出一个未忽略的异常</li>
     * <li>超时到期</li>
     * <li>当前线程被中断</li>
     * </ol>
     *
     * @param expectedCondition 预期条件表达式
     * @param <T>               函数的预期返回类型
     * @return This driver focused on the given window
     */
    <T> E until(ExpectedCondition<T> expectedCondition);

    /**
     * 重复将此实例的输入值应用于给定函数，直到发生以下情况之一：
     *
     * @param expectedCondition 预期条件表达式
     * @param <T>               函数的预期返回类型
     * @return 如果函数在超时到期之前返回了不同于 null 或 false 的内容，则该函数的返回值。
     */
    <T> T untilReturn(ExpectedCondition<T> expectedCondition);

    /**
     * 如果条件成立，则执行后续流程。
     *
     * @param expectedCondition 预期条件表达式
     * @param ifTrue            条件成立下执行{@code ifTrue.done()}
     * @param <T>               函数的预期返回类型
     */
    <T> E untilDoIfTrue(ExpectedCondition<T> expectedCondition, ConditionDo ifTrue);

    /**
     * 条件成立与否，都会执行后续流程。
     *
     * @param expectedCondition 预期条件表达式
     * @param ifTrue            条件成立下执行{@code ifTrue.done()}，否则执行{@code ifFalse.done()}
     * @param <T>               函数的预期返回类型
     */
    <T> E untilDo(ExpectedCondition<T> expectedCondition, ConditionDo ifTrue, ConditionDo ifFalse);

    /**
     * 重复将此实例的输入值应用于给定函数，直到发生以下情况之一：
     * 如果条件成立，则执行action操作。<b>忽略异常</b>
     *
     * @param expectedCondition 预期条件表达式
     * @param <T>               函数的预期返回类型
     */
    <T> void untilDo(ExpectedCondition<T> expectedCondition, Consumer<T> action);

    /**
     * 重复将此实例的输入值应用于给定函数，直到发生以下情况之一：
     * 如果条件成立，则执行action操作。<b>忽略异常</b>
     *
     * @param expectedCondition 预期条件表达式
     * @param <V>               函数的预期返回类型
     * @return 如果函数在超时到期之前返回了不同于 null 或 false 的内容，则该函数的返回值。
     */
    <T, V> V untilDoAndReturn(ExpectedCondition<T> expectedCondition, Function<? super T, V> action);

}
