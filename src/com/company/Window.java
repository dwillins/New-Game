package com.company;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas{
    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        Dimension size = new Dimension(width, height);

        frame.setPreferredSize(size);
        frame.setMaximumSize(size);
        frame.setMinimumSize(size);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        frame.addKeyListener(new KeyInput());
        game.start();
    }
}
