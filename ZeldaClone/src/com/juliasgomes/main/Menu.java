package com.juliasgomes.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {

	public String[] options = {"new game", "run game", "exit"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;
	
	public boolean pause = false;
	
	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if (currentOption < 0)
				currentOption = maxOption;
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "new game") {
				Game.gameState = "NORMAL";
				pause = false;
			}else if(options[currentOption] == "exit") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		//g.setColor(new Color(250,203,219));
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		//g.setColor(new Color(249, 135, 197));
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("cat vs aliens ^. .^", (Game.WIDTH * Game.SCALE) / 2 - 140, (Game.HEIGHT * Game.SCALE) / 2 - 130);
		
		// Opções de Menu
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,24));
		if(pause == false)
			g.drawString("new game", (Game.WIDTH * Game.SCALE) / 2 - 60, 200);
		else
			g.drawString("continue game", (Game.WIDTH * Game.SCALE) / 2 - 85, 200);
		g.drawString("run game", (Game.WIDTH * Game.SCALE) / 2 - 55, 240);
		g.drawString("exit", (Game.WIDTH * Game.SCALE) / 2 - 20, 280);
		
		if(options[currentOption] == "new game") {
			g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 110, 200);
		}else if(options[currentOption] == "run game") {
			g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 110, 240);
		}else if(options[currentOption] == "exit") {
			g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 110, 280);
		}
	}
	
}
