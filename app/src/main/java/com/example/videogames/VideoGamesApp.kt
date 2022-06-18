package com.example.videogames

import android.app.Application
import com.example.feature_main.di.MainDepsStore
import com.example.videogames.di.AppComponent
import com.example.videogames.di.DaggerAppComponent

class VideoGamesApp:Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        MainDepsStore.deps = appComponent
    }
}