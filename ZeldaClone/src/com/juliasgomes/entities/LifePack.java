package com.juliasgomes.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.juliasgomes.main.Game;
import com.juliasgomes.world.Camera;

public class LifePack extends Entity{

	
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
	
	private BufferedImage[] lifePack;
	
	public LifePack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		lifePack = new BufferedImage[2];
		
		for(int i = 0; i < 2; i++) {
			lifePack[i] = Game.spritesheet.getSprite(96 + (i * 16), 0, 16, 16); 
		}
	}
	
	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(lifePack[index],this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
