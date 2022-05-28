package com.joo.screenshotmouseclick.listners;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.joo.screenshotmouseclick.MainController;


public class MouseListener extends AbstractListener implements NativeMouseInputListener {

    public MouseListener(MainController mainController) {
        super(mainController);
    }
    public void nativeMousePressed(NativeMouseEvent e) {
        System.out.println("Mouse Pressed: " + e.getButton());
        if ((e.getButton() == 1 || e.getButton() == 2)
                && mainController.isRunning()) {
            mainController.takeScreenshot(e.getPoint());
            System.out.println("Screenshot taken");
        }
    }
}