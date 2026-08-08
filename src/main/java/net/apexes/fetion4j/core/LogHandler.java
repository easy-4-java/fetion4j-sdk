package net.apexes.fetion4j.core;

import net.apexes.fetion4j.core.sipc.SipcMessage;

/**
 * Pluggable logging interface for SIPC message traffic and SDK diagnostics.
 * Implementations may route output to SLF4J, stdout, or any other backend.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.sipc.SipcMessage
 */
public interface LogHandler {
    
    void transmit(SipcMessage message);
    
    void receive(SipcMessage message);
    
    void error(Class<?> c, String msg, Throwable t);
    
    void debug(Class<?> c, String msg);
    
    void info(Class<?> c, String msg);
    
    void warn(Class<?> c, String msg);
    
}
