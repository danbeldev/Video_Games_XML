package com.example.feature_main.screens.videoGamesScreen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.feature_main.R
import com.example.feature_main.databinding.FragmentVideoGamesBinding
import com.example.feature_main.di.MainComponentViewModel
import com.example.feature_main.screens.videoGamesScreen.viewModel.VideoGamesViewModel
import dagger.Lazy
import javax.inject.Inject

class VideoGamesFragment : Fragment(R.layout.fragment_video_games) {

    @Inject
    internal lateinit var videoGamesViewModelFactory:Lazy<VideoGamesViewModel.Factory>

    private val viewModel by viewModels<VideoGamesViewModel> {
        videoGamesViewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainComponentViewModel>()
            .mainDetailsComponent.injectVideoGames(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentVideoGamesBinding.bind(view)
    }
}