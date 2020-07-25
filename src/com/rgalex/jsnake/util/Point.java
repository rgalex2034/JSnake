package com.rgalex.jsnake.util;

public class Point{

    public final int x;
    public final int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point translate(int x, int y){
        return new Point(this.x + x, this.y + y);
    }

    public Point translate(Point p){
        return this.translate(p.x, p.y);
    }

    public Point sub(Point p){
        return new Point(this.x - p.x, this.y - p.y);
    }

    @Override
    public boolean equals(Object o){

        if(o instanceof Point){
            Point p = (Point)o;
            return this.x == p.x && this.y == p.y;
        }

        return false;
    }

    @Override
    public String toString(){
        return String.format("X:%d - Y:%d", this.x, this.y);
    }

}
