package com.github.industrialcraft.pyrobite.location;

public class Position {
    private float x;
    private float y;
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position withX(float x){
        return new Position(x, this.y);
    }
    public Position withY(float y){
        return new Position(this.x, y);
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
