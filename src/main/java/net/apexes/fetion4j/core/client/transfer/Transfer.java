/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */
package net.apexes.fetion4j.core.client.transfer;

import java.io.IOException;
import java.text.ParseException;

import net.apexes.fetion4j.core.sipc.SipcMessage;

/**
 * Abstraction for the underlying network transport used to exchange SIPC
 * messages with the server.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.client.transfer.TcpTransfer
 * @see net.apexes.fetion4j.core.sipc.SipcMessage
 */
public interface Transfer {
    
    /**
     * 启动传输
     * @throws TransferException
     */
	public void startTransfer() throws TransferException;
	
    /**
     * 停止传输
     * 
     * @throws TransferException 
     */
	public void stopTransfer() throws TransferException;
    
    /**
     * 读取一个SIPC消息
     * 
     * @return 
     * @throws TransferException
     */
    public SipcMessage read() throws IOException, ParseException;
    
    /**
     * 发送一个SIPC消息
     * 
     * @param message 
     * @throws TransferException
     */
    public void write(SipcMessage message) throws IOException;
    
    /**
     * 如果传输对象已经关闭则返回 true
     * @return 
     */
    public boolean isClosed();
    
	/**
	 * 返回这个传输对象的名字
     * 
	 * @return
	 */
	public String getTransferName();
    
}
