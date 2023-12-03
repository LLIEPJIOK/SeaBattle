package core;

import dto.Coordinate;
import dto.MyActionEvent;
import dto.Request;
import dto.Response;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComponent;

import client.ClientWindow;

public class MyField extends JComponent implements ActionListener {
    private final int SIZE = 10;
    private final int CELL_SIZE = 40;
    private final int[][] cells;
    private final List<Ship> ships;
    private final boolean isEnemy;
    private final MessageWriter messageWriter;

    public MyField(MessageWriter messageWriter, boolean isEnemy, boolean isHost) {
        this.isEnemy = isEnemy;
        this.messageWriter = messageWriter;

        cells = new int[SIZE][SIZE];
        createWindow();

        if (isHost) {
            ships = ShipsReader.read("1.json");
        } else {
            ships = ShipsReader.read("2.json");
        }
    }

    private void drawField(Graphics2D g2d) {
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

    private void drawShips(Graphics2D g2d) {
        for (Ship ship : ships) {
            ship.paint(g2d, isEnemy);
        }
    }

    private void drawLetters(Graphics2D g2d) {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
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
        drawShips(g2d);

        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        g2d.setColor(Color.BLUE);
        drawLetters(g2d);
        drawDigits(g2d);
    }

    private void createWindow() {
        setSize(new Dimension((SIZE + 1) * CELL_SIZE + 3, (SIZE + 1) * CELL_SIZE + 3));
        if (isEnemy) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX() / CELL_SIZE;
                    int y = e.getY() / CELL_SIZE;
                    if (ClientWindow.canAttack && Math.min(x, y) > 0 && Math.max(x, y) <= 10 && cells[y - 1][x - 1] == 0) {
                        ClientWindow.canAttack = false;
                        messageWriter.write(new Request(new Coordinate(x, y)));
                        repaint();
                    }
                }
            });
        }
    }

    private Response updateShips(Coordinate coordinate) {
        Response response;
        for (Ship ship : ships) {
            if ((response = ship.update(coordinate)) != null) {
                return response;
            }
        }
        return new Response(coordinate);
    }

    private void surroundWithDots(Coordinate coordinate) {
        for (int row = coordinate.getY() - 2; row <= coordinate.getY(); ++row) {
            for (int col = coordinate.getX() - 2; col <= coordinate.getX(); ++col) {
                if (Math.min(row, col) >= 0 && Math.max(row, col) < 10 && cells[row][col] == 0) {
                    cells[row][col] = 1;
                }
            }
        }
    }

    private void handleDestruction(List<Coordinate> shipCoordinates) {
        for (Coordinate coordinate : shipCoordinates) {
            surroundWithDots(coordinate);
        }
    }

    public void handleResponse(Response response) {
        int x = response.getCoordinate().getX();
        int y = response.getCoordinate().getY();
        if (!response.getCoordinates().isEmpty()) {
            handleDestruction(response.getCoordinates());
        }
        if (response.isHit()) {
            cells[y - 1][x - 1] = -1;
            ClientWindow.canAttack = true;
        } else {
            cells[y - 1][x - 1] = 1;
        }
        repaint();
    }

    public void handleRequest(Request request) {
        Response response = updateShips(request.getCoordinate());
        int x = request.getCoordinate().getX();
        int y = request.getCoordinate().getY();
        if (response.isHit()) {
            cells[y - 1][x - 1] = -1;
        } else {
            cells[y - 1][x - 1] = 1;
            ClientWindow.canAttack = true;
        }
        messageWriter.write(response);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event instanceof MyActionEvent myEvent) {
            if (myEvent.getData() instanceof Request request) {
                handleRequest(request);
            } else if (myEvent.getData() instanceof Response response) {
                handleResponse(response);
            }
        }
    }
}
