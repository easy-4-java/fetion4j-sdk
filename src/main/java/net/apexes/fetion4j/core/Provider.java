/*
 * Copyright (C) 2013, Apexes Network Technology. All rights reserved.
 *
 *       http://www.apexes.net
 *
 */
package net.apexes.fetion4j.core;

import net.apexes.fetion4j.core.util.XmlElement;

/**
 * Abstraction for reading cached system configuration and user information.
 * Implementations may persist data to the file system or a database.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.ProviderFactory
 * @see net.apexes.fetion4j.core.SystemConfig
 */
public interface Provider {
    
    /**
     * 
     * @return 
     */
    XmlElement readSystemConfig();
    
    /**
     * 
     * @return 
     */
    UserInfo readUserInfo();
    
}
