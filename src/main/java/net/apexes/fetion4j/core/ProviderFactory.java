/*
 * Copyright (C) 2013, Apexes Network Technology. All rights reserved.
 *
 *       http://www.apexes.net
 *
 */
package net.apexes.fetion4j.core;

/**
 * Factory interface for creating {@link Provider} instances keyed by mobile number.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.Provider
 * @see net.apexes.fetion4j.core.client.SimpleProviderFactory
 */
public interface ProviderFactory {
    
    /**
     * 
     * @param mobileNo
     * @return 
     */
    Provider create(long mobileNo);
    
}
