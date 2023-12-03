package core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonHoverMouseAdapter extends MouseAdapter {
    @Override
    public void mouseEntered(MouseEvent e) {
        SoundPlayer.playButtonHoverSound();
    }
}
