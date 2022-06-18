package com.example.feature_main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feature_main.adapter.VideoGamesAdapter
import com.example.feature_main.databinding.FragmentMainBinding
import com.example.feature_main.di.MainComponentViewModel
import com.example.feature_main.viewModel.MainViewModel
import dagger.Lazy
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    internal lateinit var mainViewModelFactory:Lazy<MainViewModel.Factor>

    private val viewModel by viewModels<MainViewModel>{
        mainViewModelFactory.get()
    }

    private var videoGamesAdapter:VideoGamesAdapter? = null

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainComponentViewModel>()
            .newDetailsComponent.inject(this)

        viewModel.getGames()

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMainBinding.bind(view)

        lifecycleScope.launch {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.responseVideoGame.collect{ videoGame ->
                    videoGame?.let {
                        videoGamesAdapter = VideoGamesAdapter(videoGames = videoGame.results)
                        binding.recyclerView.adapter = videoGamesAdapter
                    }
                }
            }
        }

        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.layoutManager = layoutManager
    }
}