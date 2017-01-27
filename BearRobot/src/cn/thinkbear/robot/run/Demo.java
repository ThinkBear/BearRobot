package cn.thinkbear.robot.run;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.ws.spi.http.HttpContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import cn.thinkbear.robot.utils.QQException;

public class Demo {

	public static void main(String[] args) throws ClientProtocolException, IOException {	
	 CloseableHttpClient httpClient = HttpClients.createDefault();
	 HttpGet g = new HttpGet("https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=0.2978390134052902");
	 CloseableHttpResponse httpRes = httpClient.execute(g);
	 HttpEntity entity = httpRes.getEntity();
	 httpRes.close();
	 System.out.println(EntityUtils.toString(entity));
	 /*
	 httpClient.close();
	 httpClient = HttpClients.createDefault();
	 for(int i =0;i<2;i++){
		 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 HttpGet get = new HttpGet("https://ssl.ptlogin2.qq.com/ptqrlogin?webqq_type=10&remember_uin=1&login2qq=1&aid=501004106&u1=http%3A%2F%2Fw.qq.com%2Fproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=0-0-6073&mibao_css=m_webqq&t=undefined&g=1&js_type=0&js_ver=10179&login_sig=&pt_randsalt=0");
		 CloseableHttpResponse httpResponse = httpClient.execute(get);
		 System.out.println(EntityUtils.toString(httpResponse.getEntity()));
	 }
	 */
	}

}
