package com.tutorial.mario.entity.powerup;

import java.awt.Graphics;
import java.util.Random;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.ID;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;

public class Mushroom extends Entity {

	private Random random = new Random();

	public Mushroom(int x, int y, int width, int height, ID id,
			Handler handler ,int type) {
		super(x, y, width, height, id, handler);
		
		this.type=type;
		

		int dir = random.nextInt(2);

		switch (dir) {

		case 0:
			setVelX(-1);
			break;
		case 1:
			setVelX(1);
			break;
		}

	}

	@Override
	public void render(Graphics g) {
		switch(getType()){
		case 0:
			g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
			break;
		case 1:
			g.drawImage(Game.upMushroom.getBufferedImage(), x, y, width, height, null);
			break;
		}

		

	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;

		for (int i = 0; i < handler.tile.size(); i++) {
			Tile t = handler.tile.get(i);

			if (getBoundsBottom().intersects(t.getBounds())) {

				setVelY(0);
				if (falling)
					falling = false;
				else if (!falling) {
					falling = true;
					gravity = 0.8;
				}

			}
			if (getBoundsLeft().intersects(t.getBounds())) {

				setVelX(5);

			}
			if (getBoundsRight().intersects(t.getBounds())) {

				setVelX(-5);

			}
		}

		if (falling) {
			gravity += 0.1;
			setVelY((int) gravity);

		}

	}

}
