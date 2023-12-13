package com.technical.assessment.featuremain.data.repo


import com.technical.assessment.featuremain.data.remote.MainApiService
import com.technical.assessment.featuremain.domain.model.Album
import com.technical.assessment.featuremain.domain.model.Photo
import com.technical.assessment.featuremain.domain.repo.MainRepo

class MainRepoImpl(
    private val api: MainApiService
) : MainRepo {
    override suspend fun getUsers() = api.getUsers()
    override suspend fun getAlbums(userId:Int) = api.getAlbums(userId)
    override suspend fun getPhotos(albumId:Int) = api.getPhotos(albumId)
}