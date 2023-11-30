import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class MyField extends JComponent {
    private static final int SIZE = 10;
    private static final int CELL_SIZE = 40;

    private boolean[][] cells;

    public MyField() {
        cells = new boolean[SIZE][SIZE];
        createWindow();
    }

    private void drawLetters(Graphics g) {

    }

    private void drawDigits(Graphics g) {
        char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
        g.setColor(Color.BLUE);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        for (int j = 1; j <= 10; ++j) {
            g.drawString(String.valueOf(chars[j - 1]), j * CELL_SIZE + 10, CELL_SIZE - 10);
        }
    }

    private void drawField(Graphics g) {
        for (int i = 1; i <= SIZE + 1; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SIZE * CELL_SIZE);
            g.drawLine(0, i * CELL_SIZE + 0, (SIZE + 1) * CELL_SIZE, i * CELL_SIZE);
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (cells[i][j]) {
                    g.setColor(Color.BLUE);
                    g.fillRect((j + 1) * CELL_SIZE, (i + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        drawField(g);
        drawLetters(g);
        drawDigits(g);
    }

    private void createWindow() {
        setSize(new Dimension((SIZE + 1) * CELL_SIZE + 1, (SIZE + 1) * CELL_SIZE));
        addMouseListener(new CellClickListener());
    }

    private class CellClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = e.getY() / CELL_SIZE - 1;
            int col = e.getX() / CELL_SIZE - 1;
            cells[row][col] = !cells[row][col];
            repaint();
        }
    }

}
