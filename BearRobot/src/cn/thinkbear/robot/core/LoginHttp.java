package cn.thinkbear.robot.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.thinkbear.robot.factory.QQConstants;
import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.utils.QQException.QQErrorCode;
import cn.thinkbear.robot.vo.QQHttpRequest;
import cn.thinkbear.robot.vo.QQLogin2;
import cn.thinkbear.robot.vo.QQResponse;
import cn.thinkbear.robot.vo.QRCodeResult;
import cn.thinkbear.robot.vo.QQSelfInfo;
import cn.thinkbear.robot.vo.QQSession;
import cn.thinkbear.robot.vo.QQVfwebqq;

public class LoginHttp extends BaseHttp {

	public byte[] getQRCode() throws QQException, Exception {

		QQHttpRequest request = new QQHttpRequest(QQConstants.URL_GET_QRCODE, QQHttpRequest.RequestMethod.GET);

		// appid=501004106&e=0&l=M&s=5&d=72&v=4&t=0.3669793399321737
		request.addFromData("appid", "501004106");
		request.addFromData("e", "0");
		request.addFromData("l", "M");
		request.addFromData("s", "5");
		request.addFromData("d", "72");
		request.addFromData("v", "4");
		request.addFromData("t", String.valueOf(Math.random()));
		CloseableHttpResponse response = super.getHttpResponse(request);
		byte[] data = EntityUtils.toByteArray(response.getEntity());
		response.close();
		return data;
	}

	public QRCodeResult checkQRCode() throws QQException, Exception {
		QQHttpRequest request = new QQHttpRequest(QQConstants.URL_CHECK_QRCODE, QQHttpRequest.RequestMethod.GET);

		// webqq_type=10&remember_uin=1&login2qq=1&aid=501004106&u1=http%3A%2F%2Fw.qq.com%2Fproxy.html%3Flogin2qq%3D1%26webqq_type%3D10
		// &ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=0-0-6073
		// &mibao_css=m_webqq&t=undefined&g=1&js_type=0&js_ver=10179&login_sig=&pt_randsalt=0
		request.addFromData("webqq_type", "10");
		request.addFromData("remember_uin", "1");
		request.addFromData("login2qq", "1");
		request.addFromData("aid", "501004106");
		request.addFromData("u1", "http://w.qq.com/proxy.html?login2qq=1&webqq_type=10");
		request.addFromData("ptredirect", "0");
		request.addFromData("ptlang", "2052");
		request.addFromData("daid", "164");
		request.addFromData("from_ui", "1");
		request.addFromData("pttype", "1");
		request.addFromData("dumy", "");
		request.addFromData("fp", "loginerroralert");
		request.addFromData("action", "0-0-6073");
		request.addFromData("mibao_css", "m_webqq");
		request.addFromData("t", "undefined");
		request.addFromData("g", "1");
		request.addFromData("js_type", "0");
		request.addFromData("js_ver", "10179");
		request.addFromData("login_sig", "");
		request.addFromData("pt_randsalt", "0");
		CloseableHttpResponse response = super.getHttpResponse(request);
		String data = EntityUtils.toString(response.getEntity());
		response.close();
		Pattern pt = Pattern.compile(QQConstants.REGXP_LOGIN);
		Matcher mc = pt.matcher(data);
		QRCodeResult result = null;
		if (mc.find()) {
			result = new QRCodeResult();
			result.setCode(Integer.parseInt(mc.group(1)));
			result.setMessage(mc.group(5));
			result.setHttp(mc.group(3));
		}
		return result;

	}

	public void checkSig(String url) throws QQException, Exception {
		QQHttpRequest request = new QQHttpRequest(url, QQHttpRequest.RequestMethod.GET);
		CloseableHttpResponse response = super.getHttpResponse(request);
		response.close();
	}

	public QQVfwebqq getVfwebqq() throws QQException, Exception {

		QQHttpRequest request = new QQHttpRequest(QQConstants.URL_GET_VFWEBQQ, QQHttpRequest.RequestMethod.GET);
		String ptwebqq = super.getCookieByValue("ptwebqq");

		request.addFromData("ptwebqq", ptwebqq);
		request.addFromData("clientid", String.valueOf(QQConstants.PARA_CLIENTID));
		request.addFromData("psessionid", "");
		request.addFromData("t", String.valueOf(System.currentTimeMillis() / 1000));

		request.addHeaderData("Referer", QQConstants.REFERER_S);

		CloseableHttpResponse response = super.getHttpResponse(request);
		String gsonData = EntityUtils.toString(response.getEntity());
		response.close();
		try {
			QQResponse<QQVfwebqq> qqResponse = super.getGson().fromJson(gsonData,
					new TypeToken<QQResponse<QQVfwebqq>>() {
					}.getType());
			if (qqResponse.getRetcode() == 0) {
				return qqResponse.getResult();
			} else {
				throw new QQException(QQErrorCode.RETCODE_ERROR, String.valueOf(qqResponse.getRetcode()));
			}

		} catch (QQException e) {
			throw e;
		} catch (Exception e) {
			throw new QQException(QQException.QQErrorCode.GSON_ERROR);
		}
	}

