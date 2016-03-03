package com.dateme.entities;

public class Color {

    public final int red;
    public final int green;
    public final int blue;


    private int normalize(int i) {
        if (i < 0) return 0;
        if (i > 255) return 255;
        else return i;
    }

    public Color(int r, int g, int b) {
        this.red = normalize(r);
        this.green = normalize(g);
        this.blue = normalize(b);
    }

    public static final int MAX_COLOR_DISTANCE = 442;

    public double distance(Color c) {
        return Math.sqrt(
            Math.pow(this.red - c.red, 2) + 
            Math.pow(this.green - c.green , 2) + 
            Math.pow(this.blue - c.blue , 2)
        );
    }

}
