package com.technical.assessment.featuremain.presentation.adapters.interfaces

import com.technical.assessment.featuremain.domain.model.Photo


interface PhotoEvent {
    fun onPhotoClickListener(photo: Photo)
}