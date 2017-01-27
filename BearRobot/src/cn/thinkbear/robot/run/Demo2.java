package cn.thinkbear.robot.run;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import cn.thinkbear.robot.gui.dialog.QRCodeDialog;
import cn.thinkbear.robot.gui.frame.MainFrame;

public class Demo2 {
	public static void main(String args[]) throws ClientProtocolException, IOException{
		new QRCodeDialog();	
		//new MainFrame(null,null);
	}
}
