package com.madproject.core_ui.animation

import androidx.navigation.NavOptions
import com.madproject.core_ui.R

fun navOptionIsModal() =
    NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_up_enter)
        .setExitAnim(R.anim.slide_out_up_enter)
        .setPopEnterAnim(R.anim.slide_in_up_exit)
        .setPopExitAnim(R.anim.slide_out_up_exit)
        .setLaunchSingleTop(true)
        .build()
