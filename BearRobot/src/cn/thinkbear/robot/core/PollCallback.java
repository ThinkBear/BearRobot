package cn.thinkbear.robot.core;

import cn.thinkbear.robot.vo.QQDiscuMessage;
import cn.thinkbear.robot.vo.QQGroupMessage;
import cn.thinkbear.robot.vo.QQMessage;
/**
 * QQ消息事件回调
 * 
 * 好友消息、群消息、讨论群消息
 * 
 * @author ThinkBear
 * @date 2016年11月13日 下午8:07:44
 */
public interface PollCallback {
	/**
	 * 收到好友消息事件
	 * @param message 好友消息数据对象
	 */
	public void onQQMessageEvent(QQMessage message);
	/**
	 * 收到群消息事件
	 * @param message 群消息数据对象
	 */
	public void onQQGroupMessageEvent(QQGroupMessage message);
	/**
	 * 收到讨论群消息事件
	 * @param message 讨论群消息数据对象
	 */
	public void onQQDiscuMessageEvent(QQDiscuMessage message);
}
