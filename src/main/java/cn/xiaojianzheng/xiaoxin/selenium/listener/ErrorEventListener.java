package cn.xiaojianzheng.xiaoxin.selenium.listener;

import cn.hutool.core.util.StrUtil;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * log event listener for error
 *
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class ErrorEventListener implements WebDriverListener {
    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        for (Object arg : args) {
            if (arg instanceof ElementOperate) {
                ElementOperate elementOperate = (ElementOperate) arg;
                String describe = elementOperate.getDescribe();
                if (StrUtil.isNotBlank(describe)) {
                    if (e.getTargetException() instanceof NoSuchElementException) {
                        throw new NoSuchWindowException(describe);
                    }
                }
            }
        }
    }

}
