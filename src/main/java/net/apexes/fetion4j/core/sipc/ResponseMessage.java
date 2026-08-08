/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */

package net.apexes.fetion4j.core.sipc;

/**
 * SIPC response message received from the server. Carries a numeric status
 * code, a status message, and a reference back to the originating request.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.sipc.RequestMessage
 * @see net.apexes.fetion4j.core.sipc.Sipc
 */
public class ResponseMessage extends SipcMessage {
    
    /**
	 * 回复状态代码
	 */
	private int status;
	
	/**
	 * 回复状态说明
	 */
	private String statusMessage;
    
    /**
     * 该回复对应的请求
     */
    private RequestMessage requestMessage;
    
    /**
     * 
     * @param status
     * @param statusMessage 
     */
    public ResponseMessage(int status, String statusMessage) {
        this.status = status;
        this.statusMessage = statusMessage; 
    }
    
    public int getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public RequestMessage getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(RequestMessage requestMessage) {
        this.requestMessage = requestMessage;
    }
    
    @Override
    protected String getHeadline() {
        return Sipc.SIPC_VERSION + " " + getStatus() + " " + getStatusMessage();
    }

}
