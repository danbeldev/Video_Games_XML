package com.madproject.videogames

import android.app.Application
import com.madproject.feature_creator_info.di.CreatorDepsStore
import com.madproject.feature_main.di.MainDepsStore
import com.madproject.feature_platform_info.di.PlatformDepsStore
import com.madproject.feature_store_info.di.StoreDepsStore
import com.madproject.feature_video_game_info.di.GameInfoDepsStore
import com.madproject.videogames.di.AppComponent
import com.madproject.videogames.di.DaggerAppComponent
import com.yandex.mobile.ads.common.MobileAds

class VideoGamesApp:Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        MainDepsStore.deps = appComponent

        GameInfoDepsStore.deps = appComponent

        PlatformDepsStore.deps = appComponent

        CreatorDepsStore.deps = appComponent

        StoreDepsStore.deps = appComponent

        MobileAds.initialize(this){  }
    }
}