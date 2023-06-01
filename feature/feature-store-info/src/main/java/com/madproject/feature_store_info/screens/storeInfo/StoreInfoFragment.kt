package com.madproject.feature_store_info.screens.storeInfo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.madproject.core_common.extension.parseHtml
import com.madproject.core_common.naigation.NavCommand
import com.madproject.core_common.naigation.NavCommands
import com.madproject.core_common.naigation.Screen
import com.madproject.core_common.naigation.navigation
import com.madproject.feature_store_info.R
import com.madproject.feature_store_info.databinding.FragmentStoreInfoBinding
import com.madproject.feature_store_info.di.StoreCompanyViewModel
import com.madproject.feature_store_info.screens.storeInfo.adapter.StoreVideoGameHorizontalAdapter
import com.madproject.feature_store_info.screens.storeInfo.viewModel.StoreInfoViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class StoreInfoFragment:Fragment(R.layout.fragment_store_info) {

    companion object {
        const val STORE_ID_KEY = "storeId"
    }

    @Inject
    internal lateinit var storeInfoViewModelFactory:Lazy<StoreInfoViewModel.Factory>

    private val viewModel by viewModels<StoreInfoViewModel> {
        storeInfoViewModelFactory.get()
    }

    private val storeVideoGameHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE) {
        StoreVideoGameHorizontalAdapter(
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

    override fun onAttach(context: Context) {

        ViewModelProvider(this).get<StoreCompanyViewModel>()
            .storeDetailComponent.injectStoreInfoFragment(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentStoreInfoBinding.bind(view)

        val storeId = arguments?.getInt(STORE_ID_KEY) ?: 0

        val storeVideoGameHorizontalLayoutManager = LinearLayoutManager(this.context)
        storeVideoGameHorizontalLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        viewModel.getStoreById(
            storeId, { store ->
                binding.nameStore.text = store.name
                binding.descriptionStore.text = store.description.parseHtml()
                binding.videoGameCount.text = store.gamesCount.toString()
            },{
                Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
            }
        )

        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.getVideoGames(storeId).collectLatest(storeVideoGameHorizontalAdapter::submitData)
            }
        }

        binding.videoGameRecyclerView.layoutManager = storeVideoGameHorizontalLayoutManager
        binding.videoGameRecyclerView.adapter = storeVideoGameHorizontalAdapter
    }
}