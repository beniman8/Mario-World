package com.tutorial.mario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.tutorial.mario.entity.Coin;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.entity.TowerBoss;
import com.tutorial.mario.entity.mob.Koopa;
import com.tutorial.mario.entity.mob.Player;
import com.tutorial.mario.tile.Pipe;
import com.tutorial.mario.tile.PowerUpBlock;
import com.tutorial.mario.tile.Tile;
import com.tutorial.mario.tile.Wall;

public class Handler {

	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();

	public void render(Graphics g) {

		for(int i=0;i<entity.size();i++){
			Entity e = entity.get(i);
			if(Game.getVisibleArea()!=null&&e.getBounds().intersects(Game.getVisibleArea()))e.render(g);
		}
		for(int i=0;i<tile.size();i++){
			Tile ti = tile.get(i);

			if(Game.getVisibleArea()!=null&&ti.getBounds().intersects(Game.getVisibleArea()))ti.render(g);
		}

	}

	public void tick() {
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).tick();
		}
		for (int i = 0; i < tile.size(); i++) {
			if(Game.getVisibleArea()!=null&&tile.get(i).getBounds().intersects(Game.getVisibleArea()))tile.get(i).tick();
		}
	}

	public void addEntity(Entity en) {

		entity.add(en); // this adds the entity to our linked list
	}

	public void removeEntity(Entity en) {
		entity.remove(en);
	}

	public void addTile(Tile ti) {

		tile.add(ti); // this adds the Tiles to our linked list
	}

	public void removeTile(Tile ti) {
		tile.remove(ti);
	}

	public void createLevel(BufferedImage level) {

		int width = level.getWidth();
		int height = level.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = level.getRGB(x, y);

				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				if (red == 0 && green == 0 && blue == 0)
					addTile(new Wall(x * 64, y * 64, 64, 64, true, ID.wall,
							this));
				if (red == 0 && green == 0 && blue == 255)
					addEntity(new Player(x * 64, y * 64, 64, 64,
							ID.player, this));
			
				if (red == 205 && green == 106 && blue == 0)
					addEntity(new Koopa(x * 64, y * 64, 64, 64,
							ID.koopa, this));
				if (red == 255 && green == 0 && blue == 220)
					addTile(new PowerUpBlock(x * 64, y * 64, 64, 64,true,
							ID.powerUp, this,Game.upMushroom,1));
				if (red == 0 && (green>123&&green<129) && blue == 0)
					addTile(new Pipe(x * 64, y * 64, 64, 64,true,
							ID.pipe, this,128-green));
				if (red == 255 && green==255 && blue == 0)
					addEntity(new Coin(x * 64, y * 64, 64, 64,ID.coins, this));
				if (red == 0 && green==255 && blue == 100)
					addEntity(new TowerBoss(x * 64, y * 64, 64, 64,ID.towerBoss,this, 3));

			}

		}

	}
	
	public void clearLevel(){
		entity.clear();
		tile.clear();
	}
}
