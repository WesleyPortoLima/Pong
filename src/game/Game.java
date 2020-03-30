package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	private static final long serialVersionUID = 713019536602837144L;
	
	public static int WIDTH = 200;
	public static int HEIGHT = 200;
	public static int SCALE = 3;
	
	public static int pontosInimigo = 0, pontos = 0;
	
	public BufferedImage layer; 
	
	public static Player player;
	public static Enemy enemy;
	public static Ball ball;
	
	public Game() throws IOException {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.addKeyListener(this);
		player = new Player(85, HEIGHT - 5);
		enemy = new Enemy(85, 0);
		ball = new Ball(85, HEIGHT/2 - 1);
		
		layer = 
			new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}

	public static void main(String[] args) throws IOException {
		Game game = new Game();
		JFrame frame= new JFrame("Pong");
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		new Thread(game).start();
	}
	
	public void tick() throws IOException {
		player.tick();
		enemy.tick();
		ball.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = layer.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD, 10));

		if (Partida.pontos == 10) {
			g.drawString("Você Venceu", 72, 90);
			g.drawString("Aperte R para recomeçar", 47, 110);
		} else if (Partida.pontosInimigo == 10) {
			g.drawString("O Inimigo Venceu", 60, 90);
			g.drawString("Aperte R para recomeçar", 43, 110);
		}
		
		player.render(g);
		enemy.render(g);
		ball.render(g);
		
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		bs.show();
	}
	
	@Override
	public void run() {
		while(true) {
			requestFocus();
			try {
				tick();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R) {
			new Partida(0, 0, false);
			try {
				new Game();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
