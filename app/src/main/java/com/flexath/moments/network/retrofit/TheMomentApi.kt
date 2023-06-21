package com.flexath.moments.network.retrofit

import com.flexath.moments.data.vos.fcm.FCMBody
import com.flexath.moments.network.retrofit.responses.FCMResponse
import com.flexath.moments.network.retrofit.responses.TrendingGifResponse
import com.flexath.moments.utils.API_GET_GIPHY_SEARCH
import com.flexath.moments.utils.API_GET_GIPHY_TRENDING
import com.flexath.moments.utils.API_POST_FCM
import com.flexath.moments.utils.FCM_AUTH_KEY
import com.flexath.moments.utils.GIPHY_API_KEY
import com.flexath.moments.utils.HEADER_ACCEPT
import com.flexath.moments.utils.HEADER_ACCEPT_ENCODING
import com.flexath.moments.utils.HEADER_AUTH
import com.flexath.moments.utils.HEADER_CONNECTION
import com.flexath.moments.utils.HEADER_CONTENT_LENGTH
import com.flexath.moments.utils.HEADER_CONTENT_TYPE
import com.flexath.moments.utils.HEADER_HOST
import com.flexath.moments.utils.HEADER_USER_AGENT
import com.flexath.moments.utils.PARAM_API_KEY
import com.flexath.moments.utils.PARAM_API_KEY_SEARCH
import com.flexath.moments.utils.PARAM_API_LIMIT
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TheMomentApi {

    @GET(API_GET_GIPHY_TRENDING)
    fun getAllTrendingGifs(
        @Query(PARAM_API_KEY) api_key:String = GIPHY_API_KEY,
        @Query(PARAM_API_LIMIT) limit:String = "10"
    ) : Observable<TrendingGifResponse>

    @GET(API_GET_GIPHY_SEARCH)
    fun searchGifs(
        @Query(PARAM_API_KEY) api_key:String = GIPHY_API_KEY,
        @Query(PARAM_API_KEY_SEARCH) q:String = "trending",
        @Query(PARAM_API_LIMIT) limit:String = "5"
    ) : Observable<TrendingGifResponse>

    @POST(API_POST_FCM)
    fun sendFCMNotification(
        @Header(HEADER_AUTH) authorization:String = FCM_AUTH_KEY,
        @Body fcmBody:FCMBody
    ) : Call<FCMResponse>
}