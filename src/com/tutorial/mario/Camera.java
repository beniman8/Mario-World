package com.tutorial.mario;

import com.tutorial.mario.entity.Entity;

public class Camera {
	
	public int x,y;

	public void tick(Entity player){
		
		setX(-player.getX()+Game.WIDTH/2+250);
		setY(-player.getY()+Game.HEIGHT/2+210);
		
		
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
	

}
