package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

public class Ball {

	public int height, width;
	public double x, y;
	
	public double dx, dy;
	public double speed = 1.6;
	
	int angle = new Random().nextInt(120 - 45) + 45;
	
	public Ball(double x, double y) {
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = 4;
		

		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}
	
	public void tick() throws IOException {
		if (x + (dx * speed) >= Game.WIDTH) {
			dx*=-1;
			speed+=0.1;
		} else if(x + (dx * speed) < 0) {
			dx*=-1;
			speed+=0.1;
		}
		
		if (y >= Game.HEIGHT) {
			if (!Partida.iniciada) {
				if (Partida.pontosInimigo < 2) {
					Partida.pontosInimigo++;
	 	   			new Game();
		   			return;
				} else {
					new Partida(10, 0, true);
					return;
				}
			}
		} else if(y <= 0) {
			if (!Partida.iniciada) {
				if (Partida.pontos < 2) {
					Partida.pontos++;
					new Game();
				} else {				
					new Partida(0, 10, true);
					return;
				}
				return;
			}
		}
			
		x += dx * speed;
		y += dy * speed;

		Rectangle bounds = 
			new Rectangle((int)(x + (dx * speed)), (int)(y + (dy * speed)), width, height);
		
		Rectangle boundsPlayer = 
			new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
		
		Rectangle boundsEnemy = 
				new Rectangle((int) Game.enemy.x, (int) Game.enemy.y, Game.enemy.width, Game.enemy.height);
		
		if(bounds.intersects(boundsPlayer)) {
			int angle = new Random().nextInt(this.angle / 2) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			speed += 0.1;
			
			if(dy > 0) {
				dy *= -1;
			}
		} else if(bounds.intersects(boundsEnemy)) {
			int angle = new Random().nextInt(120 - 45) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			speed += 0.1;
			
			if(dy < 0) {
				dy *= -1;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillOval((int) x, (int) y, width, height);
	}

}
