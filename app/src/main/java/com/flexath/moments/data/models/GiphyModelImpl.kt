package com.flexath.moments.data.models

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.flexath.moments.data.vos.giphy.Data
import com.flexath.moments.network.retrofit.TheMomentApi
import com.flexath.moments.utils.BASE_URL
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GiphyModelImpl : GiphyModel {

    private var mGiphyApi: TheMomentApi

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

        mGiphyApi = retrofit.create(TheMomentApi::class.java)
    }

    @SuppressLint("CheckResult")
    override fun getAllTrendingGifs(
        onSuccess:(List<Data>) -> Unit,
        onFailure: (String) -> Unit
    ){
        mGiphyApi.getAllTrendingGifs()
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe({
                val data = it.data ?: listOf()
                onSuccess(data)
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    @SuppressLint("CheckResult")
    override fun searchGifs(query: String): Observable<List<Data>> {
        return mGiphyApi.searchGifs(q = query)
            .map {
                it.data ?: listOf()
            }
            .onErrorResumeNext{
                Observable.just(listOf())
            }
            .subscribeOn(Schedulers.io())
    }


}