package com.AraAra;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Listen_chan  implements ProjectManagerListener {
    /**
     * Invoked on project open.
     *
     * @param project opening project
     */
    @Override
    public void projectOpened(@NotNull Project project) {
        // Ensure this isn't part of testing
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return;
        }

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


    /**
     * Invoked on project close.
     *
     * @param project closing project
     */
    @Override
    public void projectClosed(@NotNull Project project) {
        // Ensure this isn't part of testing
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return;
        }
    }

}

