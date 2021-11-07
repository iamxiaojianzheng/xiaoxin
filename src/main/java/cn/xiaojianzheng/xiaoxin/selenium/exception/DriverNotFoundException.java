package cn.xiaojianzheng.xiaoxin.selenium.exception;

import java.io.Serializable;

/**
 * @author JIAHE
 * @since 1.0
 */
public class DriverNotFoundException extends RuntimeException implements Serializable {

    public DriverNotFoundException() {
        super();
    }

    public DriverNotFoundException(String message) {
        super(message);
    }

    public DriverNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverNotFoundException(Throwable cause) {
        super(cause);
    }

    protected DriverNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
