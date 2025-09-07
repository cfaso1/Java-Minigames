package snake;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import doodlepad.Oval;
import doodlepad.Pad;
import doodlepad.Text;

public class Snake extends Pad{
	
	private ArrayList<Oval> body;
	private Oval head;
	private Oval orange;
	private int orangesEaten;
	private Boolean headRight, headLeft, headUp, headDown;
	private static final int WIDTH = 30;
	
	public Snake(Color c) {
		super(500, 500);
		setBackground(93, 200, 0);
		body = new ArrayList<Oval>();
		makeSnake(c);
		makeOrange();
		headRight=true;
	}
	
	public void makeSnake(Color c) {
		for (int i = 0; i < 4; i++) {
			body.add(new Oval(100+(i*WIDTH), 100, WIDTH, WIDTH));
			body.get(i).setFillColor(c);
		}
		head = new Oval(220, 100, WIDTH, WIDTH);
		head.setFillColor(0, 0, 100);
	} 
	
	public boolean touchOrange() {
		return head.intersects(orange);
	}
	
	public boolean ateOrange() {
		removeShape(orange);
		makeOrange();
		orangesEaten++;
		return true;
	}
	
	public void makeOrange() {
		orange = new Oval((int)(Math.random()*500), (int)(Math.random()*500), WIDTH, WIDTH);
		orange.setFillColor(Color.ORANGE);
	}
	
	public void moveSnake(int changeX,int changeY) {
		head.setX(changeX+head.getX());
		head.setY(changeY+head.getY());
		removeShape(body.get(0));
		body.add(new Oval(head.getX()-changeX, head.getY()-changeY, WIDTH, WIDTH));
		body.get(body.size()-1).setFillColor(93, 200, 253);
		if (touchOrange())
			ateOrange();
		else
			body.remove(0);
		for (int i = 0; i < body.size()-1; i++)
			if (head.intersects(body.get(i)))
				head.setWidth(30.01);
	} 
	
	public void moveRight() {moveSnake(WIDTH, 0);}
	public void moveLeft() {moveSnake(WIDTH*-1, 0);}
	public void moveUp() {moveSnake(0, WIDTH*-1);}
	public void moveDown() {moveSnake(0, WIDTH);}
	
	public boolean gameOver() {return head.getWidth()!=30 || (head.getX() > 500 || head.getX() < 0) || (head.getY() > 500 || head.getY() < 0);}
	
	public void onKeyPressed(String keyText,String e) {
		headRight=false;
		headLeft=false;
		headUp=false;
		headDown=false;
		if (keyText.equals("Up")) headUp=true;
		else if (keyText.equals("Down")) headDown=true;
		else if (keyText.equals("Right")) headRight=true;
		else if (keyText.equals("Left")) headLeft=true;
	}
	
	public void move(Clip clip) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException{
        while(!gameOver()) {
            if(headRight) moveRight();
            else if(headLeft) moveLeft();
            else if(headUp) moveUp();
            else if(headDown) moveDown();
            TimeUnit.MILLISECONDS.sleep(250);
        }
      //ends theme music and plays game over sound after snake dies
        clip.stop();
        clip.close();
        File gameOverPath = new File("assets/gameover.wav");
        AudioInputStream s2 = AudioSystem.getAudioInputStream(gameOverPath);
        clip.open(s2);
        clip.start();

        //waits 6 seconds after playing game over sound, then ends all audio objects
        TimeUnit.SECONDS.sleep(6);
        clip.stop();
        clip.close();
        setEventsEnabled(false);
		Text t;
		if (orangesEaten == 1)
			t = new Text("1 ORANGE EATEN!",30,50,50);
		else
			t = new Text(orangesEaten +" ORANGES EATEN!",30,50,50);
		addShape(t);

	}

    public static void main(String[] args) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        //theme music setup
        Clip clip = AudioSystem.getClip();
        File themePath = new File("assets/theme.wav");
        AudioInputStream s1 = AudioSystem.getAudioInputStream(themePath);
        clip.open(s1);
        clip.start();

        //starts the game
        Color bodyC = new Color(93, 200, 253);
        Snake game = new Snake(bodyC);
        game.move(clip);

        
    }

}
