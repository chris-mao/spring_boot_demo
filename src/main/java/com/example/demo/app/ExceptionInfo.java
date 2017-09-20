/**
 * 
 */
package com.example.demo.app;

/**
 * com.example.demo.app ExceptionInfo
 * 
 * 对异常进行封装，确保返回的都是同一结构的数据对象，方便前端处理
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class ExceptionInfo {
	
	public final static Integer OK = 0;
	
	/**
	 * 错误代码
	 */
	public String code;
	
	/**
	 * 错误信息
	 */
	public String message;
	
	/**
	 * 用于给开发人员查看的更加详细的错误描述
	 */
	public String developerMessage;
	
	/**
	 * 请求的URL
	 */
	public String url;
	
	/**
	 * HTTP请求状态码
	 */
	public String httpStatus;
	
	/**
	 * 出现错误的对象
	 */
	public Object errorData;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the errorData
	 */
	public Object getErrorData() {
		return errorData;
	}

	/**
	 * @param errorData the errorData to set
	 */
	public void setErrorData(Object errorData) {
		this.errorData = errorData;
	}

	/**
	 * @return the httpStatus
	 */
	public String getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus the httpStatus to set
	 */
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * @return the developerMessage
	 */
	public String getDeveloperMessage() {
		return developerMessage;
	}

	/**
	 * @param developerMessage the developerMessage to set
	 */
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExceptionInfo [httpStatus=" + httpStatus + ", code=" + code + ", message=" + message
				+ ", developerMessage=" + developerMessage + ", url=" + url + ", errorData=" + errorData + "]";
	}

}
