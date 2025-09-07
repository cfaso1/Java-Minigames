package piano;

import java.awt.Color;
import doodlepad.Rectangle;

public class Key extends Rectangle{
	
	public Key(double x, double y, boolean isWhite,int width, int height) {
		super(x, y, width, height);
		if (isWhite)
			setFillColor(Color.WHITE);
		else
			setFillColor(Color.BLACK);
	}
}
