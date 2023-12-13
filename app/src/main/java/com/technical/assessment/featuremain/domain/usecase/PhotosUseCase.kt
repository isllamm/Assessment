package com.technical.assessment.featuremain.domain.usecase

import com.technical.assessment.featuremain.domain.repo.MainRepo
import javax.inject.Inject

class PhotosUseCase
@Inject
constructor(
    private val repo: MainRepo
){
    suspend operator fun invoke(albumId:Int) = repo.getPhotos(albumId)
}