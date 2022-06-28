package com.example.feature_main.screens.videoGamesScreen

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
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

        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.CREATED){
                viewModel.getVideoGames().collectLatest(videoGameVerticalAdapter::submitData)
            }
        }

        binding.videoGames.adapter = videoGameVerticalAdapter
        binding.videoGames.layoutManager = videoGamesLayoutManager

        binding.topToolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }

        binding.topToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.search -> {
                    val search = menuItem.actionView as SearchView
                    search.isSubmitButtonEnabled = true
                    search.setOnQueryTextListener(this)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuInflater.inflate(R.menu.video_games_top_app_bar, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(search: String?): Boolean {
        Toast.makeText(this.context, search, Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onQueryTextChange(search: String?): Boolean {
        Toast.makeText(this.context, search, Toast.LENGTH_SHORT).show()
        return true
    }
}