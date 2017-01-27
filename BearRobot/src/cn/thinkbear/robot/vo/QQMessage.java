package cn.thinkbear.robot.vo;

import java.util.List;

/**
 * 好友消息
 * 
 * JSON数据示例：
 * {
    "result": [
        {
            "poll_type": "message",
            "value": {
                "content": [
                    [
                        "font",
                        {
                            "color": "000000",
                            "name": "微软雅黑",
                            "size": 10,
                            "style": [
                                0,
                                0,
                                0
                            ]
                        }
                    ],
                    "fgghh"
                ],
                "from_uin": 2786289188,
                "msg_id": 30592,
                "msg_type": 0,
                "time": 1479009963,
                "to_uin": 740578998
            }
        }
    ],
    "retcode": 0
 * }
 * 
 * @author ThinkBear
 * @date 2016年11月13日 下午7:28:32
 */
public class QQMessage {

	private Object[] content;//消息内容
	
	private long from_uin;//发送方 的uin值
	private int msg_id;//消息id
	private int msg_type;//消息类型
	private long time;//时间
	private long to_uin;//接收方 的帐号
	
	public String getMsg(){
		if(this.content == null || this.content.length != 2){
			return "";
		}
		
		Object msgData = this.content[1];
		
		if(msgData instanceof String){
			return String.valueOf(msgData);
		}else if(msgData instanceof List){
			return "[表情]";
		}else{
			return "";
		}
		
	}
	
	public Object[] getContent() {
		return content;
	}
	public void setContent(Object[] content) {
		this.content = content;
	}
	public long getFrom_uin() {
		return from_uin;
	}
	public void setFrom_uin(long from_uin) {
		this.from_uin = from_uin;
	}
	public int getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}
	public int getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getTo_uin() {
		return to_uin;
	}
	public void setTo_uin(long to_uin) {
		this.to_uin = to_uin;
	}
	
	
}
