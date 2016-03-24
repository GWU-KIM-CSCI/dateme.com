package com.dateme.entities;

public class Profile {

    public final String email;
    public final String location;
    public final int salary;
    public final Color color;

    public Profile(String e, String l, int s, Color c) {
        this.email = e;
        this.location = l;
        this.salary = s;
        this.color = c;
    }

    public boolean equals(Object right) {
        if (right instanceof Profile) {
            Profile r = (Profile) right;
            return this.email.equals(r.email) && this.location.equals(r.location) && this.salary == r.salary && this.color.equals(r.color);

        } else {
            return false;
        }
    }

}
