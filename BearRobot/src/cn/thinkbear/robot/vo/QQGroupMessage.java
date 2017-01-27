package cn.thinkbear.robot.vo;
/**
 * 群信息
 * 
 * JSON数据示例：
 * {
    "result": [
        {
            "poll_type": "group_message",
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
                    "停电了"
                ],
                "from_uin": 1446293248,
                "group_code": 1446293248,
                "msg_id": 8112,
                "msg_type": 0,
                "send_uin": 789652722,
                "time": 1479010043,
                "to_uin": 740578998
            }
        }
    ],
    "retcode": 0
 * }
 * @author ThinkBear
 * @date 2016年11月13日 下午7:43:10
 */
public class QQGroupMessage extends QQMessage{
	private long group_code;//群id
	private long send_uin;//发送信息者的uin值
	public long getGroup_code() {
		return group_code;
	}
	public void setGroup_code(long group_code) {
		this.group_code = group_code;
	}
	public long getSend_uin() {
		return send_uin;
	}
	public void setSend_uin(long send_uin) {
		this.send_uin = send_uin;
	}
	
	
}
