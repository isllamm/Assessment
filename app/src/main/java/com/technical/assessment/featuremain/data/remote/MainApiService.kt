package com.technical.assessment.featuremain.data.remote

import com.technical.assessment.featuremain.domain.model.*
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


interface MainApiService {
    @GET("users")
    suspend fun getUsers(): MutableList<User>
    @GET("albums")
    suspend fun getAlbums(@Query("userId") albumId:Int): MutableList<Album>
    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId:Int): MutableList<Photo>
}