package com.example.feature_main.screens.videoGamesScreen

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_common.naigation.NavCommand
import com.example.core_common.naigation.NavCommands
import com.example.core_common.naigation.Screen
import com.example.core_common.naigation.navigation
import com.example.feature_main.R
import com.example.feature_main.databinding.FragmentVideoGamesBinding
import com.example.feature_main.di.MainComponentViewModel
import com.example.feature_main.screens.videoGamesScreen.adapter.VideoGameVerticalAdapter
import com.example.feature_main.screens.videoGamesScreen.viewModel.VideoGamesViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoGamesFragment : Fragment(R.layout.fragment_video_games), SearchView.OnQueryTextListener {

    @Inject
    internal lateinit var videoGamesViewModelFactory:Lazy<VideoGamesViewModel.Factory>

    private val viewModel by viewModels<VideoGamesViewModel> {
        videoGamesViewModelFactory.get()
    }

    private val videoGameVerticalAdapter by lazy(LazyThreadSafetyMode.NONE){
        VideoGameVerticalAdapter(
            onClickVideoGame = { videGame ->
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.VideoGameInfo.arguments(
                                videoGameId = videGame?.id ?: 0
                            )),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            }
        )
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainComponentViewModel>()
            .mainDetailsComponent.injectVideoGames(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentVideoGamesBinding.bind(view)

        val videoGamesLayoutManager = LinearLayoutManager(this.context)

        viewModel.getVideoGames()
        lifecycleScope.launch {
            viewModel.responseVideoGames.collectLatest(videoGameVerticalAdapter::submitData)
        }

        binding.topToolbar.menu.forEach { menuItem ->
            when(menuItem.itemId){
                R.id.search -> {
                    val search = menuItem.actionView as SearchView
                    search.isSubmitButtonEnabled = true
                    search.setOnQueryTextListener(this)
                }
            }
        }

        binding.videoGames.adapter = videoGameVerticalAdapter
        binding.videoGames.layoutManager = videoGamesLayoutManager

        binding.topToolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onQueryTextSubmit(search: String?): Boolean {
        viewModel.getVideoGames(search = search)
        return true
    }

    override fun onQueryTextChange(search: String?): Boolean {
        viewModel.getVideoGames(search = search)
        return true
    }
}