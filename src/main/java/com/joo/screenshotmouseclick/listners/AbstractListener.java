package com.joo.screenshotmouseclick.listners;

import com.joo.screenshotmouseclick.MainController;

public abstract class AbstractListener {
    protected MainController mainController;

    public AbstractListener(MainController mainController) {
        this.mainController = mainController;
    }
}
