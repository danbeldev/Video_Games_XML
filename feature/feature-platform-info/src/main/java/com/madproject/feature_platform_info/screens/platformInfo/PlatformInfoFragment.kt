package com.madproject.feature_platform_info.screens.platformInfo

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.madproject.core_common.extension.launchWhenStarted
import com.madproject.core_common.extension.parseHtml
import com.madproject.core_common.naigation.NavCommand
import com.madproject.core_common.naigation.NavCommands
import com.madproject.core_common.naigation.Screen
import com.madproject.core_common.naigation.navigation
import com.madproject.core_model.api.platform.PlatformInfo
import com.madproject.core_network_domain.response.Result
import com.madproject.core_ui.animation.navOptionIsModal
import com.madproject.feature_platform_info.R
import com.madproject.feature_platform_info.databinding.FragmentPlatformInfoBinding
import com.madproject.feature_platform_info.di.PlatformComponentViewModel
import com.madproject.feature_platform_info.screens.platformInfo.adapter.VideoGameHorizontalAdapter
import com.madproject.feature_platform_info.screens.platformInfo.viewModel.PlatformInfoViewModel
import com.madproject.feature_platform_info.screens.platformVideoGames.PlatformVideoGamesFragment
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PlatformInfoFragment : Fragment(R.layout.fragment_platform_info) {

    companion object {
        const val PLATFORM_ID_KEY = "platform_id"
    }

    @Inject
    internal lateinit var platformInfoViewModelFactory:Lazy<PlatformInfoViewModel.Factory>

    private val viewModel by viewModels<PlatformInfoViewModel> {
        platformInfoViewModelFactory.get()
    }

    private val videoGameHorizontalAdapter by lazy(LazyThreadSafetyMode.NONE){
        VideoGameHorizontalAdapter(
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
        ViewModelProvider(this).get<PlatformComponentViewModel>()
            .platformDetailComponent.injectPlatformInfo(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPlatformInfoBinding.bind(view)

        val platformId = arguments?.getInt(PLATFORM_ID_KEY) ?: 0

        viewModel.getPlatforms(platformId)
        viewModel.responsePlatforms.onEach { response ->
            when(response){
                is Result.Error -> platformError(binding, response.message ?: "")
                is Result.Loading -> platformLoading(binding)
                is Result.Success -> platformSuccess(binding, response.data!!)
            }
        }.launchWhenStarted(lifecycle, lifecycleScope)

        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.getGames(platformId).collectLatest(videoGameHorizontalAdapter::submitData)
            }
        }

        binding.videoGamesText.setOnClickListener {
            findNavController().navigate(
                R.id.action_platformInfoFragment_to_platformVideoGamesFragment,
                bundleOf(PlatformVideoGamesFragment.PLATFORM_ID_KEY to platformId),
                navOptionIsModal()
            )
        }
    }

    private fun platformSuccess(
        binding:FragmentPlatformInfoBinding,
        data:PlatformInfo
    ){
        val videoGamesLayoutManager = LinearLayoutManager(this.context)
        videoGamesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        binding.platformInfo.visibility = View.VISIBLE
        binding.errorLoading.visibility = View.GONE

        binding.platformName.text = data.name

        binding.videoGamesRecyclerView.layoutManager = videoGamesLayoutManager
        binding.videoGamesRecyclerView.adapter = videoGameHorizontalAdapter

        if (data.description != null){
            binding.platformDescription.text = data.description!!.parseHtml()
        }else {
            binding.platformDescription.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun platformLoading(
        binding:FragmentPlatformInfoBinding
    ){
        binding.platformInfo.visibility = View.GONE
        binding.errorLoading.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE
        binding.message.text = "Loading..."
    }

    private fun platformError(
        binding:FragmentPlatformInfoBinding,
        message:String
    ){
        binding.platformInfo.visibility = View.GONE
        binding.errorLoading.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.message.text = message
    }
}