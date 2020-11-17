package com.cn.poker.common.util;

import java.io.Serializable;

/**
 * ClassName: HttpClientResult <br/>
 * Function: 封装httpClient响应结果. <br/>
 * date: 2018年10月25日 上午10:37:12 <br/>
 * 
 * @author fate
 * @version
 * @since JDK 1.7
 */
public class HttpClientResult implements Serializable {

	private static final long	serialVersionUID	= -4439561012441236453L;

	/**
	 * 响应状态码
	 */
	private int					code;

	/**
	 * 响应数据
	 */
	private String				content;

	public HttpClientResult() {
	}

	public HttpClientResult(int code) {
		this.code = code;
	}

	public HttpClientResult(String content) {
		this.content = content;
	}

	public HttpClientResult(int code, String content) {
		this.code = code;
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "HttpClientResult [code=" + code + ", content=" + content + "]";
	}

}
