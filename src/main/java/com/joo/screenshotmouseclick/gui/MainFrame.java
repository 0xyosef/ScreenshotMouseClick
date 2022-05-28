package com.joo.screenshotmouseclick.gui;

import com.joo.screenshotmouseclick.MainController;
import com.joo.screenshotmouseclick.SAVE_MODE;
import com.joo.screenshotmouseclick.debugg.Debugger;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {
    private final MainController mainController;
    private JButton startAndStopButton,
            pauseAndResumeButton,
            settingsButton,
            openViewerButton,
            saveButton;

    public MainFrame() {
        Debugger.setEnable(true); // Active debug mode
        setupTheFrame();
        setupTheButtons();
        addTheComponentsToTheFrame();
        setUpTheFont();
        this.mainController = new MainController();
        addTheListeners();
        super.pack();
        super.setLocationRelativeTo(null); // Center the frame on the screen
        super.setVisible(true);
    }

    private void addTheListeners() {
        addListenerToStartAndStopButton();
        addListenerToPauseAndResumeButton();
        addListenerToSettingsButton();
        addListenerToOpenViewerButton();
        addListenerToSaveButton();
    }

    // TODO: 5/28/22 Clean up this method
    private void addListenerToSaveButton() {
        saveButton.addActionListener(e -> {
            SAVE_MODE saveMode = SAVE_MODE.values()[JOptionPane.
                    showOptionDialog(this, "Choose the save method", "Save",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new String[]{"Save as pdf", "Save as photos"}, null)];
            // Setup the file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Screenshot");
            //
            fileChooser.setFileSelectionMode(saveMode == SAVE_MODE.PNG ?
                    JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    mainController.saveScreenshots(fileChooser.getSelectedFile(), saveMode);
                    Debugger.println("Saved in: " + fileChooser.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Successfully saved in: " +
                            fileChooser.getSelectedFile().getAbsolutePath());
                } catch (IOException ex) {
                    Debugger.println(ex.toString(), true);
                    JOptionPane.showMessageDialog(this, "Error while saving: " + ex.getMessage());
                }
            }
        });
    }

    private void addListenerToOpenViewerButton() {
        // TODO: 5/28/22 Implement this method
    }

    private void addListenerToSettingsButton() {
        // TODO: 5/28/22 Implement this method
    }

    private void addListenerToPauseAndResumeButton() {
        pauseAndResumeButton.addActionListener(e -> {
            if (!mainController.isRunning()) {
                mainController.startRec();
                pauseAndResumeButton.setText("Pause");
            } else {
                mainController.stopRec();
                pauseAndResumeButton.setText("Resume");
            }
        });
    }

    private void addListenerToStartAndStopButton() {
        startAndStopButton.addActionListener(e -> {
            if (mainController.isRunning()) {
                mainController.stopRec();
                startAndStopButton.setText("Start");
                pauseAndResumeButton.setEnabled(false);
                pauseAndResumeButton.setText("Pause");
                saveButton.setEnabled(true);
            } else {
                mainController.startRec();
                // super.setState(Frame.ICONIFIED); // Minimize the window if that is possible (wm's not
                startAndStopButton.setText("Stop");
                pauseAndResumeButton.setEnabled(true);
                pauseAndResumeButton.setText("Pause");
                saveButton.setEnabled(false);
            }
        });
    }

    private void setUpTheFont() {
        Font font = new Font("SansSerif", Font.PLAIN, 28);
        startAndStopButton.setFont(font);
        pauseAndResumeButton.setFont(font);
        settingsButton.setFont(font);
        openViewerButton.setFont(font);
        saveButton.setFont(font);
    }

    private void addTheComponentsToTheFrame() {
        add(startAndStopButton, "grow, push");
        add(pauseAndResumeButton, "grow, push");
        add(openViewerButton, "grow, push, wrap");
        add(saveButton, "grow, push,  span 2");
        add(settingsButton, "grow, push");

        // Disable the pause button if the program is not running
        pauseAndResumeButton.setEnabled(false);
        // Disable the save button
        saveButton.setEnabled(false);
    }

    private void setupTheButtons() {
        startAndStopButton = new JButton("Start");
        pauseAndResumeButton = new JButton("Pause");
        settingsButton = new JButton("Settings");
        openViewerButton = new JButton("Open Viewer/Editor");
        saveButton = new JButton("Save");
    }

    private void setupTheFrame() {
        setTitle("ScreenshotMouseClick");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
    }
}
