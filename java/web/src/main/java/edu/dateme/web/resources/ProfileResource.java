package edu.dateme.web.resources;


import com.codahale.metrics.annotation.Timed;
import com.dateme.api.DateMeApi;
import com.dateme.dao.ProfileDaoException;
import com.dateme.entities.Profile;
import edu.dateme.web.json.ProfileJson;
import edu.dateme.web.json.ScoreJson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/api/profile")
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

    private DateMeApi api;

    public ProfileResource(DateMeApi api) {
        this.api = api;
    }

    @GET
    @Path("/{email}/compare")
    @Timed
    public Response getScore(@PathParam("email") String me, @QueryParam("with") String other) throws ProfileDaoException {
        int score = api.compatibilityScore(me, other);
        ScoreJson json = new ScoreJson(me, other, score);
        return Response.ok(json).build();
    }

    @GET
    @Path("/{email}")
    @Timed
    public Response getProfile(@PathParam("email") String email) throws ProfileDaoException {
        Optional<Profile> result = api.getProfile(email);
        if (result.isPresent()) {
            Profile profile = result.get();
            return Response.ok(new ProfileJson(profile)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Timed
    public Response createProfile(ProfileJson json) throws ProfileDaoException {
        Profile profile = json.asProfile();
        Profile created = api.createProfile(profile);
        return Response.ok(new ProfileJson(created)).build();
    }


}














