package com.joo.screenshotmouseclick.listners;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.joo.screenshotmouseclick.MainController;

public class ShortCutsListener extends AbstractListener implements NativeKeyListener {
    public ShortCutsListener(MainController mainController) {
        super(mainController);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F12 &&
                mainController.isRunning()) {
            mainController.stopRec();
            System.out.println("Recording stopped by F12");
        }
    }
}
