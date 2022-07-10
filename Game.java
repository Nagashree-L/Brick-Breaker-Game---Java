package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener,  ActionListener{

	private boolean play = false;
	private int score = 0;
	private int totalBricks = 28;
	private Timer timer;
	private int delay = 4;
	//initial paddle position in x axis(horizontally)
	private int paddleX = 310;
	//initial position of the ball
	private int ballposX = 330;
	private int ballposY = 526;
	private int balldirX = 1;
	private int balldirY = -2;
	
	private Bricks brick;
	
	public Game() 
	{
		brick = new Bricks(4,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}
	
	Image img = Toolkit.getDefaultToolkit().createImage("D://Desktop//spacebg.jpg");
	
	public void paint(Graphics g) 
	{
		//background
			g.drawImage(img, 0, 0, null);
			  
		//map drawing
			brick.draw((Graphics2D)g);
		
		//border
			g.setColor(Color.white);
			g.fillRect(0, 0, 3, 592);
			g.fillRect(0, 0, 692, 3);
			g.fillRect(681, 0, 3, 592);
			
		//scores
			g.setColor(Color.white);
			g.setFont(new Font("georgia", Font.BOLD, 25));
			g.drawString(""+score, 590, 30);
			
		//paddle
			g.setColor(new Color(189, 220, 255));
			g.fillRoundRect(paddleX-20, 550, 100, 10, 10, 15);
			
		//ball
			g.setColor(Color.white);
			g.fillOval(ballposX, ballposY, 20, 20);
			
		if(totalBricks <= 0)
		{
			play = false;
			balldirX = 0;
			balldirY = 0;
			
			//display at the end of the game
				g.setColor(Color.green);
				g.setFont(new Font("san-serif", Font.BOLD, 30));
				g.drawString("YOU WIN THE GAME!!", 250, 300);
				
			//display at the end of the game to restart	
				g.setColor(Color.white);
				g.setFont(new Font("san-serif", Font.BOLD, 20));
				g.drawString("Press ENTER to Restart", 250, 350);
		}
		
		if(ballposY > 570)
		{
			play = false;
			balldirX = 0;
			balldirY = 0;
			
			//display at the end of the game when you loose
				g.setColor(Color.red);
				g.setFont(new Font("san-serif", Font.BOLD, 30));
				g.drawString("Game Over, Scores: "+ score, 190, 300);
			
			//display at the end of the game to restart
				g.setColor(Color.white);
				g.setFont(new Font("san-serif", Font.BOLD, 20));
				g.drawString("Press ENTER to Restart", 230, 350);
		}
		
		g.dispose();
	}
	
	
	
	//action listener method when the ball is moving and intersects the brick or the paddle or wall
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		
		if(play) 
		{
			// intersection of rectangle of ball and paddle rectangle
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(paddleX, 550, 100, 8)))
			{
				balldirY = -balldirY;
			}
			
			A: for(int i = 0; i < brick.map.length; i++)
			{
				for(int j = 0; j < brick.map[0].length; j++)
				{
					//arrangement of bricks like a matrix
					if(brick.map[i][j] > 0)
					{
						//define the location of each brick
						int brickX = j * brick.brickWidth + 80;
						int brickY = i * brick.brickHeight + 50;
						int brickWidth = brick.brickWidth;
						int brickHeight = brick.brickHeight;
						//
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{
							brick.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)
							{
								balldirX = -balldirX;
							}
							else
							{
								balldirY = -balldirY;
							}
							break A;	//with respect to the for loop in line 128					
						}
					}
				}
			}
			
			ballposX += balldirX;
			ballposY += balldirY;
				if(ballposX < 0)
				{
					balldirX = -balldirX;
				}
				if(ballposY < 0)
				{
					balldirY = -balldirY;
				}
				if(ballposX > 670)
				{
					balldirX = -balldirX;
				}
			
		}
		
		repaint();
	}

	
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(paddleX >= 600)
			{
				paddleX = 600;
			}
			else
			{
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(paddleX < 10)
			{
				paddleX = 10;
			}
			else
			{
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(!play)
			{
				//Restart
				play = true;
				ballposX = 120;
				ballposY = 350;
				balldirX = -1;
				balldirY = -2;
				paddleX = 310;
				score = 0;
				totalBricks = 28;
				brick = new Bricks(4,7);
				repaint();
			}
		}
		
		
	}

	
	public void moveRight()
	{
		play = true;
		paddleX+=20;
	}
	
	
	public void moveLeft()
	{
		play = true;
		paddleX-=20;
	}
	

}
