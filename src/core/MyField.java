package core;

import dto.Coordinate;
import dto.MyActionEvent;
import dto.Request;
import dto.Response;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class MyField extends Field implements ActionListener {
    private final List<Ship> ships;

    public MyField(MessageWriter messageWriter, boolean isHost, JLabel playersTurnLabel) {
        super(messageWriter, playersTurnLabel);

        if (isHost) {
            ships = ShipsReader.read("1.json");
        } else {
            ships = ShipsReader.read("2.json");
        }
    }

    private void drawShips(Graphics2D g2d) {
        for (Ship ship : ships) {
            ship.paint(g2d);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawShips(g2d);
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

    public void handleRequest(Request request) {
        Response response = updateShips(request.getCoordinate());
        int x = request.getCoordinate().getX();
        int y = request.getCoordinate().getY();
        if (response.isHit()) {
            cells[y - 1][x - 1] = -1;
            if (!response.getCoordinates().isEmpty()) {
                handleDestruction(response.getCoordinates());
                --shipsAlive;
                if (shipsAlive == 0) {
                    playersTurnLabel.setText("Your lose!");
                }
            }
        } else {
            cells[y - 1][x - 1] = 1;
            playersTurnLabel.setText("Your turn");
        }
        messageWriter.write(response);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event instanceof MyActionEvent myEvent) {
            if (myEvent.getData() instanceof Request request) {
                handleRequest(request);
            }
        }
    }
}
