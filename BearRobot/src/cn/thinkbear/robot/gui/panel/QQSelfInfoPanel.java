package cn.thinkbear.robot.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cn.thinkbear.robot.core.InfoHttp;
import cn.thinkbear.robot.gui.GuiFactory;
import cn.thinkbear.robot.gui.IContainers;
import cn.thinkbear.robot.gui.dialog.QRCodeDialog;
import cn.thinkbear.robot.utils.QQException;
import cn.thinkbear.robot.vo.QQSelfInfo;

public class QQSelfInfoPanel implements IContainers {
	private JPanel mainPanel = null;
	private JPanel infoPanel = null;

	private JLabel photoLabel = null;

	private JLabel nickLabel = null;
	private JLabel uinLabel = null;

	private QQSelfInfo selfInfo = null;
	
	private Callback callback = null;


	public QQSelfInfoPanel(QQSelfInfo selfInfo ,Callback callback) {
		this.selfInfo = selfInfo;
		this.callback = callback;
		this.doCase();
		this.doAppend();
		this.doSet();
	}

	@Override
	public void doCase() {
		this.mainPanel = new JPanel(new BorderLayout(5, 5));
		this.infoPanel = new JPanel(new GridLayout(2, 1));
		this.photoLabel = new JLabel(GuiFactory.getResByImage("qq_logo_60.png"));
		this.nickLabel = GuiFactory.getBigBoldLabel("");
		this.uinLabel = GuiFactory.getBigPlainLabel("");
	}

	@Override
	public void doAppend() {
		this.mainPanel.add(this.photoLabel, BorderLayout.WEST);
		this.infoPanel.add(this.nickLabel);
		this.infoPanel.add(this.uinLabel);
		this.mainPanel.add(this.infoPanel);
	}

	@Override
	public void doSet() {
		this.nickLabel.setText(selfInfo.getNick());
		this.uinLabel.setText(String.valueOf(selfInfo.getUin()));
		
		this.photoLabel.addMouseListener(new MyMouseEvent());
		this.photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.photoLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		this.mainPanel.setBackground(Color.WHITE);
		this.photoLabel.setPreferredSize(new Dimension(65, 65));
		this.photoLabel.setBackground(Color.WHITE);
		this.photoLabel.setBorder(BorderFactory.createEtchedBorder());
		this.mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.callback.requestRefreshPhoto(photoLabel, selfInfo.getUin());
	}

	private class MyMouseEvent extends MouseAdapter{

		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			if(e.getSource() == photoLabel){
				callback.requestRefreshPhoto(photoLabel, selfInfo.getUin());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			if(e.getSource() == photoLabel){
				photoLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN,2));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			if(e.getSource() == photoLabel){
				photoLabel.setBorder(BorderFactory.createEtchedBorder());
			}
		}
		
	}

	public JPanel getMainPanel() {
		return this.mainPanel;
	}
	
	
	public interface Callback{
		public void requestRefreshPhoto(JLabel photoLabel,long uin);
	}

}
