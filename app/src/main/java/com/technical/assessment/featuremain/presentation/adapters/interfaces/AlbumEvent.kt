package com.technical.assessment.featuremain.presentation.adapters.interfaces

import com.technical.assessment.featuremain.domain.model.Album


interface AlbumEvent {
    fun onAlbumClickListener(album: Album)
}