package com.flexath.moments.data.models

import androidx.lifecycle.LiveData
import com.flexath.moments.data.vos.giphy.Data
import io.reactivex.rxjava3.core.Observable

interface GiphyModel {

    fun getAllTrendingGifs(
        onSuccess:(List<Data>) -> Unit,
        onFailure:(String) -> Unit
    )

    fun searchGifs(
        query:String
    ) : Observable<List<Data>>
}