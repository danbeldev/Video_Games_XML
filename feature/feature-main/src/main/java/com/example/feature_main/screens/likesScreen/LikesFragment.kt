package com.example.feature_main.screens.likesScreen
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
import com.example.core_common.extension.launchWhenStartedPagingData
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
import javax.inject.Inject

class LikesFragment:Fragment(R.layout.fragment_likes) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<LikesViewModel.Factory>

    private val viewModel by viewModels<LikesViewModel> { viewModelFactory.get() }

    private lateinit var binding:FragmentLikesBinding

    private val favoriteVideoGameAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteVideoGameAdapter { videGame ->
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
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainComponentViewModel>()
            .mainDetailsComponent.injectLikesFragment(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLikesBinding.bind(view)

        binding.favoriteVideoGames.layoutManager = LinearLayoutManager(this.context)

        launchWhenStartedPagingData(
            adapter = favoriteVideoGameAdapter
        ){ viewModel.getFavoriteVideoGames() }

        binding.favoriteVideoGames.adapter = favoriteVideoGameAdapter

        swipeToDelete()
    }

    private fun swipeToDelete(){
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                val videoGame = favoriteVideoGameAdapter.snapshot()[position] ?: return

                viewModel.deleteFavoriteVideoGames(id = videoGame.id)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.favoriteVideoGames)

    }
}