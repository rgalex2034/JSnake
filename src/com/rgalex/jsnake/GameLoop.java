package com.rgalex.jsnake;

import java.util.Timer;
import java.util.TimerTask;

public abstract class GameLoop extends TimerTask{

    public static final int TICKS_PER_SECOND = 60;

    private long last = 0;

    public void start(){
        long tickTime = 1000/GameLoop.TICKS_PER_SECOND;
        Timer gameLoop = new Timer();
        gameLoop.schedule((TimerTask)this, tickTime, tickTime);
    }

    @Override
    public void run(){
        long now   = System.nanoTime();
        long delta = this.last == 0 ? 0 : now - this.last;
        delta /= 1000000;//Convert to ms

        this.loop(delta);

        this.last = now;
    }

    public abstract void loop(long delta);

}
