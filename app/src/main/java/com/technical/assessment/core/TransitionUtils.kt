package com.technical.assessment.core

import androidx.navigation.NavOptions
import com.technical.assessment.R

object TransitionUtils {
    val generalAnimationOptions by lazy {
        NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out).build()
    }
}