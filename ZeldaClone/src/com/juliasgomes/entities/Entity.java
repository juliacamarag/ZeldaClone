package com.juliasgomes.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.juliasgomes.main.Game;
import com.juliasgomes.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6*16,0,16,16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(8*16, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(7*16, 16, 16, 16);
	
	public static BufferedImage WEAPON_RIGHT = Game.spritesheet.getSprite(144,0,16,16);
	public static BufferedImage WEAPON_LEFT = Game.spritesheet.getSprite(112,16,16,16);
	public static BufferedImage WEAPON_UP = Game.spritesheet.getSprite(128, 16, 16, 16);
	public static BufferedImage WEAPON_DOWN = Game.spritesheet.getSprite(144, 16, 16, 16);
	
	public static BufferedImage Damage_Weapon_RIGHT = Game.spritesheet.getSprite(96,48,16,16);
	public static BufferedImage Damage_Weapon_LEFT = Game.spritesheet.getSprite(112,48,16,16);
	public static BufferedImage Damage_Weapon_UP = Game.spritesheet.getSprite(128,48,16,16);
	public static BufferedImage Damage_Weapon_DOWN = Game.spritesheet.getSprite(144,48,16,16);

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	private int maskX, maskY, mWidth, mHeight;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskX = 0;
		this.maskY = 0;
		this.mWidth = width;
		this.mHeight = height;
	}
	
	public void setMask(int maskX, int maskY, int mWidth, int mHeight) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.mWidth = mWidth;
		this.mHeight = mHeight;
		
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskX, e1.getY() + e1.maskY, e1.mWidth, e1.mHeight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskX, e2.getY() + e2.maskY, e2.mWidth, e2.mHeight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y,null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskX - Camera.x, this.getY() + maskY - Camera.y, mWidth, mHeight);
	}
	
}
