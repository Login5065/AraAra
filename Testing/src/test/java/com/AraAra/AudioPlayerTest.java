package com.AraAra;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AudioPlayerTest {

    @Test
    void play() {

        File S = new File("hello.wav");
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(S));
            clip.isOpen();
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}