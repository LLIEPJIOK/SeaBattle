import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class MyField extends JComponent {
    private static final int SIZE = 10;
    private static final int CELL_SIZE = 40;

    private final boolean[][] cells;

    public MyField() {
        cells = new boolean[SIZE][SIZE];
        createWindow();
    }

    private void drawField(Graphics2D g2d) {
        g2d.setColor(new Color(128, 0, 128));
        g2d.setStroke(new BasicStroke(0.3f));
        for (int i = 1; i <= SIZE + 2; i++) {
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, (SIZE + 1) * CELL_SIZE);
            g2d.drawLine(0, i * CELL_SIZE, (SIZE + 1) * CELL_SIZE, i * CELL_SIZE);
        }
        g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(5.0f));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (cells[i][j]) {
                    int x = (int)((j + 1.5) * CELL_SIZE);
                    int y = (int)((i + 1.5) * CELL_SIZE);
                    g2d.fillOval(x - 2, y - 2, 4, 4);
                }
            }
        }
    }

    private void drawLetters(Graphics2D g2d) {
        String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        for (int i = 1; i <= 10; ++i) {
            if (i <= 8) {
                g2d.drawString(letters[i - 1], i * CELL_SIZE + 10, CELL_SIZE - 10);
            } else {
                g2d.drawString(letters[i - 1], i * CELL_SIZE + 15, CELL_SIZE - 10);
            }
        }
    }

    private void drawDigits(Graphics2D g2d) {
        String[] digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        for (int i = 1; i <= 10; ++i) {
            if (i == 10) {
                g2d.drawString(digits[i - 1], 3, 10 * CELL_SIZE + 32);
            } else {
                g2d.drawString(digits[i - 1], 12, i * CELL_SIZE + 32);
            }
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawField(g2d);

        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        g2d.setColor(Color.BLUE);
        drawLetters(g2d);
        drawDigits(g2d);
    }

    private void createWindow() {
        setSize(new Dimension((SIZE + 1) * CELL_SIZE + 1, (SIZE + 1) * CELL_SIZE + 1));
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
