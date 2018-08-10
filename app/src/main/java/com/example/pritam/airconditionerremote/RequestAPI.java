package com.example.pritam.airconditionerremote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RequestAPI {
    @GET
    Call<AirConditionerResponse> AcResponse(@Url String url);
}
