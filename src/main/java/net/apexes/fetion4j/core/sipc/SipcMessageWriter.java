/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */

package net.apexes.fetion4j.core.sipc;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Writes SIPC messages to an {@link java.io.OutputStream} in the
 * standard SIPC wire format.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.sipc.SipcMessageReader
 * @see net.apexes.fetion4j.core.sipc.SipcMessage
 */
public class SipcMessageWriter {
    
    private OutputStream out;
    
    public SipcMessageWriter(OutputStream out) {
        this.out = out;
    }
    
    public void close() throws IOException {
        out.close();
    }
    
    public void write(SipcMessage message) throws IOException {
        out.write(message.getText().getBytes());
    }
}
