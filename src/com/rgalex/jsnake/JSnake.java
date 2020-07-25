package com.rgalex.jsnake;

import com.rgalex.jsnake.util.Point;
import com.rgalex.jsnake.util.Observer;
import com.rgalex.jsnake.GameLoop;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class JSnake extends GameLoop{

    public enum Direction{
        UP (0, -1),
        DOWN (0, 1),
        LEFT (-1, 0),
        RIGHT (1, 0);

        public final Point vector;

        Direction(int x, int y){
            this.vector = new Point(x, y);
        }
    }

    //Game properties
    int score;

    //Fruit properties
    private Point fruit;
    private static final int FRUIT_POINS = 10;

    //Map boundaries
    public final int width;
    public final int height;

    //Snake properties
    private LinkedList<Point> snake;
    private JSnake.Direction direction;
    private long speed;
    private long speedAcc;

    private Random rnd;

    //Game events
    public static final int EV_TICK = 0;
    public static final int EV_STEP = 1;
    private LinkedList<Observer> listeners;

    public JSnake(int width, int height){
        this(width, height, 500);
    }

    public JSnake(int width, int height, long initialSpeed){
        this.score = 0;
        this.width = width;
        this.height = height;
        this.speed = initialSpeed;
        this.speedAcc = 0;
        this.rnd = new Random();
        this.listeners = new LinkedList<Observer>();
        this.resetSnake();
    }

    public boolean turn(JSnake.Direction direction){
        Point head = this.snake.getFirst();
        Point neck = this.snake.get(1);

        if(head.translate(direction.vector).equals(neck)) return false;

        this.direction = direction;
        return true;
    }

    public Point getFruitPoint(){
        return this.fruit;
    }

    public LinkedList<Point> getSnakePoints(){
        return (LinkedList<Point>)this.snake.clone();
    }

    public int getScore(){
        return this.score;
    }

    public void addListener(Observer listener){
        this.listeners.push(listener);
    }

    private void sendEvent(int id){
        for(Observer o : this.listeners){
            o.event(this, id);
        }
    }

    public void move(){
        LinkedList<Point> newSnake = new LinkedList<Point>();
        Point last = null;
        for(Point p : this.snake){
            newSnake.addLast(p.translate(last == null ? this.direction.vector : last.sub(p)));
            last = p;
        }

        //Check if snake ate the fruit
        if(newSnake.get(0).equals(this.fruit)){
            this.score += JSnake.FRUIT_POINS;
            this.randomizeFruit();
            newSnake.addLast(this.snake.getLast());
        }

        this.snake = newSnake;

        this.sendEvent(JSnake.EV_STEP);
    }

    @Override
    public void loop(long delta){
        this.sendEvent(JSnake.EV_TICK);

        //Move snake once speed is reached
        this.speedAcc += delta;
        if(this.speedAcc >= this.speed){
            this.move();
            this.speedAcc -= this.speed;
        }
    }

    private void resetSnake(){
        //Set up snake body as a centered head with one body block under it.
        Point head = new Point(this.width/2, this.height/2);
        Point body = head.translate(0, 1);

        this.randomizeFruit();

        this.direction = JSnake.Direction.UP;

        this.snake = new LinkedList<Point>();
        this.snake.add(head);
        this.snake.add(body);

        this.logStatus();
    }

    private void randomizeFruit(){
        this.fruit = new Point(this.rnd.nextInt(this.width), this.rnd.nextInt(this.height));
    }

    private void logStatus(){
        System.out.println("Fruit is located on: " + this.fruit);
        System.out.println("Snake body is facing " + this.direction + " and is located on:");
        for(Point p : this.snake){
            System.out.println(p);
        }
    }

}
