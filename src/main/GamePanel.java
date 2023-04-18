package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics; //can draw many
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int orginalTileSize = 16; // 16x16 tile (standard size for many retro 2D games + characters)
	final int scale = 3;

	public final int tileSize = orginalTileSize * scale; // 48x48 title
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;// 768 pixels
	public final int screenHeight = tileSize * maxScreenRow;// 576 pixels

	
	//WORLD SETTING
	
			public final int maxWorldCol = 50;
			public final int maxWorldRow = 50;

	// FPS
	int FPS = 60;
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Sound sound = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	Thread gameThread; // Thread - allow user to start and stop, when it start then it keep running
						// until user stop
	
	
	// ENTITY AND OBJ
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10]; //10 slots of object
	
	
	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true); // this GamePanel can be "focused" to receive key input
	}
	
	public void setupGame() { //call before this game start vv
		
		aSetter.setObject();
		
		playMusic(0);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >=1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS:"+drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {

		player.update();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g); // Applying Graphics to JPanel (super: parent class)

		Graphics2D g2 = (Graphics2D) g; // Change to 2D Graphic
		
		tileM.draw(g2);
		
		for(int i = 0; i < obj.length; i++) { //because too many items
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}

		player.draw(g2);

		g2.dispose(); // dispose of this graphics context and release any system resources that it is
						// using

	}
	public void playMusic(int i) {
		
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	public void stopMusic() {
		
		sound.stop();
	}
	public void playSE(int i) {
		
		sound.setFile(i);
		sound.play();
	}
}
