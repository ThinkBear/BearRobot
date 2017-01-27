package cn.thinkbear.robot.vo;
/**
 * 讨论群信息
 * 
 * JSON数据示例：
 * {
    "result": [
        {
            "poll_type": "discu_message",
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
                    "wersdvxcvx"
                ],
                "did": 3117632983,
                "from_uin": 3117632983,
                "msg_id": 2,
                "msg_type": 0,
                "send_uin": 2786289188,
                "time": 1479010949,
                "to_uin": 740578998
            }
        }
    ],
    "retcode": 0
 * }
 * 
 * @author ThinkBear
 * @date 2016年11月13日 下午8:02:22
 */
public class QQDiscuMessage extends QQMessage{
	private long did;//讨论群id
	private long send_uin;//发送信息者的uin值
	public long getDid() {
		return did;
	}
	public void setDid(long did) {
		this.did = did;
	}
	public long getSend_uin() {
		return send_uin;
	}
	public void setSend_uin(long send_uin) {
		this.send_uin = send_uin;
	}
	
	
}
