package com.smile.download.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class MyButton extends BasicButtonUI {

	public MyButton() {
		super();
	}

	public void installUI(JComponent c) {
		super.installUI(c);

		AbstractButton button = (AbstractButton) c;
		button.setRolloverEnabled(true);
		button.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
	}

	public void paint(Graphics g, JComponent c) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
//		g2d.setColor(new Color(238, 238, 238));
		g2d.setColor(new Color(150, 235, 130));
		g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 5, 5);
		g2d.setColor(new Color(136, 136, 136));
		g2d.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 5, 5);

		super.paint(g, c);
	}

	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(1, 1, b.getWidth() - 2, b.getHeight() - 2, 5, 5);
		
		super.paintButtonPressed(g, b);
	}
	
}
