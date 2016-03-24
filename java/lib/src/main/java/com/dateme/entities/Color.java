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

    public Color(String c) {
        int redValue = 255;
        int greenValue  = 0;
        int blueValue = 0;
        try {
            String r = c.substring(1,3);
            String g = c.substring(3,5);
            String b = c.substring(5,7);
            redValue = Integer.valueOf(r, 16);
            greenValue  = Integer.valueOf(g, 16);
            blueValue = Integer.valueOf(b, 16);
        } catch (Exception e) { }
        this.red = redValue;
        this.green = greenValue;
        this.blue = blueValue;
    }


    public static final int MAX_COLOR_DISTANCE = 442;

    public double distance(Color c) {
        return Math.sqrt(
            Math.pow(this.red - c.red, 2) + 
            Math.pow(this.green - c.green , 2) + 
            Math.pow(this.blue - c.blue , 2)
        );
    }

    @Override
    public String toString() {
        String red = toHexString(this.red);
        String green = toHexString(this.green);
        String blue = toHexString(this.blue);
        return "#" + red + green + blue;
    }

    private String toHexString(int color) {
        String hex = Integer.toString(color, 16);
        if (color < 16) {
            return "0" + hex;
        } else {
            return hex;
        }
    }


    public boolean equals(Object right) {
        if (right instanceof Color) {
            Color r = (Color) right;
            return this.red == r.red && this.green == r.green && this.blue == r.blue;
        } else {
            return false;
        }
    }

}















