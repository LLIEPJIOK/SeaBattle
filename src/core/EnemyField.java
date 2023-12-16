package core;

import dto.Coordinate;
import dto.MyActionEvent;
import dto.Request;
import dto.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EnemyField extends Field implements ActionListener {

    public EnemyField(MessageWriter messageWriter, JLabel playersTurnLabel) {
        super(messageWriter, playersTurnLabel);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;
                if (playersTurnLabel.getText().equals("Your turn") && Math.min(x, y) > 0 && Math.max(x, y) <= 10 && cells[y - 1][x - 1] == 0) {
                    playersTurnLabel.setText("Waiting response...");
                    messageWriter.write(new Request(new Coordinate(x, y)));
                    repaint();
                }
            }
        });
    }

    public void handleResponse(Response response) {
        int x = response.getCoordinate().getX();
        int y = response.getCoordinate().getY();
        if (response.isHit()) {
            cells[y - 1][x - 1] = -1;
            playersTurnLabel.setText("Your turn");
        } else {
            cells[y - 1][x - 1] = 1;
            playersTurnLabel.setText("Enemy turn");
        }
        if (!response.getCoordinates().isEmpty()) {
            handleDestruction(response.getCoordinates());
            --shipsAlive;
            if (shipsAlive == 0) {
                playersTurnLabel.setText("Your win!");
            }
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event instanceof MyActionEvent myEvent) {
            if (myEvent.getMessage() instanceof Response response) {
                handleResponse(response);
            }
        }
    }
}
