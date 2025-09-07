package connectfour;

import java.awt.Color;
import doodlepad.Oval;
import doodlepad.Text;

public class ConnectFour extends Board{
	private Oval[][] ovals;
	private int pieces;
	private boolean computer;

	public ConnectFour(boolean computer){
		super(7, 6);
		drawGrid();
		ovals = new Oval[6][7];
		this.computer = computer;
	}

	public boolean placePiece(int col) {
		for (int r = ovals.length-1; r >= 0; r--)
			if (ovals[r][col]==null) {
				ovals[r][col]= new Oval(col*getColWidth(), r*getColWidth(), getPieceSize(), getPieceSize());
				if (pieces%2==0)
					ovals[r][col].setFillColor(Color.BLUE);
				else
					ovals[r][col].setFillColor(Color.RED);
				if (gameOver(r, col)) 
					return true;
				pieces++;
				if (pieces%2==1 && computer)
					computerTurn();
				return true;
			}
		return false;			
	}
	
	public void computerTurn() {		
		boolean placed = false;
		while(!placed)
			placed=placePiece((int)(Math.random()*ovals[0].length));	
		}
		
	public void onMouseClicked(double x,double y,int button) {	
		if (!placePiece((int)(x/getColWidth())))
			System.out.println("Column Full");
	}

	public void endGame(Color c) {
		setEventsEnabled(false); 
	}
		
	public boolean gameOver(int row, int col) {
		Color c = Color.BLACK;
		String color;
		if (pieces%2==0) {
			c=Color.BLUE;
			color="Blue";
		}
		else {
			c=Color.RED;
			color="Red";
		}
		if (pieces==41) {
			Text t = new Text("No Winner", 100, 100, 100);
			t.setFillColor(Color.BLACK);
			endGame(c);
			return true;
		}
		if (horizontalWin(row, c) || verticalWin(col, c) || diagonalWin(c)) {
			String s = color +" Wins!";
			Text t = new Text(s, 100, 100, 100);
			t.setFillColor(c);
			endGame(c);
			return true;
		}
		return false;
	 }
	
	public boolean horizontalWin(int row, Color color) {
		int count = 0;
		for (int c = 0; c < ovals[0].length; c++)
			if (ovals[row][c]!=null && ovals[row][c].getFillColor().equals(color)) {
				count++;
				if (count==4)
					return true;
			}
			else
				count = 0;
		return false;
	}
			
	public boolean verticalWin(int col,Color color) {
		int c = 0;
		for (int r = 0; r < ovals.length; r++)
			if (ovals[r][col]!=null && ovals[r][col].getFillColor().equals(color)) {
				c++;
				if (c==4)
					return true;
			}
			else
				c = 0;
		return false;
	}

	public boolean diagonalWin(Color color) {
		int rows = ovals.length;
		int cols = ovals[0].length;

		for (int r = 0; r <= rows - 4; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (ovals[r][c] != null &&
					ovals[r+1][c+1] != null &&
					ovals[r+2][c+2] != null &&
					ovals[r+3][c+3] != null &&
					ovals[r][c].getFillColor().equals(color) &&
					ovals[r+1][c+1].getFillColor().equals(color) &&
					ovals[r+2][c+2].getFillColor().equals(color) &&
					ovals[r+3][c+3].getFillColor().equals(color)) {
					return true;
				}
			}
		}

		for (int r = 0; r <= rows - 4; r++) {
			for (int c = 3; c < cols; c++) {
				if (ovals[r][c] != null &&
					ovals[r+1][c-1] != null &&
					ovals[r+2][c-2] != null &&
					ovals[r+3][c-3] != null &&
					ovals[r][c].getFillColor().equals(color) &&
					ovals[r+1][c-1].getFillColor().equals(color) &&
					ovals[r+2][c-2].getFillColor().equals(color) &&
					ovals[r+3][c-3].getFillColor().equals(color)) {
					return true;
				}
			}
		}
		return false;
	}


	public static void main(String[] args){
		boolean playComputer=false;//set to false to play a 2 person game.
		new ConnectFour(playComputer);
	}
}
