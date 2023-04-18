package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX; //minus player.worldX because player always at the center of all the map so minus for any tiles outside become out of camera
		int screenY = worldY - gp.player.worldY + gp.player.screenY; //add player.screen because when player always at the center of camera so when it move to tile[0][0] then it become tile[center][center]
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
			
			
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
