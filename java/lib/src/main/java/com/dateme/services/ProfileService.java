package com.dateme.services;

import com.dateme.entities.Profile;
import com.dateme.entities.Color;

public class ProfileService {

    public static int getCompatScore(Profile p1, Profile p2) {
        double distance = (int) Math.floor(p1.color.distance(p2.color));
        int invertDistance = (int) Math.abs(Color.MAX_COLOR_DISTANCE - distance);
        int normalized = 10 * invertDistance / Color.MAX_COLOR_DISTANCE;
        return normalized;
    }


}
