package edu.dateme.web.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoreJson {
    private String profile1;
    private String profile2;
    private int score;

    public ScoreJson() {}

    public ScoreJson(String p1, String p2, int score) {
        this.profile1 = p1;
        this.profile2 = p2;
        this.score = score;
    }


    @JsonProperty
    public String getProfile1() {
        return profile1;
    }

    @JsonProperty
    public String getProfile2() {
        return profile2;
    }

    @JsonProperty
    public int getScore() {
        return score;
    }
}
