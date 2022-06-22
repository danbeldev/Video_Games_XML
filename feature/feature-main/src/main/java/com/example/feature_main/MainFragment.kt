package com.example.feature_main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_common.naigation.NavCommand
import com.example.core_common.naigation.NavCommands
import com.example.core_common.naigation.Screen
import com.example.core_common.naigation.navigation
import com.example.feature_main.adapter.CreatorsAdapter
import com.example.feature_main.adapter.VideoGamesPagerAdapter
import com.example.feature_main.databinding.FragmentMainBinding
import com.example.feature_main.di.MainComponentViewModel
import com.example.feature_main.viewModel.MainViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    internal lateinit var mainViewModelFactory:Lazy<MainViewModel.Factor>

    private val viewModel by viewModels<MainViewModel> {
        mainViewModelFactory.get()
    }

    private val videoGamesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        VideoGamesPagerAdapter { id ->
            navigation(
                NavCommand(
                    NavCommands.DeepLink(
                        url = Uri.parse(Screen.VideoGameInfo.arguments(
                            videoGameId = id
                        )),
                        isModal = true,
                        isSingleTop = true
                    )
                )
            )
        }
    }

    private val creatorsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CreatorsAdapter()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainComponentViewModel>()
            .mainDetailsComponent.inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMainBinding.bind(view)

        binding.videoGamesRecyclerView.adapter = videoGamesAdapter
        binding.creatorsRecyclerView.adapter = creatorsAdapter

        lifecycleScope.launch{
            viewModel.videoGames.collectLatest(videoGamesAdapter::submitData)
        }

        lifecycleScope.launch{
            viewModel.creators.collectLatest(creatorsAdapter::submitData)
        }

        val videoGameLayoutManager = LinearLayoutManager(this.context)
        val creatorLayoutManager = LinearLayoutManager(this.context)
//            GridLayoutManager(this.context, 2)
        videoGameLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        creatorLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.videoGamesRecyclerView.layoutManager = videoGameLayoutManager
        binding.creatorsRecyclerView.layoutManager = creatorLayoutManager
    }
}