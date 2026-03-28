package com.SpeaceGame;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class GamePanel extends JPanel implements ActionListener, KeyListener {

    int shipX = 180;
    int shipY = 350;

    java.util.List<Rectangle> asteroids = new ArrayList<>();
    Random random = new Random();

    Timer timer;

    boolean gameOver = false;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 400, 400);

        // Spaceship
        g.setColor(Color.GREEN);
        g.fillRect(shipX, shipY, 40, 40);

        // Asteroids
        g.setColor(Color.RED);
        for (Rectangle r : asteroids) {
            g.fillOval(r.x, r.y, r.width, r.height);
        }

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", 150, 200);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameOver) return;

        // Add new asteroid
        if (random.nextInt(10) == 0) {
            asteroids.add(new Rectangle(random.nextInt(360), 0, 30, 30));
        }

        // Move asteroids
        for (Rectangle r : asteroids) {
            r.y += 5;
        }

        // Collision check
        Rectangle ship = new Rectangle(shipX, shipY, 40, 40);
        for (Rectangle r : asteroids) {
            if (ship.intersects(r)) {
                gameOver = true;
                timer.stop();
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            shipX -= 20;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            shipX += 20;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}

public class SpaceExplorerGame {
    public static void main(String[] args) {

        JFrame frame = new JFrame("🚀 Space Explorer");
        GamePanel panel = new GamePanel();

        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}