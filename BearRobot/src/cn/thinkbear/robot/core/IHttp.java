package cn.thinkbear.robot.core;

import org.apache.http.client.methods.CloseableHttpResponse;

import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.vo.QQHttpRequest;
/**
 * WebQQ 模拟网络请求接口
 * 
 * @author ThinkBear
 * @date 2016年11月23日 下午1:48:26
 */
public interface IHttp {

	public int CONNECT_TIMEOUT = 3000;//确定建立连接之前的超时
	public int SOCKET_TIMEOUT = 5000;//定义以毫秒为单位的套接字超时（SO_TIMEOUT），这是等待数据的超时，或者换句话说，两个连续数据包之间的最大周期不活动。
	
	/**
	 * 默认头信息，User-Agent属性
	 */
	public String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";
	
	/**
	 * 网络请求方法
	 * @param request QQ模拟请求参数
	 * @return 返回响应对象
	 * @throws QQException 发生QQException,交给被调用处处理
	 * @throws Exception 发生Exception,交给被调用处处理
	 */
	public CloseableHttpResponse getHttpResponse(QQHttpRequest request) throws QQException,Exception;
	
	/**
	 * 关闭操作
	 * 
	 * @throws QQException
	 * @throws Exception
	 */
	public void close() throws QQException,Exception;

}
