package com.technical.assessment.featuremain.presentation.fragments.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technical.assessment.core.Resource
import com.technical.assessment.featuremain.domain.model.Album
import com.technical.assessment.featuremain.domain.model.Photo
import com.technical.assessment.featuremain.domain.model.User
import com.technical.assessment.featuremain.domain.usecase.AlbumsUseCase
import com.technical.assessment.featuremain.domain.usecase.PhotosUseCase
import com.technical.assessment.featuremain.domain.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel
@Inject
constructor(
    private val photosUseCase: PhotosUseCase,

): ViewModel(){
    private val _photosFlow = MutableSharedFlow<Resource<MutableList<Photo>>>()
    val photosFlow = _photosFlow.asSharedFlow()

    fun getPhotos(albumId:Int) = viewModelScope.launch {
        _photosFlow.emit(Resource.Loading())
        try {
            val response = photosUseCase(albumId)
            if (response.isNotEmpty()){
                _photosFlow.emit(Resource.Success(response))
            }else{
                _photosFlow.emit(Resource.Error(message = "There is no Photos available"))
            }
        }catch (e: Exception){
            _photosFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}