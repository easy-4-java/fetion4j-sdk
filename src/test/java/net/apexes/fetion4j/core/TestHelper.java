package net.apexes.fetion4j.core;

import net.apexes.fetion4j.core.sipc.SipcMessage;

/**
 * Test helper providing factory methods for package-private constructors.
 */
public class TestHelper {

    public static Account createAccount(long mobileNo, int userId, String uri) {
        return new Account(mobileNo, userId, uri);
    }

    public static LogHandler createNoopLogHandler() {
        return new LogHandler() {
            public void transmit(SipcMessage m) {}
            public void receive(SipcMessage m) {}
            public void error(Class<?> c, String msg, Throwable t) {}
            public void debug(Class<?> c, String msg) {}
            public void info(Class<?> c, String msg) {}
            public void warn(Class<?> c, String msg) {}
        };
    }
}
