package core;

import java.awt.Dimension;
import java.awt.Panel;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

    public MainFrame() {
        this.setSize(new Dimension(1000, 500));
        Menu menu = new Menu();
    }
}