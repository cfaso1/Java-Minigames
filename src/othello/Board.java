package othello;

import doodlepad.Pad;
import doodlepad.Rectangle;

public class Board extends Pad {
    private int rows;
    private int cols;
    private double colWidth;
    private double pieceSize;
    private boolean eventsEnabled = true;

    public Board(int cols, int rows) {
        super(cols * 100, rows * 100); // 100 pixels per cell, adjustable
        this.rows = rows;
        this.cols = cols;
        this.colWidth = 100; // default cell size
        this.pieceSize = colWidth; 
    }

    public void drawGrid() {
        // Draw vertical lines
        for (int c = 0; c <= cols; c++) {
            double x = c * colWidth;
            new Rectangle(x, 0, 1, rows * colWidth).setFillColor(java.awt.Color.BLACK);
        }
        // Draw horizontal lines
        for (int r = 0; r <= rows; r++) {
            double y = r * colWidth;
            new Rectangle(0, y, cols * colWidth, 1).setFillColor(java.awt.Color.BLACK);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getColWidth() {
        return colWidth;
    }

    public double getPieceSize() {
        return pieceSize;
    }

    public void setEventsEnabled(boolean enabled) {
        this.eventsEnabled = enabled;
    }

    // Allow subclasses to override these
    public void onMouseClicked(double x, double y, int button) {
        // To be overridden by child class
    }

    public void onKeyPressed(String keyText, String keyModifiers) {
        // To be overridden by child class
    }

    // Internally handled by doodlepad, these would route to the user-implemented methods
    public void mouseClicked(double x, double y, int button) {
        if (eventsEnabled)
            onMouseClicked(x, y, button);
    }

    public void keyPressed(String keyText, String keyModifiers) {
        if (eventsEnabled)
            onKeyPressed(keyText, keyModifiers);
    }
    
}
