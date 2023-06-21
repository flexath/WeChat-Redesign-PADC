package com.flexath.moments.data.models

import com.flexath.moments.network.retrofit.TheMomentApi
import com.flexath.moments.utils.BASE_URL
import com.flexath.moments.utils.BASE_URL_FCM
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class RetrofitAbstractBaseModel {

    protected var mGiphyApi: TheMomentApi
    protected var mFCMApi:TheMomentApi

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        val retrofitFCM = Retrofit.Builder()
            .baseUrl(BASE_URL_FCM)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        mGiphyApi = retrofit.create(TheMomentApi::class.java)
        mFCMApi = retrofitFCM.create(TheMomentApi::class.java)
    }
}