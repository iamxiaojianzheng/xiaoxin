package cn.xiaojianzheng.xiaoxin.selenium.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
class BrowserVersionUtilTest {

    @Test
    void chrome() {
        log.info(BrowserVersionUtil.chrome());
    }

    @Test
    void firefox() {
        log.info(BrowserVersionUtil.firefox());
    }

    @Test
    void edge() {
        log.info(BrowserVersionUtil.edge());
    }
}