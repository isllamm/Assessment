package com.technical.assessment.featuremain.presentation.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technical.assessment.core.Resource
import com.technical.assessment.featuremain.domain.model.Album
import com.technical.assessment.featuremain.domain.model.User
import com.technical.assessment.featuremain.domain.usecase.AlbumsUseCase
import com.technical.assessment.featuremain.domain.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val usersUseCase: UsersUseCase,
    private val albumsUseCase: AlbumsUseCase,
): ViewModel(){


    private val _usersFlow = MutableSharedFlow<Resource<MutableList<User>>>()
    val usersFlow = _usersFlow.asSharedFlow()
    private val _albumsFlow = MutableSharedFlow<Resource<MutableList<Album>>>()
    val albumsFlow = _albumsFlow.asSharedFlow()


     fun getUsers() = viewModelScope.launch {
        _usersFlow.emit(Resource.Loading())
        try {
            val response = usersUseCase()
            if (response.isNotEmpty()){
                _usersFlow.emit(Resource.Success(response))
            }else{
                _usersFlow.emit(Resource.Error(message = "There is no users available"))
            }
        }catch (e: Exception){
            _usersFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun getAlbums(userId:Int) = viewModelScope.launch {
        _albumsFlow.emit(Resource.Loading())
        try {
            val response = albumsUseCase(userId)
            if (response.isNotEmpty()){
                _albumsFlow.emit(Resource.Success(response))
            }else{
                _albumsFlow.emit(Resource.Error(message = "There is no albums available"))
            }
        }catch (e: Exception){
            _albumsFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }
}