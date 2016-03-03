package com.dateme.services;

import com.dateme.entities.Profile;
import com.dateme.entities.Color;
import com.dateme.services.ProfileService;
import org.junit.Assert;
import org.junit.Test;

public class ProfileServiceTest {

    @Test
    public void testColorCreation() {
        Color invalid = new Color(-4, 49999, -53);
        Color expected = new Color(0, 255, 0);

        Assert.assertTrue(
            invalid.red == expected.red &&
            invalid.green == expected.green &&
            invalid.blue == expected.blue
        );
    }



    @Test
    public void testCompatibilityScore() {

        Profile tim = new Profile("tim@example.com", "Washington, DC", 5000, new Color(0, 0, 255));
        Profile hannah = new Profile("???", "???", 10000, new Color(0, 0, 255));
        int score = ProfileService.getCompatScore(tim, hannah);

        Assert.assertEquals(10, score);

        Profile p1 = new Profile("", "", 0, new Color(0, 0, 0));
        Profile p2 = new Profile("", "", 0, new Color(255, 255, 255));

        Assert.assertEquals(0, ProfileService.getCompatScore(p1, p2));


        Profile p3 = new Profile("", "", 0, new Color(43, 204, 2));
        Profile p4 = new Profile("", "", 0, new Color(43, 204, 2));

        Assert.assertEquals(10, ProfileService.getCompatScore(p3, p4));

    }
    
}
