/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */

package net.apexes.fetion4j.core.sipc;

/**
 * SIPC request message sent from the client to the server. Carries an
 * acceptor (destination host) and method, and produces a headline in the
 * format {@code METHOD acceptor SIP-C/4.0}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.sipc.SipcMessage
 * @see net.apexes.fetion4j.core.sipc.ResponseMessage
 */
public class RequestMessage extends SipcMessage {
    
    private String acceptor;
    
    public RequestMessage(String method) {
        this(method, "fetion.com.cn");
    }
    
    public RequestMessage(String method, String acceptor) {
        this.acceptor = acceptor; 
        setMethod(method);
    }
    
    public String getAcceptor() {
        return acceptor;
    }
    
    @Override
    protected String getHeadline() {
        return getMethod() + " " + getAcceptor() + " " + Sipc.SIPC_VERSION;
    }
}
