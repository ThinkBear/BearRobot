package cn.thinkbear.robot.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.google.gson.Gson;

import cn.thinkbear.robot.core.LoginHttp;
import cn.thinkbear.robot.gui.GuiFactory;
import cn.thinkbear.robot.gui.IContainers;
import cn.thinkbear.robot.gui.frame.MainFrame;
import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.vo.QQSession;
import cn.thinkbear.robot.vo.QRCodeResult;

public class QRCodeDialog extends JDialog implements IContainers{
	private final int RETRY_MAX = 3;//请求失败，重试的次数
	private final String QRCODE_GET = "获取二维码图片中..";
	private final String QRCODE_SCANNER = "打开手机QQ，码上扫描";
	private final String QRCODE_AUTH = "正在等待手机确认操作..";
	private final String QRCODE_INVALID = "二维码已失效，请点击二维码";
	private final String QRCODE_REGXP = "检测数据正则匹配失败，请联系开发者";
	
	private final String QQSESSION_CHECKSIG = "加载QQ用户权限中 (%d) ..";
	private final String QQSESSION_GETVFWEBQQ = "获取加密请求参数 (%d) ..";
	private final String QQSESSION_GETLOGIN2 = "获取模拟请求数据 (%d) ..";
	private final String QQSESSION_GETSELFINFO = "获取登录的用户信息 (%d) ..";
	private final String QQSESSION_GETLOST = "登录失败，请点击图标重新扫描";
	
	private JPanel mainPanel = null;
	private JPanel topPanel = null;
	private JPanel qrcodePanel = null;
	private JLabel closeLabel = null;
	private JLabel topLabel = null;
	private JLabel qrcodeLabel = null;
	private JLabel infoLabel = null;
	private Thread getQRCode = null;
	private Thread checkQRCode = null;
	
	private ImageIcon defaultQrcode = null;
	
	private MyMouseEvent mouseEvent = null;
	private final int SCREEN_WIDTH = 300;
	private final int SCREEN_HEIGHT = 300;
	private final int QRCODE_SIZE = SCREEN_WIDTH * 3 / 5;
	private final int QRCODE_PADDING = QRCODE_SIZE / 6;
	
	private LoginHttp loginHttp = null;
	
	
	
