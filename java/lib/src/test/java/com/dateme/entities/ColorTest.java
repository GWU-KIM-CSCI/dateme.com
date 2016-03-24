package com.dateme.entities;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {

    @Test
    public void toStringTest() throws Exception {

        Color color1 = new Color(0, 0, 0);
        Assert.assertEquals(color1.toString(), "#000000");

        Color color2 = new Color(255, 255, 255);
        Assert.assertEquals(color2.toString(), "#ffffff");

        Color color3 = new Color(5, 16, 10);
        Assert.assertEquals(color3.toString(), "#05100a");
    }

    @Test
    public void deserialization() throws Exception {

        String color = "#10ff0a";
        Color c = new Color(color);
        Assert.assertEquals(c.red, 16);
        Assert.assertEquals(c.green, 255);
        Assert.assertEquals(c.blue, 10);


        Color c2 = new Color("asdfASdzxcvxf");
        Assert.assertEquals(c2.red, 255);
        Assert.assertEquals(c2.green, 0);
        Assert.assertEquals(c2.blue, 0);

    }
}