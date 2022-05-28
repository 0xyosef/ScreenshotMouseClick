package com.joo.screenshotmouseclick.listners;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.joo.screenshotmouseclick.MainController;
import com.joo.screenshotmouseclick.debugg.Debugger;


public class MouseListener extends AbstractListener implements NativeMouseInputListener {

    public MouseListener(MainController mainController) {
        super(mainController);
    }
    public void nativeMousePressed(NativeMouseEvent e) {
        Debugger.println("Mouse Pressed: " + e.getButton());
        if ((e.getButton() == 1 || e.getButton() == 2)
                && mainController.isRunning()) {
            mainController.takeScreenshot(e.getPoint());
            Debugger.println("Screenshot taken");
        }
    }
}