	public QRCodeDialog (){
		this.doCase();
		this.doAppend();
		this.doSet();
	}
	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new BorderLayout(5,5));
		this.topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,8,4));
		this.qrcodePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.closeLabel = GuiFactory.getAmongBoldLabel("关闭");
		this.topLabel = GuiFactory.getAmongBoldLabel("置顶");
		this.defaultQrcode = GuiFactory.getResByImage("qq_logo.png");
		this.qrcodeLabel = new JLabel();
		this.infoLabel = GuiFactory.getAmongBoldLabel("");
		this.mouseEvent = new MyMouseEvent();
		this.loginHttp = new LoginHttp();
	}

	@Override
	public void doAppend() {
		this.topPanel.add(this.topLabel);
		this.topPanel.add(this.closeLabel);
		this.qrcodePanel.add(this.qrcodeLabel);
		this.mainPanel.add(this.topPanel,BorderLayout.NORTH);
		this.mainPanel.add(this.qrcodePanel,BorderLayout.CENTER);
		this.mainPanel.add(this.infoLabel,BorderLayout.SOUTH);
		super.add(this.mainPanel);
	}

	@Override
	public void doSet() {
		
		this.infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.qrcodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.qrcodeLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		this.qrcodeLabel.setPreferredSize(new Dimension(QRCODE_SIZE+QRCODE_PADDING,QRCODE_SIZE+QRCODE_PADDING));
		this.qrcodeLabel.setBackground(Color.WHITE);
		
		this.closeLabel.addMouseListener(this.mouseEvent);
		this.qrcodeLabel.addMouseListener(this.mouseEvent);
		this.topLabel.addMouseListener(this.mouseEvent);
		
		this.closeLabel.setForeground(Color.GRAY);
		this.topLabel.setForeground(Color.BLACK);
		
		this.doGetQRCode();
		
		this.mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
		// 设置对话框大小
		super.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		// 设置对话框默认关闭处理操作
		super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// 设置对话框显示于相对于主窗口的正中央位置
		super.setLocationRelativeTo(null);

		// 设置此 dialog 的模式类型,对话框阻塞同一 Java 应用程序中的所有顶层窗口
		super.setModalityType(ModalityType.APPLICATION_MODAL);
		super.setAlwaysOnTop(true);
		
		super.setUndecorated(true);
		// 显示对话框
		super.setVisible(true);
		
	}
	
	private void doGetQRCode(){
		if(getQRCode!=null && getQRCode.getState() == Thread.State.RUNNABLE){
			System.out.println("获取中");
			return;
		}
		
		this.getQRCode = new Thread(new Task_GetQRCode());
		this.getQRCode.start();		
	}
	
	private void doCheckQRCode(){
		if(this.checkQRCode!=null && this.checkQRCode.getState() != Thread.State.TERMINATED){
			System.out.println("检查运行中");
			return;
		}
		this.checkQRCode = new Thread(new Task_CheckQRCode());
		this.checkQRCode.start();
	}
	
	private class MyMouseEvent extends MouseAdapter{

		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			if(e.getSource() == QRCodeDialog.this.closeLabel){
				System.exit(0);	
			}else if(e.getSource() == QRCodeDialog.this.qrcodeLabel){
				doGetQRCode();
			}else if(e.getSource() == QRCodeDialog.this.topLabel){
				boolean isAlwaysOnTop = QRCodeDialog.this.isAlwaysOnTop();
				QRCodeDialog.this.setAlwaysOnTop(!isAlwaysOnTop);
				QRCodeDialog.this.topLabel.setForeground(isAlwaysOnTop?Color.GRAY:Color.BLACK);
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			if(e.getSource() == QRCodeDialog.this.closeLabel){
				closeLabel.setForeground(Color.BLACK);
			}else if(e.getSource() == QRCodeDialog.this.qrcodeLabel){
				//QRCodeDialog.this.qrcodeLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			if(e.getSource() == QRCodeDialog.this.closeLabel){
				closeLabel.setForeground(Color.GRAY);	
			}else if(e.getSource() == QRCodeDialog.this.qrcodeLabel){
				//QRCodeDialog.this.qrcodeLabel.setBorder(BorderFactory.createEtchedBorder());
			}
		}
		
	}
	
	
	private class Task_GetQRCode implements Runnable{

		@Override
		public void run() {
			QRCodeDialog.this.qrcodeLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			QRCodeDialog.this.qrcodeLabel.setIcon(defaultQrcode);
			QRCodeDialog.this.infoLabel.setText(QRCODE_GET);
			
			try {
				Thread.sleep(1000);
				byte[] data = loginHttp.getQRCode();
				ImageIcon icon = new ImageIcon(data);
				
				QRCodeDialog.this.qrcodeLabel.setIcon(GuiFactory.getZoomImage(icon, QRCODE_SIZE, QRCODE_SIZE));
				QRCodeDialog.this.infoLabel.setText(QRCODE_SCANNER);
				QRCodeDialog.this.qrcodeLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				doCheckQRCode();
			} catch (Exception e) {
				QRCodeDialog.this.infoLabel.setText(QQException.getException(e).toString());
			}
			
		}
		
	}
	
	private class Task_CheckQRCode implements Runnable{
		
		@Override
		public void run() {
			boolean flag = true;
			while(flag){
				
				try {
					/**
					 * 66未失效
					 * 65已失效
					 * 67认证中
					 * 0   认证成功 
					 */
					QRCodeResult result = loginHttp.checkQRCode();
					if(result != null){
						switch(result.getCode()){
						case 0:
							flag = false;
							QRCodeDialog.this.qrcodeLabel.setEnabled(false);
							QRCodeDialog.this.qrcodeLabel.setIcon(defaultQrcode);
							QRCodeDialog.this.qrcodeLabel.setBorder(BorderFactory.createEtchedBorder());
							
							new Thread(new Task_GetSession(result.getHttp())).start();
							break;
						case 65:
							QRCodeDialog.this.infoLabel.setText(QRCODE_INVALID);
							QRCodeDialog.this.qrcodeLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
							flag = false;
							break;
						case 66:

							//QRCodeDialog.this.qrcodeLabel.setBorder(BorderFactory.create);
							break;
						case 67:
							QRCodeDialog.this.infoLabel.setText(QRCODE_AUTH);
							QRCodeDialog.this.qrcodeLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
							break;
						default:
								
						}
					}else{
						QRCodeDialog.this.infoLabel.setText(QRCODE_REGXP);
						flag = false;
					}
				}catch (Exception e) {
					System.out.println(QQException.getException(e).toString());
				}	
				
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e1) {
				}
			}
			
		}
		
	}
	
	
	
	private class Task_GetSession implements Runnable{

		private String checkSigHttp = null;
		
		public Task_GetSession(String checkSigHttp){
			this.checkSigHttp = checkSigHttp;
		}
		
		@Override
		public void run() {
			for(int i = 1 ; i <= RETRY_MAX ; i ++){
				
				try {
					Thread.sleep(1000);
					QRCodeDialog.this.infoLabel.setText(String.format(QQSESSION_CHECKSIG, i));
					loginHttp.checkSig(this.checkSigHttp);
					break;
				} catch (QQException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(i == RETRY_MAX){
					QRCodeDialog.this.infoLabel.setText(QQSESSION_GETLOST);
					QRCodeDialog.this.qrcodeLabel.setEnabled(true);
					return;
				}
				
			}
			QQSession session = new QQSession();
			for(int i = 1 ; i <= RETRY_MAX ; i ++){
				
				try {
					Thread.sleep(1000);
					QRCodeDialog.this.infoLabel.setText(String.format(QQSESSION_GETVFWEBQQ, i));
					session.setVfwebqq(loginHttp.getVfwebqq());
					break;
				} catch (QQException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(i == RETRY_MAX){
					QRCodeDialog.this.infoLabel.setText(QQSESSION_GETLOST);
					QRCodeDialog.this.qrcodeLabel.setEnabled(true);
					return;
				}
			}
			
			for(int i = 1 ; i <= RETRY_MAX ; i ++){
				
				try {
					Thread.sleep(1000);
					QRCodeDialog.this.infoLabel.setText(String.format(QQSESSION_GETLOGIN2, i));
					session.setLogin2(loginHttp.getLogin2());
					break;
				} catch (QQException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(i == RETRY_MAX){
					QRCodeDialog.this.infoLabel.setText(QQSESSION_GETLOST);
					QRCodeDialog.this.qrcodeLabel.setEnabled(true);
					return;
				}
			}
			
			for(int i = 1 ; i <= RETRY_MAX ; i ++){
				
				try {
					Thread.sleep(1000);
					QRCodeDialog.this.infoLabel.setText(String.format(QQSESSION_GETSELFINFO, i));
					session.setSelfInfo(loginHttp.getSelfInfo());
					break;
				} catch (QQException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(i == RETRY_MAX){
					QRCodeDialog.this.infoLabel.setText(QQSESSION_GETLOST);
					QRCodeDialog.this.qrcodeLabel.setEnabled(true);
					return;
				}
			}
			
			try {
				loginHttp.getFace(session.getVfwebqq().getVfwebqq(), session.getSelfInfo().getUin());
			} catch (QQException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//new MainFrame(session,loginHttp.getCookieStore());
			//QRCodeDialog.this.dispose();
		}
		
	}

}
