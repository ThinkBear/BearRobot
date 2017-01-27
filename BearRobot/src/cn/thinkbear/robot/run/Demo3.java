package cn.thinkbear.robot.run;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.apache.http.client.utils.URIBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.vo.QQDiscuMessage;
import cn.thinkbear.robot.vo.QQGroupMessage;
import cn.thinkbear.robot.vo.QQMessage;
import cn.thinkbear.robot.vo.QQPoll;
import cn.thinkbear.robot.vo.QQResponse;
import cn.thinkbear.robot.vo.QQResponseList;
import cn.thinkbear.robot.vo.QQSelfInfo;

public class Demo3 {

	public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
		Scanner scan = new Scanner(new File("source.txt"));
		StringBuffer buf = new StringBuffer();
		
		while(scan.hasNext()){
			buf.append(scan.nextLine());
		}
		Gson g = new Gson();
		System.out.println(buf.toString());
		
		QQResponseList<QQPoll> res = new Gson().fromJson(buf.toString(), new TypeToken<QQResponseList<QQPoll>>(){}.getType());
		
		String pollType = res.getResult().get(0).getPoll_type();
		
		Object obj = res.getResult().get(0).getValue();
		String data = g.toJson(obj);
		if("message".equals(pollType)){
			QQMessage message = g.fromJson(data, QQMessage.class);
			System.out.println(message.getMsg());
		}else if("group_message".equals(pollType)){
			QQGroupMessage groupMessage = g.fromJson(data, QQGroupMessage.class);
			System.out.println(groupMessage.getGroup_code());
			System.out.println(groupMessage.getSend_uin());
			System.out.println(groupMessage.getMsg());
			
		}else if("discu_message".equals(pollType)){
			QQDiscuMessage discuMessage = g.fromJson(data, QQDiscuMessage.class);
			System.out.println(discuMessage.getDid());
			System.out.println(discuMessage.getSend_uin());
			System.out.println(discuMessage.getMsg());
			
		}
		
		
		
		
	}
	
	

}
