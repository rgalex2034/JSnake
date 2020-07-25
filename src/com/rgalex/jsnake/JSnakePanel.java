package com.rgalex.jsnake;

import com.rgalex.jsnake.util.Point;
import com.rgalex.jsnake.util.Observer;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.Graphics;
import java.awt.Color;
import java.util.LinkedList;

public class JSnakePanel extends JPanel implements Observer, KeyEventDispatcher{

    public JSnake jsnake;

    public JSnakePanel(JSnake jsnake){
        this.jsnake = jsnake;
        this.jsnake.addListener(this);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
        this.setBackground(Color.BLACK);
    }

    public void event(Object source, int id){
        if(id == JSnake.EV_STEP) this.repaint();
    }

    public boolean dispatchKeyEvent(KeyEvent ev){
        switch(ev.getKeyCode()){
            case KeyEvent.VK_UP:
                this.jsnake.turn(JSnake.Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                this.jsnake.turn(JSnake.Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                this.jsnake.turn(JSnake.Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                this.jsnake.turn(JSnake.Direction.RIGHT);
                break;
        }
        return true;
    }

    public void paint(Graphics g){
        int blockSize = 10;

        //Paint top status bar
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, jsnake.width*blockSize, 30);
        g.setColor(Color.BLACK);
        g.drawString(String.format("Score: %d", this.jsnake.getScore()), 0, 25);

        g.translate(0, 30);

        //Paint background
        g.setColor(Color.gray);
        g.fillRect(0, 0, jsnake.width*blockSize, jsnake.height*blockSize);

        //Extract point information from the snake game
        Point fruit = this.jsnake.getFruitPoint();
        LinkedList<Point> snake = this.jsnake.getSnakePoints();

        //Paint the fruit
        g.setColor(Color.RED);
        g.fillRect(fruit.x*blockSize, fruit.y*blockSize, blockSize, blockSize);
        g.setColor(Color.BLACK);
        g.drawRect(fruit.x*blockSize, fruit.y*blockSize, blockSize, blockSize);

        //Paint the snake
        for(Point p : snake){
            g.setColor(Color.WHITE);
            g.fillRect(p.x*blockSize, p.y*blockSize, blockSize, blockSize);
            g.setColor(Color.BLACK);
            g.drawRect(p.x*blockSize, p.y*blockSize, blockSize, blockSize);
        }

        //Paint the board border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, jsnake.width*blockSize, jsnake.height*blockSize);
    }

}
