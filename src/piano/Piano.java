package piano;

import java.awt.Color;
import doodlepad.Pad;
import doodlepad.Text;

public class Piano extends Pad{
	private Key[][] keys;
	private int score;
	private final int MAX=4;
	private static final int
	PAD_WIDTH=400,
	PAD_HEIGHT=650;

	public Piano() {
		super(PAD_WIDTH, PAD_HEIGHT);
		keys = new Key[MAX][MAX];
		createBoard();
	}



	public void createBoard() {
		setEventsEnabled(true);
		int keyWidth=PAD_WIDTH/MAX;
		int keyHeight=PAD_HEIGHT/MAX;
		for (int i = 0; i < MAX; i++) {
			int r = (int)(Math.random()* MAX);
			for (int k = 0; k < MAX; k++)
				if (r!=k)
					keys[i][k] = new Key(k*keyWidth, i*keyHeight, true, keyWidth, keyHeight);
				else
					keys[i][k] = new Key(k*keyWidth, i*keyHeight, false, keyWidth, keyHeight);
		}
	}

	

	public void onKeyPressed(String keyText, String e) {
		String VALID_CHOICES="DFJK";
		int c=VALID_CHOICES.indexOf(keyText);
		if(c==-1)
			System.out.println("Not a valid key!!!!!!");
		else if (gameOver(c)) {
			System.out.println("Game Over! You Scored: " + score);
			Text t = new Text("You Lost", 0, 100, 100);
			t.setFillColor(Color.RED);
			setEventsEnabled(false);
		}
		else {
			moveKeys(c);
			score++;
		}
}
	public void moveKeys(int c) {
		keys[MAX-1][c].setFillColor(Color.WHITE);
		for(int i=MAX-1; i>0; i--) {
			int black=findBlack(keys[i-1]);
			keys[i][black].setFillColor(Color.BLACK);
			keys[i-1][black].setFillColor(Color.WHITE);
		}
		keys[0][(int)(Math.random()*keys[0].length)].setFillColor(Color.BLACK);
}

	public int findBlack(Key[] a) {
		for (int i = 0; i < a.length; i++)
			if (a[i].getFillColor().equals(Color.BLACK))
				return i;
		return -1;
	}
	public boolean gameOver(int c) {
		return (findBlack(keys[MAX-1])!=c);
	}

	public static void main(String[] args) {
		new Piano();
	}
}