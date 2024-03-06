package com.example.foodapp.data.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Handles connection and requests to the API
 *  This class is vastly reusing code as provided in "TD 5. Appels réseaux"
 */
public class APIClient {

    // The base URL of the API.
    public static final String BASE_URL ="https://www.themealdb.com/api/json/v1/1/";

    // The Retrofit client instance.
    private static Retrofit retrofit;

    /**
     * This method returns the Retrofit client instance. If it has not yet been initialized,
     * it will create a new instance using the base URL and add the necessary converter factories
     * and call adapter factories.
     *
     * @return the Retrofit client instance.
     */
    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

