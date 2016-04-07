package edu.dateme.web;

import com.dateme.api.DateMeApi;
import com.dateme.dao.DateMeDAO;
import com.dateme.dao.SqliteDAO;
import edu.dateme.web.health.DateMeHealthCheck;
import edu.dateme.web.resources.ProfileResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class DateMeWeb extends Application<DateMeConfiguration> {

    public static void main(String[] args) throws Exception {
        new DateMeWeb().run(args);
    }

    @Override
    public void run(DateMeConfiguration config, Environment env) throws Exception {
        final DateMeDAO dao = new SqliteDAO(config.getDb());
        final DateMeApi api = new DateMeApi(dao);

        final DateMeHealthCheck healthCheck = new DateMeHealthCheck();
        env.healthChecks().register("dateme", healthCheck);

        final ProfileResource profileResource = new ProfileResource(api);
        env.jersey().setUrlPattern("/api/*");
        env.jersey().register(profileResource);

    }
}
