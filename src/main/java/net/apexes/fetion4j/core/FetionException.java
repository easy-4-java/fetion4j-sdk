/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */
package net.apexes.fetion4j.core;

/**
 * Base checked exception for all Fetion SDK errors. Carries an optional
 * cause chain for diagnostic purposes.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.client.transfer.TransferException
 */
public class FetionException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public FetionException(String msg, Throwable e) {
        super(msg, e);
    }

    public FetionException(Throwable e) {
        super(e);
    }

    public FetionException(String msg) {
        super(msg);
    }

    protected FetionException() {
    }
}
