package com.example.maximus09.spfsupply;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SpfAPI {

    @POST("{user}")
    Call<ResponseBody> signin(
            @HeaderMap Map<String, String> headers,
            @Query("email") String email, // admin@gmail.com
            @Query("pass") String password // Pass1234

    );

}

