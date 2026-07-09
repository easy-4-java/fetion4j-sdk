/*
 * Copyright (C) 2013, Apexes Network Technology. All rights reserved.
 *
 *       http://www.apexes.net
 *
 */
package net.apexes.fetion4j.core;

import net.apexes.fetion4j.core.util.XmlElement;

/**
 *
 * @author HeDYn <hedyn@foxmail.com>
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
