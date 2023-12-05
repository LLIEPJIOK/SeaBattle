package core;

import dto.Coordinate;

import java.awt.*;
import java.util.List;

import javax.swing.*;

public class Field extends JComponent {
    protected final int SIZE = 10;
    public static final int CELL_SIZE = 25;
    protected final int[][] cells;
    protected final MessageWriter messageWriter;
    protected JLabel playersTurnLabel;

    public Field(MessageWriter messageWriter, JLabel playersTurnLabel) {
        this.messageWriter = messageWriter;
        this.playersTurnLabel = playersTurnLabel;
        this.cells = new int[SIZE][SIZE];
        this.setSize(new Dimension((SIZE + 1) * CELL_SIZE + 3, (SIZE + 1) * CELL_SIZE + 3));
    }


    protected void drawField(Graphics2D g2d) {
        g2d.setColor(new Color(128, 0, 128));
        g2d.setStroke(new BasicStroke(0.3f));
        for (int i = 1; i <= SIZE + 2; i++) {
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, (SIZE + 1) * CELL_SIZE);
            g2d.drawLine(0, i * CELL_SIZE, (SIZE + 1) * CELL_SIZE, i * CELL_SIZE);
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (cells[i][j] == 1) {
                    g2d.setStroke(new BasicStroke(5.0f));
                    g2d.setColor(Color.red);
                    int x = (int) ((j + 1.5) * CELL_SIZE);
                    int y = (int) ((i + 1.5) * CELL_SIZE);
                    g2d.fillOval(x - 4, y - 4, 8, 8);
                }
                if (cells[i][j] == -1) {
                    g2d.setStroke(new BasicStroke(2.0f));
                    int x = (j + 1) * CELL_SIZE;
                    int y = (i + 1) * CELL_SIZE;
                    g2d.setColor(Color.RED);
                    g2d.drawLine(x, y, x + CELL_SIZE, y + CELL_SIZE);
                    g2d.drawLine(x + CELL_SIZE, y, x, y + CELL_SIZE);
                    g2d.setColor(Color.BLUE);
                    g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    protected void drawLetters(Graphics2D g2d) {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 1; i <= 10; ++i) {
            if (i <= 8) {
                g2d.drawString(letters[i - 1], i * CELL_SIZE + 6, CELL_SIZE - 6);
            } else if (i == 9){
                g2d.drawString(letters[i - 1], i * CELL_SIZE + 10, CELL_SIZE - 6);
            } else {
                g2d.drawString(letters[i - 1], i * CELL_SIZE + 7, CELL_SIZE - 6);
            }
        }
    }

    protected void drawDigits(Graphics2D g2d) {
        String[] digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        for (int i = 1; i <= 10; ++i) {
            if (i == 10) {
                g2d.drawString(digits[i - 1], 0, (i + 1) * CELL_SIZE - 4);
            } else {
                g2d.drawString(digits[i - 1], 7, (i + 1) * CELL_SIZE - 4);
            }
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawField(g2d);

        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.BLUE);
        drawLetters(g2d);
        drawDigits(g2d);
    }

    protected void surroundWithDots(Coordinate coordinate) {
        for (int row = coordinate.getY() - 2; row <= coordinate.getY(); ++row) {
            for (int col = coordinate.getX() - 2; col <= coordinate.getX(); ++col) {
                if (Math.min(row, col) >= 0 && Math.max(row, col) < 10 && cells[row][col] == 0) {
                    cells[row][col] = 1;
                }
            }
        }
    }

    protected void handleDestruction(List<Coordinate> shipCoordinates) {
        for (Coordinate coordinate : shipCoordinates) {
            surroundWithDots(coordinate);
        }
    }
}
