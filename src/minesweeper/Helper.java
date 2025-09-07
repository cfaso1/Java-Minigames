package minesweeper;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;

final class Helper {
    private Helper() {}

    public static void setAdjacentCounts(int r, int c, MineSweeper board) {
        Cell[][] cells = board.getCells();
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = r + dr, nc = c + dc;
                if (board.inBounds(nr, nc) && !cells[nr][nc].isMine()) {
                    cells[nr][nc].incrementCount();
                }
            }
        }
    }

    public static void sweep(int r, int c, MineSweeper board) {
        Cell[][] cells = board.getCells();
        boolean[][] seen = new boolean[cells.length][cells[0].length];
        Deque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{r, c});
        seen[r][c] = true;

        while (!q.isEmpty()) {
            int[] cur = q.removeFirst();
            int cr = cur[0], cc = cur[1];

            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) continue;

                    int nr = cr + dr, nc = cc + dc;
                    if (!board.inBounds(nr, nc) || seen[nr][nc]) continue;

                    Cell neighbor = cells[nr][nc];
                    seen[nr][nc] = true;

                    if (!neighbor.isMine() && neighbor.getFillColor().equals(Color.BLUE)) {
                        neighbor.padClick();

                        String text = neighbor.getText(); 
                        boolean isZero = (text == null || text.isEmpty());
                        if (isZero) {
                            q.addLast(new int[]{nr, nc});
                        }
                    }
                }
            }
        }
    }
}
