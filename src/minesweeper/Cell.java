package minesweeper;

import java.awt.Color;
import doodlepad.Rectangle;

public class Cell extends Rectangle{
	
	private int row, column, adjacentMines;
	private MineSweeper board;
	private boolean isMine;
	private static int safe;
	
	public Cell(int r, int c, int w, MineSweeper m) {
		super(r*w, c*w, w, w);
		board = m;
		row=r;
		column=c;
		setFillColor(Color.BLUE);
		setStrokeColor(Color.BLACK);
	}
	
	public void setMine() {isMine=true;}
	public boolean isMine() {return isMine;}
	public void incrementCount() {adjacentMines++;}
	
	public void rightClick() {
		if (getFillColor().equals(Color.BLUE)) {
			setFillColor(Color.ORANGE);
			setText("F");
		}
		else if (!getFillColor().equals(Color.WHITE)){
			setFillColor(Color.BLUE);
			setText(" ");
		}
	}
	
	public void padClick() {
		if (isMine())
			board.gameOver(true);
		else {
			if (getFillColor().equals(Color.BLUE)) {
				setFillColor(Color.WHITE);
				safe++;
			}
			if (safe==board.getNumSafeCells()) {
				board.gameOver(false);
				setText("" + adjacentMines);
			}
			else if (adjacentMines==0)
				Helper.sweep(row, column, board); 
			else
				setText("" + adjacentMines);
			}
	}
	
	public void onMouseClicked(double x, double y, int b) {
		if (b==3)
			rightClick();
		else
			padClick();
	}
}
