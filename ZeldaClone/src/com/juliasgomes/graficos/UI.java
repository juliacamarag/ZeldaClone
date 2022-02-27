package com.juliasgomes.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.juliasgomes.entities.Player;
import com.juliasgomes.main.Game;

public class UI {

	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(8,3, 70, 8);
		g.setColor(new Color(179,207,221));
		g.fillRect(8, 3, (int)((Game.player.life/Game.player.maxlife)*70), 8);
		g.setColor(new Color(100,150,180));
		g.setFont(new Font("arial", Font.BOLD,8));
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxlife,31,10);
	}
}
