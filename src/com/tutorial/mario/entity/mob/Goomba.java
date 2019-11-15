package com.tutorial.mario.entity.mob;

import java.awt.Graphics;
import java.util.Random;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.ID;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;

public class Goomba extends Entity {
	private Random random = new Random();
	private int frame = 0;

	private int frameDelay = 0;

	public Goomba(int x, int y, int width, int height, ID id,
			Handler handler) {
		super(x, y, width, height, id, handler);
		
		int dir = random.nextInt(2);

		switch (dir) {

		case 0:
			setVelX(-1);
			facing=0;
			break;
		case 1:
			setVelX(1);
			facing =1;
			break;
		}

	}

	@Override
	public void render(Graphics g) {
		if (facing == 0) {
			g.drawImage(Game.goomba[frame + 4].getBufferedImage(), x, y, width,
					height, null);
		} else if (facing == 1) {
			g.drawImage(Game.goomba[frame].getBufferedImage(), x, y, width,
					height, null);
		}

	}

	@Override
	public void tick() {
		x += velX;
		y += velY;

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
				if(velX!=0){
					frameDelay++;
					if(frameDelay>=3){
						
						frame++;
						if(frame>=5){
							frame=0;
						}
						frameDelay =0;
					}	
					}

			}
			if (getBoundsLeft().intersects(t.getBounds())) {

				setVelX(5);
				facing=1;

			}
			if (getBoundsRight().intersects(t.getBounds())) {

				setVelX(-5);
				facing=0;

			}
		}

		if (falling) {
			gravity += 0.1;
			setVelY((int) gravity);

		}

	}

}
