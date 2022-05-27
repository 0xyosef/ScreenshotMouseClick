package com.joo.screenshotmouseclick.gui;

import com.joo.screenshotmouseclick.MainController;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final MainController mainController;
    private JButton startAndStopButton,
            pauseAndResumeButton,
            settingsButton;

    public MainFrame() {
        setupTheFrame();
        setupTheButtons();
        addTheComponentsToTheFrame();
        setUpTheFont();
        this.mainController = new MainController();
        addTheListeners();
        super.pack();
        super.setVisible(true);
    }

    private void addTheListeners() {
        startAndStopButton.addActionListener(e -> {
            if (mainController.isRunning()) {
                mainController.stop();
                mainController.saveScreenshots();
                startAndStopButton.setText("Start");
                pauseAndResumeButton.setEnabled(false);
                pauseAndResumeButton.setText("Pause");
            } else {
                mainController.start();
                super.setState(Frame.ICONIFIED); // Minimize the window if that is possible (wm's not
                startAndStopButton.setText("Stop");
                pauseAndResumeButton.setEnabled(true);
                pauseAndResumeButton.setText("Pause");
            }
        });

        pauseAndResumeButton.addActionListener(e -> {
            if (mainController.isRunning()) {
                mainController.start();
                pauseAndResumeButton.setText("Pause");
            } else {
                mainController.stop();
                pauseAndResumeButton.setText("Resume");
            }
        });
    }

    private void setUpTheFont() {
        Font font = new Font("SansSerif", Font.PLAIN, 28);
        startAndStopButton.setFont(font);
        pauseAndResumeButton.setFont(font);
        settingsButton.setFont(font);
    }

    private void addTheComponentsToTheFrame() {
        add(startAndStopButton, "grow, push");
        add(pauseAndResumeButton, "grow, push, wrap");
        add(settingsButton, "span, grow, push");

        // Disable the pause button if the program is not running
        pauseAndResumeButton.setEnabled(false);
    }

    private void setupTheButtons() {
        startAndStopButton = new JButton("Start");
        pauseAndResumeButton = new JButton("Pause");
        settingsButton = new JButton("Settings");
    }

    private void setupTheFrame() {
        setTitle("ScreenshotMouseClick");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
    }
}
