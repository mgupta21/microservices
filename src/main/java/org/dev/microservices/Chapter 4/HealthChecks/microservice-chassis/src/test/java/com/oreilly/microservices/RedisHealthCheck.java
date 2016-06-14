package com.oreilly.microservices;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import redis.clients.jedis.Jedis;

public class RedisHealthCheck extends HealthCheck {

    @Inject
    private Jedis jedis;

    @Override
    protected Result check() throws Exception {
        try {
            String code = jedis.ping();

            if(code.startsWith("PONG")) {
                return Result.healthy("OK");
            } else {
                return Result.unhealthy(code);
            }
        } catch(Exception ex) {
            return Result.unhealthy(ex);
        }
    }
}
