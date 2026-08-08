/*
 * Copyright (C) 2013, Apexes Network Technology. All rights reserved.
 *
 *       http://www.apexes.net
 *
 */
package net.apexes.fetion4j.core;

import java.io.IOException;

/**
 * Callback interface for authentication feedback, allowing callers to submit
 * a captcha code, cancel the operation, or request a new captcha image.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.Captcha
 * @see net.apexes.fetion4j.core.AuthSupportable
 */
public interface AuthFeedback {
    
    /**
     * 提交验证码。
     * 
     * @param captchaCode 
     */
    void submit(String captchaCode);
    
    /**
     * 取消操作。
     */
    void cancel();
    
    /**
     * 换一个图形验证码。
     * @return 
     * @throws IOException 
     */
    Captcha tryAgain() throws IOException;
    
}
