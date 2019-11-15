package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.tutorial.mario.Game;
import com.tutorial.mario.ID;
import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.tile.Tile;

public class KeyInput implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		for (Entity en : Game.handler.entity) {
			if (en.getId() == ID.player) {
				if (en.goingDownPipe)
					return;
				switch (key) {
				case KeyEvent.VK_W:

					for (int q = 0; q < Game.handler.tile.size(); q++) {

						Tile t = Game.handler.tile.get(q);
						if (t.isSolid()) {

							if (en.getBoundsBottom().intersects(t.getBounds())) {

						
								if (!en.jumping) {
									en.jumping = true;
									en.gravity = 7.0;
								}
							}

						}
					}

					break;
				case KeyEvent.VK_S:
					for (int q = 0; q < Game.handler.tile.size(); q++) {

						Tile t = Game.handler.tile.get(q);
						if (t.getId() == ID.pipe) {

							if (en.getBoundsBottom().intersects(t.getBounds())) {

								if (!en.goingDownPipe)
									en.goingDownPipe = true;

							}

						}
					}

					break;
				case KeyEvent.VK_A:
					en.setVelX(-5);
					en.facing = 0;
					break;
				case KeyEvent.VK_D:
					en.setVelX(5);
					en.facing = 1;
					break;
				case KeyEvent.VK_UP:
					// en.setVelY(-5);
					if (!en.jumping) {
						en.jumping = true;
						en.gravity = 7.0;
					}
					break;
				case KeyEvent.VK_DOWN:
					for (int q = 0; q < Game.handler.tile.size(); q++) {

						Tile t = Game.handler.tile.get(q);
						if (t.getId() == ID.pipe) {

							if (en.getBoundsBottom().intersects(t.getBounds())) {

								if (!en.goingDownPipe)
									en.goingDownPipe = true;

							}

						}
					}

					break;
				case KeyEvent.VK_LEFT:
					en.setVelX(-5);
					en.facing = 0;
					break;
				case KeyEvent.VK_RIGHT:
					en.setVelX(5);
					en.facing = 1;
					break;
				default:
				}
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (Entity en : Game.handler.entity) {
			if (en.getId() == ID.player) {
				switch (key) {
				case KeyEvent.VK_W:
					en.setVelY(0);
					break;
				case KeyEvent.VK_A:
					en.setVelX(0);
					break;
				case KeyEvent.VK_D:
					en.setVelX(0);
					break;
				case KeyEvent.VK_UP:
					en.setVelY(0);
					break;
				case KeyEvent.VK_LEFT:
					en.setVelX(0);
					break;
				case KeyEvent.VK_RIGHT:
					en.setVelX(0);
					break;
				default:
				}

			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// not used

	}

}
