package com.tutorial.mario.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.ID;
import com.tutorial.mario.state.BossState;

public abstract class Entity {

	public int x, y;

	public int width, height;
	
	public int facing =0;// 0 - left, 1-right
	public int HP;
	public int phaseTime;
	
	public BossState bossState;

	
	public boolean jumping =false;
	
	public boolean falling = true;
	
	public boolean goingDownPipe=false;
	
	public boolean attackable = false;
	public int type;

	public int velX, velY;

	public ID id;
	
	public double gravity =0.0;

	public Handler handler;

	public Entity(int x, int y, int width, int height, ID id,
			Handler handler) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.id = id;
		this.handler = handler;

	}

	public abstract void render(Graphics g);

	public abstract void tick();

	public void die() {
		handler.removeEntity(this);
		
		if(getId()==ID.player){
		Game.lives--;
		Game.showDeathScreen=true;
		
		if(Game.lives<=0 )Game.gameOver=true;	
		}
		

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


	public int getVelX() {
		return velX;
	}

	public ID getId() {
		return id;

	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public Rectangle getBounds() {

		return new Rectangle(getX(), getY(), width, height);
	}

	public Rectangle getBoundsTop() {

		return new Rectangle(getX() + 10, getY(), width - 20, 5);
	}

	public Rectangle getBoundsBottom() {

		return new Rectangle(getX() + 10, getY() + height - 5, width - 20, 5);
	}

	public Rectangle getBoundsLeft() {

		return new Rectangle(getX(), getY() + 10, 5, height - 20);
	}

	public Rectangle getBoundsRight() {

		return new Rectangle(getX() + width - 5, getY() + 10, 5, height - 20);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
