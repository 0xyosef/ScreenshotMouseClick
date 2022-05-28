package com.joo.screenshotmouseclick.listners;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.joo.screenshotmouseclick.MainController;
import com.joo.screenshotmouseclick.debugg.Debugger;

public class ShortCutsListener extends AbstractListener implements NativeKeyListener {
    public ShortCutsListener(MainController mainController) {
        super(mainController);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F12 &&
                mainController.isRunning()) {
            mainController.stopRec();
            Debugger.println("Recording stopped by F12");
        }
    }
}
