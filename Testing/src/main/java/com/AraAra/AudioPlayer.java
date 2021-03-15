package com.AraAra;

import javax.sound.sampled.*;
import java.io.*;


public class AudioPlayer {

    public void play(){

        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(AudioPlayer.class.getResource("hello.wav"));
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioIn);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip.start();

    }


}
