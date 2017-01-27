package cn.thinkbear.robot.core;

import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;

import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.vo.QQHttpRequest;

public class BaseHttp implements IHttp, AutoCloseable {

	private CloseableHttpClient httpClient = null;
	private CookieStore cookieStore = null;
	private Gson gson = null;
	
	public Gson getGson() {
		return this.gson;
	}
	
	public CookieStore getCookieStore(){
		return this.cookieStore;
	}
	
	public BaseHttp() {
		this.cookieStore = new BasicCookieStore();
		this.init();
	}
	
	public BaseHttp(CookieStore cookieStore){
		this.cookieStore = cookieStore;
		this.init();
	}
	
	private void init(){
		this.httpClient = HttpClientBuilder.create().setDefaultCookieStore(this.cookieStore)
				.setDefaultRequestConfig(this.getBaseRequestConfig(IHttp.CONNECT_TIMEOUT,IHttp.SOCKET_TIMEOUT)).build();
		this.gson = new Gson();
	}
	
	/**
	 * 通过name 取得Cookie对象
	 * @param name
	 * @return
	 */
	public Cookie getCookie(String name){	
		List<Cookie> all = this.cookieStore.getCookies();
		for (Cookie cookie : all) {
			if(cookie.getName().equals(name)){
				return cookie;
			}
		}
		return null;
		
	}
	
	/**
	 * 通过name 取得Cookie的value值
	 * @param name 
	 * @return
	 */
	public String getCookieByValue(String name){
		Cookie cookie = this.getCookie(name);
		return cookie != null ? cookie.getValue() : null;
	}
	
	@Override
	public void close() throws QQException,Exception{
		this.httpClient.close();
	}

	@Override
	public CloseableHttpResponse getHttpResponse(QQHttpRequest request)
			throws QQException, Exception {
		CloseableHttpResponse httpResponse = null;
		if(request.getRequestMethod() == QQHttpRequest.RequestMethod.GET){
			HttpGet httpGet = new HttpGet(request.getURI());
			this.setRequestPara(httpGet, request);
			httpResponse = this.httpClient.execute(httpGet);
		}else if(request.getRequestMethod() == QQHttpRequest.RequestMethod.POST){
			HttpPost httpPost = new HttpPost(request.getUrl());
			httpPost.setEntity(new UrlEncodedFormEntity(request.getFromData(),"UTF-8"));
			this.setRequestPara(httpPost, request);
			httpResponse = this.httpClient.execute(httpPost);
		}else{
			throw new QQException(QQException.QQErrorCode.UNKNOW_REQUEST,"未知的请求方式");
		}
		

		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new QQException(QQException.QQErrorCode.ERROR_HTTP_STATUS);
		}

		return httpResponse;
	}
	
	/**
	 * 请求参数设置
	 * @param httpRequestBase 请求对象
	 * @param request 相关设置信息
	 */
	private void setRequestPara(HttpRequestBase httpRequestBase,QQHttpRequest request){
		/**
		 * 设置超时
		 */
		int connectTimeOut = request.getConnectTimeOut();
		int socketTimeOut = request.getSocketTimeOut();
		if(connectTimeOut !=0 && socketTimeOut !=0){
			httpRequestBase.setConfig(this.getBaseRequestConfig(connectTimeOut, socketTimeOut));
		}
		
		
		/**
		 * 设置请求的头信息
		 */
		httpRequestBase.addHeader("User-Agent", IHttp.USER_AGENT);// 设置默认头信息
		
		if(request.getHeaderData().size() > 0){
			Iterator<String> ite = request.getHeaderData().keySet().iterator();
			while(ite.hasNext()){
				String name = ite.next();
				httpRequestBase.addHeader(name, request.getHeaderData().get(name));
			}
		}
	}
	
	
	/**
	 * 配置请求信息
	 * 
	 * @param connectTimeOut 连接超时时间
	 * @param socketTimeOut IO超时时间
	 * @return 
	 */
	private RequestConfig getBaseRequestConfig(int connectTimeOut,int socketTimeOut) {
		return RequestConfig.custom().setConnectTimeout(connectTimeOut).setSocketTimeout(socketTimeOut)
				.build();
	}



}	
