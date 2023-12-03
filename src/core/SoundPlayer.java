package core;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SoundPlayer {
    //All music stuff
    private static Clip backgroundMusic;
    private static Clip buttonHoverSound;
    private static Clip typeSound;

    private static AudioInputStream createAudioInputStream(String fileName) throws UnsupportedAudioFileException, IOException {
        InputStream audioSrc = SoundPlayer.class.getResourceAsStream(fileName);
        return AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(audioSrc)));
    }

    public static void loadBackgroundMusic(String fileName) {
        try {
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(createAudioInputStream(fileName));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void loadButtonHoverSound(String fileName) {
        try {
            buttonHoverSound = AudioSystem.getClip();
            buttonHoverSound.open(createAudioInputStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadTypeSound(String fileName) {
        try {
            typeSound = AudioSystem.getClip();
            typeSound.open(createAudioInputStream(fileName));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void playButtonHoverSound() {
        if (buttonHoverSound != null) {
            buttonHoverSound.setFramePosition(0);
            buttonHoverSound.start();
        }
    }

    public static void playTypeSound() {
        if (typeSound != null) {
            typeSound.setFramePosition(80);
            typeSound.start();
        }
    }
}
