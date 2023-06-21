package com.flexath.moments.data.models

import android.annotation.SuppressLint
import com.flexath.moments.data.vos.giphy.Data
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

object GiphyModelImpl : GiphyModel,RetrofitAbstractBaseModel() {

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