package com.tutorial.mario.tile;


import java.awt.Graphics;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.ID;

public class Wall extends Tile{

	public Wall(int x, int y, int width, int height, boolean solid, ID id,
			Handler handler) {
		super(x, y, width, height, solid, id, handler);
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(Game.grass.getBufferedImage(),x,y,width,height,null);
		
	}

	@Override
	public void tick() {
		
		
	}

}
