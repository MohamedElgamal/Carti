package com.example.triples.Apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiManager {
    private ApiManager() {
    }
    /* old base url : http://smart-cart.ybjrsjcgnp-gok6781wz452.p.runcloud.link/api/*/
    private static Retrofit retrofit;

    private static Retrofit getInstance() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://carti.elkayal.me/api/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        return retrofit;
    }

    private static Retrofit getInstanceGsonFactory() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://carti.elkayal.me/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }

    public static WebServices getApis() {
        return getInstance().create(WebServices.class);
    }

    public static WebServices getApisGsonFactory() {
        return getInstanceGsonFactory().create(WebServices.class);
    }

}