	public QQLogin2 getLogin2() throws QQException, Exception {
		QQHttpRequest request = new QQHttpRequest(QQConstants.URL_LOGIN2, QQHttpRequest.RequestMethod.POST);
		String ptwebqq = super.getCookieByValue("ptwebqq");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("ptwebqq", ptwebqq);
		map.put("clientid", QQConstants.PARA_CLIENTID);
		map.put("psessionid", "");
		map.put("status", "online");
		request.addFromData("r", super.getGson().toJson(map));

		request.addHeaderData("Referer", QQConstants.REFERER_D1);
		request.addHeaderData("Origin", QQConstants.ORIGIN_D1);
		request.addHeaderData("Connection", "Keep-Alive");

		CloseableHttpResponse response = super.getHttpResponse(request);
		String gsonData = EntityUtils.toString(response.getEntity());

		response.close();
		try {
			QQResponse<QQLogin2> qqResponse = super.getGson().fromJson(gsonData, new TypeToken<QQResponse<QQLogin2>>() {
			}.getType());
			if (qqResponse.getRetcode() == 0) {
				return qqResponse.getResult();
			} else {
				throw new QQException(QQErrorCode.RETCODE_ERROR, String.valueOf(qqResponse.getRetcode()));
			}

		} catch (QQException e) {
			throw e;
		} catch (Exception e) {
			throw new QQException(QQException.QQErrorCode.GSON_ERROR);
		}

	}

	public QQSelfInfo getSelfInfo() throws QQException, Exception {
		QQHttpRequest request = new QQHttpRequest(QQConstants.URL_GET_SELF_INFO, QQHttpRequest.RequestMethod.GET);
		request.addFromData("t", String.valueOf(System.currentTimeMillis() / 1000));
		request.addHeaderData("Referer", QQConstants.REFERER_S);
		CloseableHttpResponse response = super.getHttpResponse(request);
		String gsonData = EntityUtils.toString(response.getEntity());
		response.close();
		try {
			QQResponse<QQSelfInfo> qqResponse = super.getGson().fromJson(gsonData,
					new TypeToken<QQResponse<QQSelfInfo>>() {
					}.getType());

			if (qqResponse.getRetcode() == 0) {
				return qqResponse.getResult();
			} else {
				throw new QQException(QQErrorCode.RETCODE_ERROR, String.valueOf(qqResponse.getRetcode()));
			}

		} catch (QQException e) {
			throw e;
		} catch (Exception e) {
			throw new QQException(QQException.QQErrorCode.GSON_ERROR);
		}

	}

	public byte[] getFace(String vfwebqq, long uin) throws QQException, Exception {
		QQHttpRequest request = new QQHttpRequest(QQConstants.URL_GET_FACE, QQHttpRequest.RequestMethod.GET);
		request.addFromData("cache", "1");
		request.addFromData("type", "1");
		request.addFromData("f", "40");
		request.addFromData("uin", String.valueOf(uin));
		request.addFromData("t", String.valueOf(System.currentTimeMillis() / 1000));
		request.addFromData("vfwebqq", vfwebqq);
		

		request.addHeaderData("Referer", "http://w.qq.com/");
		request.addHeaderData("Host", "face5.web.qq.com");
		System.out.println(request.getURI().toString());

		CloseableHttpResponse response = super.getHttpResponse(request);
		Header header = response.getFirstHeader("Location");
		for (Header h : response.getAllHeaders()) {
			System.out.println(h.getName() + "\t\t" + h.getValue());
		}
		if (header != null) {
			String location = header.getValue();
			System.out.println(location);
			response.close();
			QQHttpRequest faceRequest = new QQHttpRequest(location, QQHttpRequest.RequestMethod.GET);
			response = super.getHttpResponse(faceRequest);
			byte[] data = EntityUtils.toByteArray(response.getEntity());
			response.close();
			return data;
		} else {
			throw new QQException(QQErrorCode.FACE_EMPTY);
		}
	}

}
