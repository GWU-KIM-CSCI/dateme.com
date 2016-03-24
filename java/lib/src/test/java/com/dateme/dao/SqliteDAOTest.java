package com.dateme.dao;

import com.dateme.entities.Color;
import com.dateme.entities.Profile;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.*;

public class SqliteDAOTest {

    private String dbLocation = "/tmp/dateme.db";
    private DateMeDAO dao = new SqliteDAO("jdbc:sqlite:" + dbLocation);


    @Test
    public void createProfileTable() throws Exception {

        Path dbFile = Paths.get(dbLocation);

        Assert.assertTrue(dbFile.toFile().exists());
    }

    @Test
    public void createProfile() throws Exception {

        Profile profile = new Profile("bob@example.com", "Washington, DC", 100000, new Color(100, 200, 60));
        dao.createProfile(profile);
        Optional<Profile> result = dao.getProfile("bob@example.com");
        Assert.assertTrue(result.isPresent());

        if (result.isPresent()) {
            Profile bob = result.get();
            Assert.assertEquals(profile, bob);
        }



    }

}



















