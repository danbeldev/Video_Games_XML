package com.example.feature_main.screens.likesScreen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.naigation.NavCommand
import com.example.core_common.naigation.NavCommands
import com.example.core_common.naigation.Screen
import com.example.core_common.naigation.navigation
import com.example.core_ui.swipe.SwipeToDeleteCallback
import com.example.feature_main.R
import com.example.feature_main.databinding.FragmentLikesBinding
import com.example.feature_main.di.MainComponentViewModel
import com.example.feature_main.screens.likesScreen.adapter.FavoriteVideoGameAdapter
import com.example.feature_main.screens.likesScreen.viewModel.LikesViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LikesFragment:Fragment(R.layout.fragment_likes) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<LikesViewModel.Factory>

    private val viewModel by viewModels<LikesViewModel> { viewModelFactory.get() }

    private var favoriteVideoGameAdapter:FavoriteVideoGameAdapter? = null

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainComponentViewModel>()
            .mainDetailsComponent.injectLikesFragment(this)

        super.onAttach(context)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLikesBinding.bind(view)

        binding.favoriteVideoGames.layoutManager = LinearLayoutManager(this.context)

        viewModel.getFavoriteVideoGames.onEach {
            favoriteVideoGameAdapter = FavoriteVideoGameAdapter(it){ videGame ->
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.VideoGameInfo.arguments(
                                videoGameId = videGame.id
                            )),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            }
            binding.favoriteVideoGames.adapter = favoriteVideoGameAdapter
        }.launchWhenStarted(lifecycle, lifecycleScope)

        swipeToDelete(binding = binding)
    }

    private fun swipeToDelete(
        binding: FragmentLikesBinding
    ){
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                viewModel.getFavoriteVideoGames.onEach { videoGames ->
                    val item = videoGames.getOrNull(position) ?: return@onEach
                    viewModel.deleteFavoriteVideoGames(item.id)
                }.launchWhenStarted(lifecycle,lifecycleScope)

                favoriteVideoGameAdapter?.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.favoriteVideoGames)
    }
}