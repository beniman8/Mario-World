package com.tutorial.mario.entity.mob;

//import java.awt.Color;
import java.awt.Graphics;

import com.tutorial.mario.Game;
import com.tutorial.mario.Handler;
import com.tutorial.mario.ID;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.state.BossState;
import com.tutorial.mario.states.PlayerState;
import com.tutorial.mario.tile.Tile;

public class Player extends Entity {

	private PlayerState state;

	private int pixelsTravelled = 0;

	private int frame = 0;

	private int frameDelay = 0;

	public Player(int x, int y, int width, int height, ID id, Handler handler) {
		super(x, y, width, height, id, handler);
		state = PlayerState.SMALL;

	}

	@Override
	public void render(Graphics g) {

		// g.setColor(Color.blue);
		// g.fillRect(x, y, width, height);
		if (facing == 0) {
			g.drawImage(Game.player[frame + 5].getBufferedImage(), x, y, width,
					height, null);
		} else if (facing == 1) {
			g.drawImage(Game.player[frame].getBufferedImage(), x, y, width,
					height, null);
		}

	}

	@Override
	public void tick() {

		x += velX;
		y += velY;



		for (int i = 0; i < handler.tile.size(); i++) {
			Tile t = handler.tile.get(i);
			// COLLISION METHOD AGAINST A WALL
			if (t.isSolid() && !goingDownPipe) {
				if (getBoundsTop().intersects(t.getBounds())) {

					setVelY(0);
					if (jumping&&!goingDownPipe) {
						jumping = false;
						gravity = 0.8;
						falling = true;
					}
				}
				if (t.getId() == ID.powerUp) {
					if (getBoundsTop().intersects(t.getBounds())) {
						t.activated = true;
					}
				}

				// y = t.getY() + t.height;

				if (getBoundsBottom().intersects(t.getBounds())) {

					setVelY(0);
					if (falling)
						falling = false;
					else if (!falling && !jumping) {
						gravity = 0.8;
						falling = true;
					}

					// y = t.getY() - t.height€;
				}
				if (getBoundsLeft().intersects(t.getBounds())) {

					setVelX(0);
					x = t.getX() + t.width;
				}
				if (getBoundsRight().intersects(t.getBounds())) {

					setVelX(0);
					x = t.getX() - t.width;
				}
			
			}

		}

		for (int i = 0; i < handler.entity.size(); i++) {

			Entity e = handler.entity.get(i);
			

			if (e.getId() == ID.mushroom) {
switch(e.getType()){
			case 0:
				if (getBounds().intersects(e.getBounds())) {
					int tpX = getX();
					int tpY = getY();

					width += (width/3);
					height += (height/3);

					setX(tpX - width);
					setY(tpY - height);
					if (state == PlayerState.SMALL)
						state = PlayerState.BIG;
					e.die();
					}
				break;
			case 1:
				if(getBounds().intersects(e.getBounds())){
					Game.lives++;
					e.die();
					
					
				}
			
				break;
				}

			} else if (e.getId() == ID.goomba||e.getId()==ID.towerBoss) {
				if (getBoundsBottom().intersects(e.getBoundsTop())) {
					if(e.getId()!=ID.towerBoss)e.die();
					else if(e.attackable){
						e.HP--;
						e.falling=true;
						e.gravity=3.0;
						e.bossState=BossState.RECOVERING;
						e.attackable=false;
						e.phaseTime=0;
						
						jumping = true;
						falling=false;
						gravity=3.5;
					}
				} else if (getBounds().intersects(e.getBounds())) {
					if (state == PlayerState.BIG) {
						state = PlayerState.SMALL;
						width /= 3;
						height /= 3;
						x += width;
						y += height;
					} else if (state == PlayerState.SMALL) {
						die();
					}
				}

			}else if(e.getId()==ID.coins){
	if(getBoundsRight().intersects(e.getBounds())&&e.getId()==ID.coins){
					
					Game.coins++;
					e.die();
				}
			}
		}

		if (jumping && !goingDownPipe) {

			gravity -= 0.17;
			setVelY((int) -gravity); // cast gravity into an integer

			if (gravity <= 0.5) {
				jumping = false;
				falling = true;
			}

		}
		if (falling && !goingDownPipe) {
			gravity += 0.17;
			setVelY((int) gravity);

		}
		if (velX != 0) {
			frameDelay++;
			if (frameDelay >= 10) {

				frame++;
				if (frame >= 3) {
					frame = 0;
				}
				frameDelay = 0;
			}
		}

		if (goingDownPipe) {
			for (int i = 0; i < Game.handler.tile.size(); i++) {
				Tile t = Game.handler.tile.get(i);
				if (t.getId() == ID.pipe) {
					
						switch (t.facing) {

						case 0:
							setVelY(-5);
							setVelX(0);
							pixelsTravelled+=-velY;
							break;

						case 2:
							setVelY(5);
							setVelX(0);
							pixelsTravelled+=-velY;
							break;
						}

						//if (pixelsTravelled > t.height*2+height ) {
						//	goingDownPipe = false;
							
						//}
					}

				
			}
		}

	}

}
