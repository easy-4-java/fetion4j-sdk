/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */
package net.apexes.fetion4j.core.client.transfer;

/**
 * Checked exception thrown when a network transport operation fails.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.client.transfer.Transfer
 */
public class TransferException extends Exception {

    public TransferException(Throwable cause) {
        super(cause);
    }

    public TransferException(String message) {
        super(message);
    }
    
    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferException() {
        super();
    }
}
