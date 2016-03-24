package com.dateme.dao;

import com.dateme.entities.Profile;

import java.util.List;
import java.util.Optional;

public interface DateMeDAO {

    public Profile createProfile(Profile profile) throws ProfileDaoException;

    public Optional<Profile> getProfile(String email) throws ProfileDaoException;

    public Profile updateProfile(Profile profile) throws ProfileDaoException;

    public void deleteProfile(String email) throws ProfileDaoException;

    public List<Profile> allProfiles();

}
