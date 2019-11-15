package com.tutorial.mario.entity;

import java.awt.Graphics;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.ID;


public class Coin extends Entity {



	public Coin(int x, int y, int width, int height, ID id, Handler handler) {
		super(x, y, width, height, id, handler);
	
	}

	@Override
	public void render(Graphics g) {
	g.drawImage(Game.coin.getBufferedImage(),x,y,width,height,null);
		
	}

	@Override
	public void tick() {
		
		
	}

}
