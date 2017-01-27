package cn.thinkbear.robot.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import cn.thinkbear.robot.utils.QQException.QQErrorCode;

public class QQException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QQErrorCode errorCode = null;

	public QQErrorCode getErrorCode() {
		return errorCode;
	}

	public QQException(QQErrorCode errorCode, Throwable e) {
		super(errorCode.toString(), e);
		this.errorCode = errorCode;
	}

	public QQException(QQErrorCode errorCode) {
		super(errorCode.toString());
		this.errorCode = errorCode;
	}

	public QQException(QQErrorCode errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "发生错误：" + super.getMessage()+"("+this.errorCode+")";
	}

	public static QQException getException(Exception e) {
		if (e instanceof QQException) {
			return (QQException) e;
		} else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
			return new QQException(QQErrorCode.IO_TIMEOUT);
		} else if (e instanceof IOException) {
			return new QQException(QQErrorCode.IO_ERROR);
		} else {
			return new QQException(QQErrorCode.UNKNOWN_ERROR);
		}
	}

	public enum QQErrorCode {
		
		
		
		/**
		 * 网络错误
		 */
		IO_ERROR,
		/**
		 * 网络超时
		 */
		IO_TIMEOUT,
		
		
		/**
		 * 错误的状态码
		 */
		ERROR_HTTP_STATUS,
		/**
		 * GSON解析出错
		 */
		GSON_ERROR,
		/**
		 * 未知的错误
		 */
		UNKNOWN_ERROR,
		/**
		 * 返回码非0
		 */
		RETCODE_ERROR,
		/**
		 * 错误的URI
		 */
		URI_ERROR,
		/**
		 * 未知的请求方式
		 */
		UNKNOW_REQUEST,
		/**
		 * 头像不存在
		 */
		FACE_EMPTY
		
	}

}
