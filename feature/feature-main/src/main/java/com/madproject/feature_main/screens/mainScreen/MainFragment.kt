package com.madproject.feature_main.screens.mainScreen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.madproject.core_common.extension.launchWhenStarted
import com.madproject.feature_main.R
import com.madproject.feature_main.di.MainComponentViewModel
import com.madproject.feature_main.screens.mainScreen.viewModel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.Lazy
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    internal lateinit var viewModelFactory:Lazy<MainViewModel.Factory>

    private val viewModel by viewModels<MainViewModel> { viewModelFactory.get() }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainComponentViewModel>()
            .mainDetailsComponent.injectMainFragment(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)

        val navController = (childFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment)
            .navController

        val badge = bottomNavigationView.getOrCreateBadge(R.id.likesFragment)

        viewModel.responseFavoriteVideoGames.onEach {
            if (it > 0){
                badge.number = it
                badge.isVisible = true
            }else {
                badge.isVisible = false
                badge.clearNumber()
            }
        }.launchWhenStarted(lifecycle,lifecycleScope)

        bottomNavigationView.setupWithNavController(navController)
    }
}