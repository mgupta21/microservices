package com.oreilly.microservices;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;
import redis.clients.jedis.Jedis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Map;

public class MyMicroservice extends Microservice {

    public static void main(String... args) {
        new MyMicroservice().run();
    }

    @Override
    public Module[] getModules() {
        return new Module[] {
            new RequestScopeModule() {
                @Override
                protected void configure()
                {
                    bind(DataResource.class).in(Scopes.SINGLETON);
                    bind(RedisHealthCheck.class).in(Scopes.SINGLETON);
                }

                @Provides
                @Singleton
                public Jedis jedis(Config config) {
                    Jedis jedis = new Jedis(config.getString("redis.host"),
                            config.getInt("redis.port"));

                    // side effect for demo purposes only
                    jedis.set("data", "true");

                    return jedis;
                }
            }
        };
    }

    @Path("/")
    public static class DataResource {

        @Inject
        private Jedis jedis;

        @GET
        @Produces("application/json")
        public Map<String, Boolean> getData() {
            return ImmutableMap.of("data", Boolean.valueOf(jedis.get("data")));
        }
    }
}
