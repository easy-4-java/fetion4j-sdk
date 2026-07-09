/*
 * Copyright (C) 2013, Apexes Network Technology. All rights reserved.
 *
 *       http://www.apexes.net
 *
 */
package net.apexes.fetion4j.core.client;

import net.apexes.fetion4j.core.sipc.SipcMessage;

/**
 *
 * @author HeDYn <hedyn@foxmail.com>
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
