package othello;

import java.awt.Color;

import doodlepad.Oval;
import doodlepad.Text;

public class Othello extends Board{

	private Oval[][] ovals;
	private int amtPieces;
	private static final int SIZE=6;
	
	public Othello() {
		super(SIZE, SIZE);
		drawGrid();
		setBackground(50, 100, 50);
		ovals = new Oval[SIZE][SIZE];
		placePiece(SIZE/2-1, SIZE/2-1);
		placePiece(SIZE/2-1, SIZE/2);
		placePiece(SIZE/2, SIZE/2);
		placePiece(SIZE/2, SIZE/2-1);
	}

	public void placePiece(int row, int col) {
		if (ovals[row][col]==null) {
			ovals[row][col] = new Oval(getColWidth()*col, getColWidth()*row, getPieceSize(), getPieceSize());
			if (amtPieces%2==0)
				ovals[row][col].setFillColor(Color.BLACK);
			else
				ovals[row][col].setFillColor(Color.WHITE);
			amtPieces++;
			flip(row, col);
		if (amtPieces==SIZE*SIZE)
			gameOver();
		}
	}

	public void onMouseClicked(double x,double y,int button) {
		placePiece((int)(y/getColWidth()), (int)(x/getColWidth()));
	}

	public void gameOver() {
		setEventsEnabled(false);//Turns off clicking
		int w = 0;
		for (Oval[] a: ovals)
			for (Oval i: a)
				if (i.getFillColor().equals(Color.WHITE))
						w++;
		Text t = new Text("", 100, 100, 100);
		t.setFillColor(Color.RED);
		if (w>SIZE*SIZE/2)
			t.setText("White Wins");
		else if (w==SIZE*SIZE/2)
			t.setText("Tie Game");
		else
			t.setText("Black Wins");
	}
	
	public void changeR(int start, int end, int col, Color c) {
		for (int i = start; i < end; i++)
			ovals[i][col].setFillColor(c);
	}
	
	public void changeC(int start, int end, int row, Color c) {
		for (int i = start; i < end; i++)
			ovals[row][i].setFillColor(c);
	}

	public void vertical(int row, int col) {
		Color c = ovals[row][col].getFillColor();
		int i = 1;
		while(row+i < SIZE && ovals[row+i][col]!=null && !(ovals[row+i][col].getFillColor().equals(c)))
			i++;
		if (row+i < SIZE && ovals[row+i][col]!=null && ovals[row+i][col].getFillColor().equals(c))
			changeR(row, row+i, col, c);
		i = 1;
		while(row-i > -1 && ovals[row-i][col]!=null && !(ovals[row-i][col].getFillColor().equals(c)))
			i++; 
		if (row-i > -1 && ovals[row-i][col]!=null && ovals[row-i][col].getFillColor().equals(c))
			changeR(row-i, row, col, c);
		}

	public void horizontal(int row, int col) {
		Color c = ovals[row][col].getFillColor();
		int i = 1;
		while(col+i < SIZE && ovals[row][col+i]!=null && !(ovals[row][col+i].getFillColor().equals(c)))
			i++;
		if (col+i < SIZE && ovals[row][col+i]!=null && ovals[row][col+i].getFillColor().equals(c))
			changeC(col, col+i, row, c);
		i = 1;
		while(col-i > -1 && ovals[row][col-i]!=null && !(ovals[row][col-i].getFillColor().equals(c)))
			i++; 
		if (col-i > -1 && ovals[row][col-i]!=null && ovals[row][col-i].getFillColor().equals(c))
			changeC(col-i, col, row, c);
	}

	public void diagonal(int row, int col){
		Color c = ovals[row][col].getFillColor();
		
		// Top-left to bottom-right
		int i = 1;
		while (row + i < SIZE && col + i < SIZE && ovals[row + i][col + i] != null 
			&& !ovals[row + i][col + i].getFillColor().equals(c))
			i++;
		if (row + i < SIZE && col + i < SIZE && ovals[row + i][col + i] != null 
			&& ovals[row + i][col + i].getFillColor().equals(c))
			for (int j = 1; j < i; j++)
				ovals[row + j][col + j].setFillColor(c);

		i = 1;
		while (row - i >= 0 && col - i >= 0 && ovals[row - i][col - i] != null 
			&& !ovals[row - i][col - i].getFillColor().equals(c))
			i++;
		if (row - i >= 0 && col - i >= 0 && ovals[row - i][col - i] != null 
			&& ovals[row - i][col - i].getFillColor().equals(c))
			for (int j = 1; j < i; j++)
				ovals[row - j][col - j].setFillColor(c);

		// Top-right to bottom-left
		i = 1;
		while (row + i < SIZE && col - i >= 0 && ovals[row + i][col - i] != null 
			&& !ovals[row + i][col - i].getFillColor().equals(c))
			i++;
		if (row + i < SIZE && col - i >= 0 && ovals[row + i][col - i] != null 
			&& ovals[row + i][col - i].getFillColor().equals(c))
			for (int j = 1; j < i; j++)
				ovals[row + j][col - j].setFillColor(c);

		i = 1;
		while (row - i >= 0 && col + i < SIZE && ovals[row - i][col + i] != null 
			&& !ovals[row - i][col + i].getFillColor().equals(c))
			i++;
		if (row - i >= 0 && col + i < SIZE && ovals[row - i][col + i] != null 
			&& ovals[row - i][col + i].getFillColor().equals(c))
			for (int j = 1; j < i; j++)
				ovals[row - j][col + j].setFillColor(c);
	}

	public void flip(int row, int col) {
		vertical(row, col);
		horizontal(row, col);
		diagonal(row, col);
	}
	
	public static void main(String[] args) {
		new Othello();
	}
}

