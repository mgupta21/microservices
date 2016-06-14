package com.oreilly.microservices;

import retrofit.http.GET;

import java.util.Map;

public interface OtherMicroserviceClient {

    @GET("/data")
    Map<String, String> getData();
}
