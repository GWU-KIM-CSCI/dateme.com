package com.dateme.api;

import com.dateme.dao.DateMeDAO;
import com.dateme.dao.ProfileDaoException;
import com.dateme.entities.Profile;
import com.dateme.services.ProfileService;

import java.util.List;
import java.util.Optional;

public class DateMeApi {
    private DateMeDAO dao;
    public DateMeApi(DateMeDAO dao) {
        this.dao = dao;
    }

    //Business logics
    public int compatibilityScore(String email1, String email2) throws ProfileDaoException {
        Profile p1 = getProfile(email1).get();
        Profile p2 = getProfile(email2).get();
//        long start = System.currentTimeMillis();
        int score = ProfileService.getCompatScore(p1,p2);
//        long end = System.currentTimeMillis();
//        long duration = end - start;
        return score;
    }

    public Profile createProfile(Profile profile) throws ProfileDaoException {
        return dao.createProfile(profile);
    }

    public Optional<Profile> getProfile(String email) throws ProfileDaoException {
        return dao.getProfile(email);
    }

    public Profile updateProfile(Profile profile) throws ProfileDaoException {
        return dao.updateProfile(profile);
    }

    public void deleteProfile(String email) throws ProfileDaoException {
        dao.deleteProfile(email);
    }

    public List<Profile> getAllProfiles() {
        return dao.allProfiles();
    }

//    public Result<Profile> createProfile(Profile profile) {
//        try {
//            return dao.createProfile(profile);
//        } catch (ProfileDaoException e) {
//            log.exception(e)
//        }
//    }
}
