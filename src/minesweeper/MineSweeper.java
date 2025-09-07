package minesweeper;

import doodlepad.Image;
import doodlepad.Pad;
import doodlepad.Sound;

public class MineSweeper extends Pad{
	
	private Cell[][] cells;
	private final int MINES;
	private static final int PAD_WIDTH=576;
	
	public MineSweeper(int k) {
		super(PAD_WIDTH, PAD_WIDTH);
		if (k==1) {
			cells = new Cell[9][9];
			MINES = 10;
		}
		else {
			cells = new Cell[16][16];
			MINES = 35;
		}
		setUp();
	}
		
	public void fillArray() {
		for (int r = 0; r < cells.length; r++)
			for (int c = 0; c < cells[0].length; c++)
				cells[r][c] = new Cell(r, c, PAD_WIDTH/cells.length, this);
	}
	
	public void placeMines() {
		for (int i = 0; i < MINES; i++) {
			int r = (int)(Math.random()*cells.length);
			int c = (int)(Math.random()*cells[0].length);
			while (cells[r][c].isMine()) {
				r = (int)(Math.random()*cells.length);
				c = (int)(Math.random()*cells[0].length);
			}
			cells[r][c].setMine();
			Helper.setAdjacentCounts(r, c, this); 
		}
	}
	
	public void setUp() {
		fillArray();
		placeMines();
	}
	
	public Cell[][] getCells() {return cells;}
	
	public boolean inBounds(int r, int c) {
		return r < cells.length && c < cells[0].length && r >= 0 && c >= 0;
	}
	
	public int getNumSafeCells() {
		int c = 0;
		for (Cell[] e: cells)
			for (Cell r: e)
				if (!r.isMine())
					c++;
		return c;
	}
	
	public void gameOver(boolean lost) {
        setEventsEnabled(false);
		if(lost) {
			Sound s = new Sound("gameover.wav");
			s.play();
			new Image("bomb.png",0,0,PAD_WIDTH,PAD_WIDTH);
		}
		else {
			Sound s = new Sound("winner.wav");
			s.play(); 
		}   
	}
	
	public static void main(String[] args) {
		int x = 2; // 1 for easy, 2 for hard
		new MineSweeper(x);
	}
	
	
}
