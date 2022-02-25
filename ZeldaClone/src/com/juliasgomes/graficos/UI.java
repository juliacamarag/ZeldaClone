package com.juliasgomes.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.juliasgomes.entities.Player;

public class UI {

	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(8,3, 70, 8);
		g.setColor(new Color(179,207,221));
		g.fillRect(8, 3, (int)((Player.life/Player.maxlife)*70), 8);
		g.setColor(new Color(100,150,180));
		g.setFont(new Font("arial", Font.BOLD,8));
		g.drawString((int)Player.life + "/" + (int)Player.maxlife,31,10);
	}
}
