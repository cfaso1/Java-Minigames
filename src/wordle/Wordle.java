package wordle;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import doodlepad.Rectangle;
import doodlepad.Text;

public class Wordle extends Board{

	private String word;
	private String[] letters;
	private ArrayList<String> list;
	private int currentRow;
	private int currentCol;

	public Wordle (){
		super(5, 6);
		setBackground(Color.LIGHT_GRAY);
		drawGrid();
		letters = new String[5];
		word=pickWord();
		System.out.println("Secret word: " + word);
	}

	public String pickWord(){
		list=new ArrayList<String>();
		File inputFile = new File("lib/words.txt");
		Scanner input = null;
		try {
			input = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(input.hasNextLine()){
			list.add(input.nextLine().toUpperCase());
		}
		return list.get((int)(Math.random()*list.size()));
	}
	
	public boolean isRowFull(){
		return letters[4]!=null;
	}
	
	public void colorCodeBox(int col, Color c) {
		Rectangle r = new Rectangle(col*getColWidth(), currentRow*getColWidth(), getPieceSize(), getPieceSize());
		r.setFillColor(c);
		r.setText(letters[col]);
		r.setFontSize(100);
	}

	public boolean isValid(String w) {
		return list.contains(w.toUpperCase());
	}

	public boolean keyChecker (String str) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(str)!=-1 || str.equals("Enter") || str.equals("Backspace");
	}

		public void gameOver(String message) {
			setEventsEnabled(false);
			Text t = new Text(message , 100, 100);
			t.setFontSize(100);
			t.setFillColor(Color.RED);
		}
	
	public void backspace() {
		for (int i = letters.length-1; i >= 0; i--)
			if (letters[i]!=null) {
				letters[i]=null;
				colorCodeBox(i, Color.WHITE);
				currentCol--;
				return;
			}
	}
	
	public ArrayList<String> exactMatch(){
		ArrayList<String> out = new ArrayList<String>();
		for (int i = 0; i < word.length(); i++)
			if(letters[i].equals(word.substring(i, i+1))) {
				colorCodeBox(i, Color.GREEN);
				letters[i]=null;
			}
			else
				out.add(word.substring(i, i+1));
		return out;
	}
	
	public void letterChecker() {
		ArrayList<String> notMatched=exactMatch();
		if (notMatched.size()==0) {
			gameOver("GREAT");
			return;
		}
		for (int c = 0; c < 5; c++) {
			if (notMatched.contains(letters[c])) {
				colorCodeBox(c,Color.YELLOW);
				notMatched.remove(letters[c]);
			}
			else if(letters[c]!=null)
				colorCodeBox(c,Color.GRAY);	
			}
		if (currentRow==5)
			for (int i = 0; i < letters.length; i++) {
				letters[i]=word.substring(i, i+1);
				colorCodeBox(i, Color.RED);
			}
	}

	public boolean isWord() {
		String s = "";
		for (String e: letters)
			s+=e;
		if (list.contains(s))
			return true;
		for (int i = 0; i < letters.length; i++)
			backspace();
		return false;
	}

	public void onKeyPressed(java.lang.String keyText, java.lang.String keyModifiers) {
		if(!keyChecker(keyText)) return;
		if (keyText.equals("Enter")) {
			if(isRowFull() && isWord()) { 
				letterChecker();
				currentRow++;
				currentCol=0;
				for (int i = 0; i < word.length(); i++)
					letters[i]=null;
			}
		}
		else if(keyText.equals("Backspace"))
			backspace();
		else if (!isRowFull()){
			letters[currentCol] = keyText;
			colorCodeBox(currentCol,Color.GRAY);
			currentCol++;
		}
	}

	public static void main(String[] args) {
		new Wordle();
	}
}


