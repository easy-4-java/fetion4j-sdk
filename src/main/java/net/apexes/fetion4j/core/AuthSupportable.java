/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */
package net.apexes.fetion4j.core;

/**
 * Callback interface invoked when the server requires captcha-based
 * authentication. Implementations present the captcha to the user and
 * use the supplied {@link AuthFeedback} to submit or cancel.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.AuthFeedback
 * @see net.apexes.fetion4j.core.Captcha
 */
public interface AuthSupportable {
    
    /**
     * 需要输入图形验证码。
     * 
     * @param captcha 包含验证图片等信息的对象。
     * @param feedback 验证回执。
     */
    void needAuth(Captcha captcha, AuthFeedback feedback);
    
}
