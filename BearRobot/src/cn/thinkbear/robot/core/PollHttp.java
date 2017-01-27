package cn.thinkbear.robot.core;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import cn.thinkbear.robot.factory.QQConstants;
import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.vo.QQHttpRequest;
import cn.thinkbear.robot.vo.QQSession;

public class PollHttp extends BaseHttp {
	private PollCallback callback = null;
	private QQSession session = null;
	
	
	public PollHttp(QQSession session,CookieStore cookieStore){
		super(cookieStore);
		this.session = session;
	}
	
	public void doStartPoll() throws QQException, Exception{
		QQHttpRequest request = new QQHttpRequest(QQConstants.URL_POLL,QQHttpRequest.RequestMethod.POST);
		
		
		request.setConnectTimeOut(10 * 1000);
		request.setSocketTimeOut(60 * 1000);
		
		
		CloseableHttpResponse response = super.getHttpResponse(request);
		String data = EntityUtils.toString(response.getEntity());
		
		response.close();
	}
	
	public void setCallback(PollCallback callback){
		this.callback = callback;
	}
}
