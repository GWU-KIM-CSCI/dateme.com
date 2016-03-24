package com.dateme.dao;

import com.dateme.entities.Color;
import com.dateme.entities.Profile;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class SqliteDAO implements DateMeDAO {

    private Connection connection = null;

    public SqliteDAO(String dburl) {
        try {
            connection = DriverManager.getConnection(dburl);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        String createProfileTable = "CREATE TABLE IF NOT EXISTS profiles(email TEXT, location TEXT, salary INTEGER, color TEXT);";

        try {
            PreparedStatement statement = connection.prepareStatement(createProfileTable);
            statement.setQueryTimeout(30);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    @Override
    public Profile createProfile(Profile profile) throws ProfileDaoException {

        String query = "INSERT INTO profiles (email, location, salary, color) VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, profile.email);
            statement.setString(2, profile.location);
            statement.setInt(3, profile.salary);
            statement.setString(4, profile.color.toString());
            statement.setQueryTimeout(30);
            statement.execute();
            return profile;
        } catch (SQLException e) {
            throw new ProfileDaoException("Profile creation failed. " + profile.email, e);
        }
    }

    @Override
    public Optional<Profile> getProfile(String email) throws ProfileDaoException {
        String query = "SELECT * FROM profiles WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String e = result.getString("email");
                String l = result.getString("location");
                int s = result.getInt("salary");
                String c = result.getString("color");

                Profile profile = new Profile(e, l, s, new Color(c));
                return Optional.of(profile);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new ProfileDaoException("Retreiving profile from db failed. " + email, e);
        }
    }

    @Override
    public Profile updateProfile(Profile profile) throws ProfileDaoException {
        return null;
    }

    @Override
    public void deleteProfile(String email) throws ProfileDaoException {

    }

    @Override
    public List<Profile> allProfiles() {
        return null;
    }
}
