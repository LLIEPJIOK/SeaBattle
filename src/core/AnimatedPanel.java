package core;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AnimatedPanel extends JPanel {

    private final Image backgroundImage;

    public AnimatedPanel() {
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("MenuAnimation.gif")))
                .getImage();
        setLayout(new GridBagLayout());
    }

    public void add(Component comp, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(10, 20, 30, 0);
        super.add(comp, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}