package com.oreilly.microservices;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.typesafe.config.Config;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;

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
                    bind(ConfigurationResource.class).in(Scopes.SINGLETON);
                }
            }
        };
    }

    @Path("/")
    public static class ConfigurationResource {

        @Inject
        private Config config;

        @GET
        @Produces("application/json")
        public Map<String, String> getResource() {
            return ImmutableMap.of("sample.config", config.getString("sample.config"),
                    "microservice.name", config.getString("microservice.name"));
        }
    }
}
