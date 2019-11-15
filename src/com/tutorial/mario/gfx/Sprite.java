package com.tutorial.mario.gfx;

import java.awt.image.BufferedImage;

public class Sprite {
	
	public SpriteSheet sheet;
	
	public BufferedImage image;
	
	public Sprite(SpriteSheet sheet,int x , int y){
		
		image = sheet.getSprite(x,y);
		
		
	}
	
	public BufferedImage getBufferedImage(){// this is to render our sprite that is a buffered image
		
		return image;
	}

}
