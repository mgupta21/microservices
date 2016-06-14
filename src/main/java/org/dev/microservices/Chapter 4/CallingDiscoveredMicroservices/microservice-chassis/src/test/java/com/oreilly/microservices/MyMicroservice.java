package com.oreilly.microservices;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.oreilly.microservices.discovery.ClientFactory;
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
                    bind(DataResource.class).in(Scopes.SINGLETON);
                }

                @Provides
                @Singleton
                public OtherMicroserviceClient otherMicroserviceClient(ClientFactory factory) {
                    return factory.buildClient("other-microservice", OtherMicroserviceClient.class);
                }
            }
        };
    }

    @Path("/")
    public static class DataResource {

        @Inject
        private OtherMicroserviceClient otherMicroserviceClient;

        @GET
        @Produces("application/json")
        public Map<String, Map<String, String>> getOtherData() {
            return ImmutableMap.of("retrievedFromOtherMicroservice",
                    otherMicroserviceClient.getData());
        }
    }
}
