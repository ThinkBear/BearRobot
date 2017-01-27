package cn.thinkbear.robot.vo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import cn.thinkbear.robot.core.IHttp;
import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.utils.QQException.QQErrorCode;

public class QQHttpRequest {
	private String url;
	private RequestMethod requestMethod;
	private List<NameValuePair> fromData = null;

	private Map<String, String> headerData = null;

	private int connectTimeOut = IHttp.CONNECT_TIMEOUT;
	private int socketTimeOut = IHttp.SOCKET_TIMEOUT;

	public QQHttpRequest(String url, RequestMethod requestMethod) {
		this.url = url;
		this.requestMethod = requestMethod;
		this.fromData = new ArrayList<NameValuePair>();
		this.headerData = new HashMap<String, String>();

	}

	public URI getURI() throws QQException {

		try {
			URIBuilder builder = new URIBuilder(this.url);
			if (this.fromData.size() > 0) {
				builder.addParameters(this.fromData);
			}
			return builder.build();
		} catch (URISyntaxException e) {
			throw new QQException(QQErrorCode.URI_ERROR, e.getMessage());
		}

	}

	public void addFromData(String name, String value) {
		this.fromData.add(new BasicNameValuePair(name, value));
	}

	public void addHeaderData(String name, String value) {
		this.headerData.put(name, value);
	}

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public int getSocketTimeOut() {
		return socketTimeOut;
	}

	public void setSocketTimeOut(int socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}

	public String getUrl() {
		return url;
	}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public List<NameValuePair> getFromData() {
		return fromData;
	}

	public Map<String, String> getHeaderData() {
		return headerData;
	}

	public enum RequestMethod {
		POST, GET
	}
}
