package com.example.driverfrenzi.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestClient {
    private static ApiServiceInterface mRestService = null;

 final  static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
         //error handle unexoecetd error
            .cache(null)//new Cache(sContext.getCacheDir(),10*1024*1024)


         .build();
//    OkHttpClient okHttpClient= null;
//    okHttpClient = new OkHttpClient.Builder()
//            .connectTimeout(2, TimeUnit.MINUTES)
//                .readTimeout(2, TimeUnit.MINUTES)
//                .writeTimeout(2, TimeUnit.MINUTES)
//                .retryOnConnectionFailure(false)
//                .cache(null)//new Cache(sContext.getCacheDir(),10*1024*1024)
//                .build();

    public static ApiServiceInterface getClient() {
        if (mRestService == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();



//            final OkHttpClient client = new OkHttpClient
//                    .Builder()
//                    .addInterceptor(new FakeInterceptor(context))
//                    .build();

            final Retrofit retrofit = new Retrofit.Builder()
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))  // Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $
                    .baseUrl(ApiServiceInterface.BASE_URL)
                    //.baseUrl(ApiServiceInterface.BASE_URL_NEW)
                    .client(okHttpClient)
                    .build();

            mRestService = retrofit.create(ApiServiceInterface.class);
        }
        return mRestService;
    }
}
