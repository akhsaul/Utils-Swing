package me.akhsaul.swing;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import javax.swing.*;

public class UtilTest {
    private final static Logger LOG = me.akhsaul.common.Util.logger(()-> null);
    @Test
    public void changeTheme() {
        var frame = new KFrame("Testing");

        try {
            for (Theme value : Theme.values()) {
                for (Theme.Mode mode : Theme.Mode.values()) {
                    Thread.sleep(3000);
                    SwingUtil.changeTheme(value, frame, mode);
                }
            }
        }catch (Throwable e){
            LOG.error("Theme, "+ UIManager.getLookAndFeel().getName());
        }
    }
}
