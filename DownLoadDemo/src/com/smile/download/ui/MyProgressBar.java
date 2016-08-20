package com.smile.download.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * 美化了的进度条
 * 
 * @author smile
 * 
 */
public class MyProgressBar extends BasicProgressBarUI {
	private JProgressBar jpgb;

	public MyProgressBar(JProgressBar jpgb) {
		super();
		this.jpgb = jpgb;
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		// 设置内边距
		c.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}

	@Override
	protected void paintDeterminate(Graphics g, JComponent c) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Insets b = progressBar.getInsets();
		int width = progressBar.getWidth();
		int height = progressBar.getHeight();
		int barRectWidth = width - (b.right + b.left);
		int barRectHeight = height - (b.top + b.bottom);
		int arcSize = 10;
		// 获取已完成的进度
		int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

		// 绘制边框
		g2d.setColor(new Color(136, 136, 136));
		g2d.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arcSize,
				arcSize);

		// 获取进度，值为0-100之间的整型
		int value = jpgb.getValue();
		// (255, 0, 0)是红色， (255, 255, 0)是黄色，所以到0-50设置为红色渐变为黄色，只需将中间那个值由0加到255
		// 因为是0-50，所以把255分为50段，然后根据value为哪一段计算出颜色值
		// 同理51-100，为黄色渐变到绿色
		if (value <= 50) {
			g2d.setColor(new Color(255, value * 255 / 50, 0));
		} else {
			g2d.setColor(new Color(255 - (value - 50) * 255 / 50, 255, 0));
		}
		// 绘制填充的圆角矩形
		g2d.fillRoundRect(1, 1, amountFull - 2, c.getHeight() - 2, arcSize,
				arcSize);
	}
}