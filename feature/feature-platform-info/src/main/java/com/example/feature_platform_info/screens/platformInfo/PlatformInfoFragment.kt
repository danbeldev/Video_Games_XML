package com.example.feature_platform_info.screens.platformInfo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.extension.parseHtml
import com.example.core_model.api.platform.PlatformInfo
import com.example.core_network_domain.response.Result
import com.example.feature_platform_info.R
import com.example.feature_platform_info.databinding.FragmentPlatformInfoBinding
import com.example.feature_platform_info.di.PlatformComponentViewModel
import com.example.feature_platform_info.screens.platformInfo.viewModel.PlatformInfoViewModel
import dagger.Lazy
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
    }

    private fun platformSuccess(
        binding:FragmentPlatformInfoBinding,
        data:PlatformInfo
    ){
        binding.platformInfo.visibility = View.VISIBLE
        binding.errorLoading.visibility = View.GONE

        binding.platformName.text = data.name

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