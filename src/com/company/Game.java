package com.company;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 900, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;

    private final Handler handler;

    public static int player1Points = 0;
    public static int player2Points = 0;

    //game objects here


    public Game() {
        handler = new Handler();

        new Window(WIDTH, HEIGHT, "GameWindow", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void tick() {
        handler.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // sets window properties
        g.setColor(Color.black);
        g.fillRect(0,0, WIDTH, HEIGHT);

        handler.render(g);

        // prints the point totals
        g.drawString(String.valueOf(player1Points), 250, 10);
        g.drawString(String.valueOf(player2Points), 600, 10);

        g.dispose();
        bs.show();
    }

    public static void player1Score() {
        player1Points++;
    }

    public static void player2Score() {
        player2Points++;
    }

    public void run() {
        //primary game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000.0 / amountOfTicks;
        double delta = 0.0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now-lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
}
