/*
 * Copyright (C) 2013, Apexes Network Technology. All rights reserved.
 *
 *       http://www.apexes.net
 *
 */
package net.apexes.fetion4j.core.client;

import net.apexes.fetion4j.core.sipc.SipcMessage;

/**
 * Abstract base class for SIPC dialogues that can receive incoming messages.
 * Extends {@link Activity} with a message-reception hook.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.client.Activity
 * @see net.apexes.fetion4j.core.sipc.SipcMessage
 */
public abstract class Dialogue extends Activity {
    
    protected Dialogue(FetionContext context, Controller controller, int callId) {
        super(context, controller, callId);
    }
    
    /**
     * 
     * @param message 
     */
    public abstract void receive(SipcMessage message);
    
}
