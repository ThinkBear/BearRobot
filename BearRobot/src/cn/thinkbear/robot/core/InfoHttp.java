package cn.thinkbear.robot.core;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.google.gson.reflect.TypeToken;

import cn.thinkbear.robot.factory.QQConstants;
import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.utils.QQException.QQErrorCode;
import cn.thinkbear.robot.vo.QQHttpRequest;
import cn.thinkbear.robot.vo.QQResponse;
import cn.thinkbear.robot.vo.QQSelfInfo;
import cn.thinkbear.robot.vo.QQSession;

public class InfoHttp extends BaseHttp{
	private QQSession session = null;
	
	
	public InfoHttp(QQSession session,CookieStore cookieStore){
		super(cookieStore);
		this.session = session;
	}
	
	
	
	
	public byte[] getFace(long uin)throws QQException,Exception{
		QQHttpRequest request = new QQHttpRequest(QQConstants.URL_GET_FACE,QQHttpRequest.RequestMethod.GET);
		request.addFromData("cache", "1");
		request.addFromData("type", "1");
		request.addFromData("f", "40");
		request.addFromData("uin", String.valueOf(uin));
		request.addFromData("t", String.valueOf(System.currentTimeMillis() / 1000));
		request.addFromData("vfwebqq", this.session.getVfwebqq().getVfwebqq());
		
		
		request.addHeaderData("Referer","http://w.qq.com/");
		request.addHeaderData("Host", "face5.web.qq.com");
		System.out.println(request.getURI().toString());
		
		CloseableHttpResponse response = super.getHttpResponse(request);
		Header header = response.getFirstHeader("Location");
		for (Header h : response.getAllHeaders()) {
			System.out.println(h.getName()+"\t\t"+h.getValue());
		}
		if(header != null){
			String location = header.getValue();
			System.out.println(location);
			response.close();
			QQHttpRequest faceRequest = new QQHttpRequest(location,QQHttpRequest.RequestMethod.GET);
			response = super.getHttpResponse(faceRequest);
			byte[] data = EntityUtils.toByteArray(response.getEntity());
			response.close();
			return data;
		}else{
			throw new QQException(QQErrorCode.FACE_EMPTY);
		}
	}
}
