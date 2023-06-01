package com.madproject.feature_main.screens.likesScreen
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madproject.core_common.extension.launchWhenStartedPagingData
import com.madproject.core_common.naigation.NavCommand
import com.madproject.core_common.naigation.NavCommands
import com.madproject.core_common.naigation.Screen
import com.madproject.core_common.naigation.navigation
import com.madproject.core_ui.swipe.SwipeToDeleteCallback
import com.madproject.feature_main.R
import com.madproject.feature_main.databinding.FragmentLikesBinding
import com.madproject.feature_main.di.MainComponentViewModel
import com.madproject.feature_main.screens.likesScreen.adapter.FavoriteVideoGameAdapter
import com.madproject.feature_main.screens.likesScreen.viewModel.LikesViewModel
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LikesFragment:Fragment(R.layout.fragment_likes), SearchView.OnQueryTextListener {

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

        binding.topToolbar.menu.forEach { menuItem ->
            when(menuItem.itemId){
                R.id.search -> {
                    val search = menuItem.actionView as SearchView
                    search.isSubmitButtonEnabled = true
                    search.setOnQueryTextListener(this)
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            favoriteVideoGameAdapter.onPagesUpdatedFlow.collect {
                if (favoriteVideoGameAdapter.itemCount <= 0){
                    binding.noItemsAnimation.visibility = View.VISIBLE
                    binding.favoriteVideoGames.visibility = View.GONE
                }else{
                    binding.noItemsAnimation.visibility = View.GONE
                    binding.favoriteVideoGames.visibility = View.VISIBLE
                }
            }
        }

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

    override fun onQueryTextSubmit(search: String?): Boolean {
        launchWhenStartedPagingData(
            adapter = favoriteVideoGameAdapter
        ){ viewModel.getFavoriteVideoGames(search = search ?: "") }

        return true
    }

    override fun onQueryTextChange(search: String?): Boolean {
        launchWhenStartedPagingData(
            adapter = favoriteVideoGameAdapter
        ){ viewModel.getFavoriteVideoGames(search = search ?: "") }

        return true
    }
}