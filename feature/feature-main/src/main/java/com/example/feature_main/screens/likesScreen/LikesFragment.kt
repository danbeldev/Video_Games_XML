package com.example.feature_main.screens.likesScreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.example.feature_main.R
import com.example.feature_main.databinding.FragmentLikesBinding
import com.example.feature_main.di.MainComponentViewModel
import com.example.feature_main.screens.likesScreen.viewModel.LikesViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LikesFragment:Fragment(R.layout.fragment_likes) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<LikesViewModel.Factory>

    private val viewModel by viewModels<LikesViewModel> { viewModelFactory.get() }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainComponentViewModel>()
            .mainDetailsComponent.injectLikesFragment(this)

        super.onAttach(context)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLikesBinding.bind(view)

        lifecycleScope.launchWhenCreated {
            lifecycle.whenStateAtLeast(Lifecycle.State.CREATED){
                viewModel.getFavoriteVideoGames.onEach {
                    binding.textText.text = it.toString()
                }.collect()
            }
        }

    }
}