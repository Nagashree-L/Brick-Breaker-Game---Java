package brickbreaker;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main 
{
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException 
	{
		JFrame frame = new JFrame();
		
		ImageIcon image = new ImageIcon("capsule_616x353.jpg");
		frame.setIconImage(image.getImage());
		
		
		Game game = new Game();
		
		
		//x-axis, y-axis, length, breadth
		frame.setBounds(290, 50, 700, 635);
		frame.setTitle("Brick Breaker Game");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(game);
		
		try (Scanner scanner = new Scanner(System.in)) 
		{
			File file = new File("D://Downloads//Energetic-Indie-Rock.wav");
			AudioInputStream audio = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		}
		
	}
}


