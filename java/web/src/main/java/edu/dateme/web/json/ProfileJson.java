package edu.dateme.web.json;

import com.dateme.entities.Color;
import com.dateme.entities.Profile;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * {
 *     "email": "tim@example.com",
 *     "location": "washington dc",
 *     "salary": 10,
 *     "color": "#fff000"
 * }
 *
 */
public class ProfileJson {

    private String email;
    private String location;
    private int salary;

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public String getLocation() {
        return location;
    }

    @JsonProperty
    public int getSalary() {
        return salary;
    }

    @JsonProperty
    public String getColor() {
        return color;
    }

    private String color;

    public ProfileJson() {}

    public ProfileJson(Profile p) {
        this.email = p.email;
        this.location = p.location;
        this.salary = p.salary;
        this.color = p.color.toString();
    }

    public Profile asProfile() {
        return new Profile(email, location, salary, new Color(color));
    }

}
