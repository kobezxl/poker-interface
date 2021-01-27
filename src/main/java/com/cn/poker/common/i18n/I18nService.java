/**  
 * Project Name:xycloud-common-core  
 * File Name:I18n.java  
 * Package Name:com.xycloud.core.i18n  
 * Author : ourblue
 * Date:2019年5月22日上午10:42:20  
 * @version V1.0
 *
 * Copyright (c) 至臻天龙 All Rights Reserved.
 * Company:深圳市至臻天龙
 */
package com.cn.poker.common.i18n;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * the class is i18
 * 
 * @author ourblue
 */
@Component
public class I18nService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private HttpServletRequest request;
	

	
	// 根据code获取国际化内容
	public String msg(String code) {
		return messageSource.getMessage( code, null, getCurrentLocale() );
	}

	// get client language
	public Locale getCurrentLocale() {
		String language = request.getHeader( "pf_language" );
		return StringUtils.isEmpty( language ) ? Locale.getDefault() : PfLanguage.getLocale( language );
	}

	// language enum
	private enum PfLanguage {
		en_US(Locale.US), zh_CN(Locale.SIMPLIFIED_CHINESE);

		private Locale locale;

		PfLanguage(Locale locale) {
			this.locale = locale;
		}

		private static Locale getLocale(String key) {
			Locale locale = Locale.getDefault();
			for (PfLanguage ele : PfLanguage.values()) {
				if (key.equals( ele.toString() )) {
					locale = ele.locale;
					break;
				}
			}
			return locale;
		}
	}



}