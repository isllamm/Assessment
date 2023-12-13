package com.technical.assessment.featuremain.domain.repo

import com.technical.assessment.featuremain.domain.model.Album
import com.technical.assessment.featuremain.domain.model.Photo
import com.technical.assessment.featuremain.domain.model.User


interface MainRepo {
    suspend fun getUsers(): MutableList<User>
    suspend fun getAlbums(userId:Int): MutableList<Album>
    suspend fun getPhotos(albumId:Int): MutableList<Photo>
}