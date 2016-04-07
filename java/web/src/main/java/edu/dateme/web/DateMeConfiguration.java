package edu.dateme.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class DateMeConfiguration extends Configuration {

    @NotEmpty
    private String db;


    @JsonProperty
    public String getDb() {
        return db;
    }

    @JsonProperty
    public void setDb(String db) {
        this.db = db;
    }
}
