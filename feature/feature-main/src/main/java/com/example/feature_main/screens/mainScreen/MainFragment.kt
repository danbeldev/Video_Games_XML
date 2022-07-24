package com.example.feature_main.screens.mainScreen

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_common.naigation.NavCommand
import com.example.core_common.naigation.NavCommands
import com.example.core_common.naigation.Screen
import com.example.core_common.naigation.navigation
import com.example.core_ui.animation.navOptionIsModal
import com.example.feature_main.R
import com.example.feature_main.databinding.FragmentMainBinding
import com.example.feature_main.di.MainComponentViewModel
import com.example.feature_main.screens.mainScreen.adapter.CreatorsAdapter
import com.example.feature_main.screens.mainScreen.adapter.PlatformsAdapter
import com.example.feature_main.screens.mainScreen.adapter.StoresAdapter
import com.example.feature_main.screens.mainScreen.adapter.TagsAdapter
import com.example.feature_main.screens.mainScreen.adapter.VideoGamesHorizontalAdapter
import com.example.feature_main.screens.mainScreen.viewModel.MainViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    internal lateinit var mainViewModelFactory:Lazy<MainViewModel.Factor>

    private val viewModel by viewModels<MainViewModel> {
        mainViewModelFactory.get()
    }

    private val videoGamesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        VideoGamesHorizontalAdapter { id ->
            navigation(
                NavCommand(
                    NavCommands.DeepLink(
                        url = Uri.parse(Screen.VideoGameInfo.arguments(
                            videoGameId = id
                        )),
                        isModal = true,
                        isSingleTop = false
                    )
                )
            )
        }
    }

    private val platformAdapter by lazy(LazyThreadSafetyMode.NONE){
        PlatformsAdapter(
            onPlatformItemClick = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.PlatformInfo.arguments(it?.id ?: 0)),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            },
            onClickGame = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.VideoGameInfo.arguments(
                                videoGameId = it?.id ?: 0
                            )),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            }
        )
    }

    private val creatorsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CreatorsAdapter(
            onClickCreator = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.CreatorInfo.arguments(it?.id ?: 0)),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            },
            onClickVideoGame = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.VideoGameInfo.arguments(
                                videoGameId = it?.id ?: 0
                            )),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            }
        )
    }

    private val storesAdapter by lazy(LazyThreadSafetyMode.NONE){
        StoresAdapter(
            onClickStore = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.StoreInfo.arguments(
                                storeId = it?.id ?: 0
                            )),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            },
            onClickVideoGame = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.VideoGameInfo.arguments(
                                videoGameId = it?.id ?: 0
                            )),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            }
        )
    }

    private val tagsAdapter by lazy(LazyThreadSafetyMode.NONE){
        TagsAdapter(
            onClickVideoGame = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.VideoGameInfo.arguments(
                                videoGameId = it.id
                            )),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            },
            onClickTag = {

            }
        )
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainComponentViewModel>()
            .mainDetailsComponent.injectMainFragment(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMainBinding.bind(view)

        val videoGameLayoutManager = LinearLayoutManager(this.context)
        val creatorLayoutManager = LinearLayoutManager(this.context)
        val platformLayoutManager = LinearLayoutManager(this.context)
        val storesLayoutManager = LinearLayoutManager(this.context)
        val tagLayoutManager = LinearLayoutManager(this.context)

        lifecycleScope.launchWhenCreated {
            lifecycle.whenStateAtLeast(Lifecycle.State.CREATED){
                viewModel.videoGames.collectLatest(videoGamesAdapter::submitData)
            }
        }

        lifecycleScope.launchWhenCreated{
            lifecycle.whenStateAtLeast(Lifecycle.State.CREATED){
                viewModel.creators.collectLatest(creatorsAdapter::submitData)
            }
        }

        lifecycleScope.launchWhenCreated {
            lifecycle.whenStateAtLeast(Lifecycle.State.CREATED){
                viewModel.platforms.collectLatest(platformAdapter::submitData)
            }
        }

        lifecycleScope.launchWhenCreated {
            lifecycle.whenStateAtLeast(Lifecycle.State.CREATED){
                viewModel.stores.collectLatest(storesAdapter::submitData)
            }
        }

        lifecycleScope.launchWhenCreated {
            lifecycle.whenStateAtLeast(Lifecycle.State.CREATED){
                viewModel.tags.collectLatest(tagsAdapter::submitData)
            }
        }

        binding.platformRecyclerView.adapter = platformAdapter
        binding.videoGamesRecyclerView.adapter = videoGamesAdapter
        binding.creatorsRecyclerView.adapter = creatorsAdapter
        binding.storeRecyclerView.adapter = storesAdapter
        binding.tagsRecyclerView.adapter = tagsAdapter

        videoGameLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        creatorLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        platformLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        storesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        tagLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        binding.videoGamesRecyclerView.layoutManager = videoGameLayoutManager
        binding.creatorsRecyclerView.layoutManager = creatorLayoutManager
        binding.platformRecyclerView.layoutManager = platformLayoutManager
        binding.storeRecyclerView.layoutManager = storesLayoutManager
        binding.tagsRecyclerView.layoutManager = tagLayoutManager

        binding.videoGamesText.setOnClickListener {
            findNavController().navigate(
                R.id.action_mainFragment_to_videoGamesFragment,
                bundleOf(),
                navOptionIsModal()
            )
        }
    }
}