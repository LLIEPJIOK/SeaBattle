package core;

import java.awt.Dimension;
import java.awt.Panel;

import javax.swing.JFrame;

class MainFrame extends JFrame {

    MainFrame() {
        MyField myField1 = new MyField(false);
        myField1.setLocation(0, 0);
        MyField myField2 = new MyField(true);
        myField2.setLocation(500, 0);
        add(myField1);
        add(myField2);
        add(new Panel());
        this.setSize(new Dimension(1000, 500));
        Menu menu = new Menu();
    }
}