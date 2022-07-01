package com.example.feature_creator_info.screens.creatorInfo

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.extension.parseHtml
import com.example.core_common.naigation.NavCommand
import com.example.core_common.naigation.NavCommands
import com.example.core_common.naigation.Screen
import com.example.core_common.naigation.navigation
import com.example.core_model.api.creator.CreatorInfo
import com.example.core_network_domain.response.Result
import com.example.feature_creator_info.R
import com.example.feature_creator_info.databinding.FragmentCreatorInfoBinding
import com.example.feature_creator_info.di.CreatorComponentViewModel
import com.example.feature_creator_info.screens.creatorInfo.adapter.CreatorVideoGamesAdapter
import com.example.feature_creator_info.screens.creatorInfo.viewModel.CreatorInfoViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreatorInfoFragment : Fragment(R.layout.fragment_creator_info) {

    companion object {
        const val CREATOR_ID_KEY = "creator_id"
    }

    @Inject
    internal lateinit var viewModelFactory:Lazy<CreatorInfoViewModel.Factory>

    private val viewModel by viewModels<CreatorInfoViewModel> {
        viewModelFactory.get()
    }

    private val creatorVideoGamesAdapter by lazy(LazyThreadSafetyMode.NONE){
        CreatorVideoGamesAdapter(
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
        ViewModelProvider(this).get<CreatorComponentViewModel>()
            .creatorDetailComponent
            .injectCreatorInfo(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentCreatorInfoBinding.bind(view)

        val creatorId = arguments?.getInt(CREATOR_ID_KEY) ?: 0

        val creatorVideoGamesLayoutManager = LinearLayoutManager(this.context)
        creatorVideoGamesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        viewModel.getCreator(id = creatorId)
        viewModel.responseCreator.onEach {
            when(val result = it){
                is Result.Error -> creatorError(binding, result.message ?: "Error")
                is Result.Loading -> creatorLoading(binding)
                is Result.Success -> creatorSuccess(binding, result.data!!)
            }
        }.launchWhenStarted(lifecycle, lifecycleScope)

        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.getGames(creatorId).collectLatest(creatorVideoGamesAdapter::submitData)
            }
        }

        binding.videoGames.adapter = creatorVideoGamesAdapter
        binding.videoGames.layoutManager = creatorVideoGamesLayoutManager
    }

    private fun creatorError(
        binding:FragmentCreatorInfoBinding,
        message:String
    ){
        binding.messageLayout.visibility = View.VISIBLE
        binding.creatorInfo.visibility = View.GONE

        binding.message.text = message
    }

    @SuppressLint("SetTextI18n")
    private fun creatorLoading(
        binding:FragmentCreatorInfoBinding
    ){
        binding.messageLayout.visibility = View.VISIBLE
        binding.creatorInfo.visibility = View.GONE

        binding.message.text = "Loading..."
    }

    private fun creatorSuccess(
        binding:FragmentCreatorInfoBinding,
        data:CreatorInfo
    ){
        binding.messageLayout.visibility = View.GONE
        binding.creatorInfo.visibility = View.VISIBLE

        binding.creatorName.text = data.name
        binding.creatorDescription.text = data.description.parseHtml()
        binding.creatorPhoto.load(data.image)

        var creatorPositions = ""

        data.positions.forEachIndexed { index, item ->
            creatorPositions = if (index == 0)
                if (data.positions.lastIndex == 0)
                    "${item.name}."
                else
                    item.name
            else if (data.positions.lastIndex == index)
                "$creatorPositions, ${item.name}."
            else
                "$creatorPositions, ${item.name}"
        }

        binding.creatorPositions.text = creatorPositions
    }
}