package cn.thinkbear.robot.gui.frame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.http.client.CookieStore;

import cn.thinkbear.robot.core.InfoHttp;
import cn.thinkbear.robot.core.PollHttp;
import cn.thinkbear.robot.gui.GuiFactory;
import cn.thinkbear.robot.gui.IContainers;
import cn.thinkbear.robot.gui.panel.QQSelfInfoPanel;
import cn.thinkbear.robot.gui.panel.QQSelfInfoPanel.Callback;
import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.vo.QQSelfInfo;
import cn.thinkbear.robot.vo.QQSession;

public class MainFrame implements IContainers {

	private final int SCREEN_WIDTH = 300;
	private final int SCREEN_HEIGHT = 600;

	private JFrame mainFrame = null;
	private JPanel mainPanel = null;
	private InfoHttp infoHttp = null;
	private PollHttp pollHttp = null;

	private QQSelfInfoPanel selfInfoPanel = null;
	private QQSession session = null;

	public MainFrame(QQSession session, CookieStore cookieStore) {
		this.infoHttp = new InfoHttp(session, cookieStore);
		this.pollHttp = new PollHttp(session, cookieStore);
		this.session = session;
		this.doCase();
		this.doAppend();
		this.doSet();
	}

	@Override
	public void doCase() {
		this.mainFrame = new JFrame("BearRobot");
		this.mainPanel = new JPanel(new BorderLayout());
		this.selfInfoPanel = new QQSelfInfoPanel(this.session.getSelfInfo(),new Callback() {
			
			@Override
			public void requestRefreshPhoto(JLabel photoLabel, long uin) {
				new Thread(new Task_GetFace(photoLabel, uin)).start();
				
			}
		});

	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.selfInfoPanel.getMainPanel(), BorderLayout.NORTH);
		this.mainFrame.add(this.mainPanel);
	}

	@Override
	public void doSet() {
		
		this.mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setVisible(true);
	}

	
	
	private class Task_GetFace implements Runnable {

		private long uin;
		private JLabel goldLabel;

		public Task_GetFace(JLabel goldLabel, long uin) {
			this.uin = uin;
			this.goldLabel = goldLabel;
		}

		@Override
		public void run() {

			try {
				byte[] data = infoHttp.getFace(uin);
				ImageIcon icon = new ImageIcon(data);
				this.goldLabel.setIcon(icon);

			} catch (QQException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
