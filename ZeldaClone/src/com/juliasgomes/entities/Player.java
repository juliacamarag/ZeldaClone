package com.juliasgomes.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.juliasgomes.graficos.Spritesheet;
import com.juliasgomes.main.Game;
import com.juliasgomes.world.Camera;
import com.juliasgomes.world.World;

public class Player extends Entity{

	public boolean right,up,left,down;
	public int right_dir = 0,left_dir = 1,up_dir = 2,down_dir = 3;
	public int dir = right_dir;
	public double speed = 1.4;
	
	private int frames = 0,maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] upPlayer;
	
	private BufferedImage playerDamage_RIGHT;
	private BufferedImage playerDamage_LEFT;
	private BufferedImage playerDamage_UP_DOWN;
	
	
	private boolean hasGun = false;
	
	public int ammo; //0
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public boolean shoot = false;
	
	public double life = 100, maxlife = 100;
	
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		playerDamage_RIGHT = Game.spritesheet.getSprite(96, 32, 16, 16);
		playerDamage_LEFT = Game.spritesheet.getSprite(112, 32, 16, 16);
		playerDamage_UP_DOWN = Game.spritesheet.getSprite(128, 32, 16, 16);
		
		
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
			
		}
		
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
		
		for(int i = 0; i < 4; i ++) {
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 48, 16, 16);
		}
		
		for(int i = 0; i < 4; i ++) {
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16),32,16,16);
		}

	}
	
	public void tick() {
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			moved = true;
			dir = right_dir;
			x+= speed;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-= speed;
		}
		if(up && World.isFree(this.getX(), (int)(y -speed))) {
			moved = true;
			dir = up_dir;
			y-=speed;
		}
		else if(down && World.isFree(this.getX() , (int)(y +speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionWeapon();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(shoot) {
			shoot = false;
			if (hasGun && ammo > 0) {
				ammo--;
				int dx = 0;
				int dy = 0;
				int px = 2;
				int py = 10;
				
				if(dir == right_dir) {
					px = 10;
					dx = 1;
				}else if (dir == left_dir) {
					px = 0;
					dx = -1;
				}
				if(dir == up_dir) {
					px = 10;
					py = 3;
					dy = -1;
				}else if (dir == down_dir){
					px = 3;
					py = 15;
					dy = 1;
				}
				
				BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py, 3,3,null,dx,dy);
				Game.bullets.add(bullet);
			}
		}
		
		if(life <=0) {
			// Game over
			life = 0;
			Game.gameState = "GAME_OVER";
		}
		
		Camera.x =  Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkCollisionWeapon() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Weapon) {
				if(Entity.isColliding(this, current)) {
					hasGun = true;
					Game.entities.remove(current);
				}
			}
		}
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Bullet) {
				if(Entity.isColliding(this, current)) {
					ammo+=100;
					Game.entities.remove(current);
				}
			}
		}
	}
	
	public void checkCollisionLifePack() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof LifePack) {
				if(Entity.isColliding(this, current)) {
					if(life != 100) {
						life+=10;
						Game.entities.remove(current);
					}
					if(life > 100)
						life = 100;
				}
			}
		}
	}
	
	public void render(Graphics g) {

		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX()- Camera.x, this.getY()- Camera.y, null);
				if(hasGun) {
					g.drawImage(Entity.WEAPON_RIGHT, this.getX() - Camera.x + 3, this.getY() - Camera.y +2, null);
				}
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX()- Camera.x, this.getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(Entity.WEAPON_LEFT, this.getX() - Camera.x - 4, this.getY() - Camera.y + 2, null);
				}
			}
			if(dir == up_dir) {
				g.drawImage(upPlayer[index],this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(Entity.WEAPON_UP, this.getX() - Camera.x + 5, this.getY() - Camera.y - 5,null);
				}
			}else if(dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(Entity.WEAPON_DOWN, this.getX() - Camera.x -3, this.getY() - Camera.y + 4, null);
				}
			}
		}else {
			if(dir == right_dir) {
				g.drawImage(playerDamage_RIGHT, this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun)
					g.drawImage(Entity.Damage_Weapon_RIGHT, this.getX() - Camera.x + 3, this.getY() - Camera.y + 2, null);
			}else if(dir == left_dir) {
				g.drawImage(playerDamage_LEFT, this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun)
					g.drawImage(Entity.Damage_Weapon_LEFT, this.getX() - Camera.x - 4, this.getY() - Camera.y + 2, null);
			}
			if(dir == up_dir) { 
				g.drawImage(playerDamage_UP_DOWN, this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun)
					g.drawImage(Entity.Damage_Weapon_UP, this.getX() - Camera.x + 5, this.getY() - Camera.y -5, null);
			}else if(dir == down_dir) {
				g.drawImage(playerDamage_UP_DOWN, this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun)
					g.drawImage(Entity.Damage_Weapon_DOWN, this.getX() - Camera.x -3, this.getY() - Camera.y + 4, null);
			}
		}
	}

}
