/*
 * Copyright (C) 2013, Apexes Network Technology. All rights reserved.
 *
 *       http://www.apexes.net
 *
 */
package net.apexes.fetion4j.core;

/**
 *
 * @author HeDYn <hedyn@foxmail.com>
 */
public interface ProviderFactory {
    
    /**
     * 
     * @param mobileNo
     * @return 
     */
    Provider create(long mobileNo);
    
}
