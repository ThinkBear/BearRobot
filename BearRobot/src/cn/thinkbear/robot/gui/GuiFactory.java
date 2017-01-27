package cn.thinkbear.robot.gui;

import java.awt.Color;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class GuiFactory {


	public static final Font FONT_SMALL_BOLD_OBJECT = new Font(
			GuiPara.FONT_NAME, Font.BOLD, GuiPara.FONT_SMALL);
	public static final Font FONT_SMALL_PLAIN_OBJECT = new Font(
			GuiPara.FONT_NAME, Font.PLAIN, GuiPara.FONT_SMALL);

	public static final Font FONT_BIG_BOLD_OBJECT = new Font(GuiPara.FONT_NAME,
			Font.BOLD, GuiPara.FONT_BIG);
	public static final Font FONT_BIG_PLAIN_OBJECT = new Font(
			GuiPara.FONT_NAME, Font.PLAIN, GuiPara.FONT_BIG);

	public static final Font FONT_AMONG_BOLD_OBJECT = new Font(
			GuiPara.FONT_NAME, Font.BOLD, GuiPara.FONT_AMONG);
	public static final Font FONT_AMONG_PLAIN_OBJECT = new Font(
			GuiPara.FONT_NAME, Font.PLAIN, GuiPara.FONT_AMONG);

	/**
	 * 取得指定大小的图片
	 * 
	 * @param image
	 *            图片对象
	 * @param width
	 *            图片的宽度
	 * @param height
	 *            图片的高度
	 * @return 返回一个指定大小的图片对象
	 */
	public static ImageIcon getZoomImage(ImageIcon image, int width, int height) {
		ImageIcon newImage = new ImageIcon();
		// 对传入的图片对象转为Image对象，并通过此对象的getScaledInstance方法，创建此图像的缩放版本。
		// 返回一个新的 Image对象后设置给newImage
		newImage.setImage(image.getImage().getScaledInstance(width, height,
				Image.SCALE_DEFAULT));
		return newImage;
	}
	
	public static JLabel getAmongPlainLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(FONT_AMONG_PLAIN_OBJECT);
		return label;
	}

	public static JLabel getBigBoldLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(FONT_BIG_BOLD_OBJECT);
		return label;
	}

	public static JLabel getBigPlainLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(FONT_BIG_PLAIN_OBJECT);
		return label;
	}

	public static JLabel getAmongBoldLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(FONT_AMONG_BOLD_OBJECT);
		return label;
	}

	public static JButton getButton(String text, Object listener) {
		JButton newButton = new JButton(text);
		newButton.setFont(FONT_AMONG_PLAIN_OBJECT);
		newButton.addActionListener((ActionListener) listener);
		newButton.setOpaque(false);
		return newButton;
	}

	public static ImageIcon getResByImage(String name) {
		URL url = GuiFactory.class.getResource("/res/image/" + name);
		if (url == null) {
			return null;
		} else {
			return new ImageIcon(url);
		}
	}

}
