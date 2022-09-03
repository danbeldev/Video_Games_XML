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
import com.example.core_common.extension.launchWhenStartedPagingData
import com.example.core_common.extension.parseHtml
import com.example.core_common.naigation.NavCommand
import com.example.core_common.naigation.NavCommands
import com.example.core_common.naigation.Screen
import com.example.core_common.naigation.navigation
import com.example.feature_creator_info.R
import com.example.feature_creator_info.databinding.FragmentCreatorInfoBinding
import com.example.feature_creator_info.di.CreatorComponentViewModel
import com.example.feature_creator_info.screens.creatorInfo.action.CreatorInfoAction
import com.example.feature_creator_info.screens.creatorInfo.adapter.CreatorVideoGamesAdapter
import com.example.feature_creator_info.screens.creatorInfo.model.CreatorInfoStateSucces
import com.example.feature_creator_info.screens.creatorInfo.state.CreatorInfoState
import com.example.feature_creator_info.screens.creatorInfo.viewModel.CreatorInfoViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreatorInfoFragment : Fragment(R.layout.fragment_creator_info),CreatorInfoView {

    companion object {
        const val CREATOR_ID_KEY = "creator_id"
    }

    @Inject
    internal lateinit var viewModelFactory:Lazy<CreatorInfoViewModel.Factory>

    private var binding:FragmentCreatorInfoBinding? = null

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
        binding = FragmentCreatorInfoBinding.bind(view)

        val creatorId = arguments?.getInt(CREATOR_ID_KEY)!!

        viewModel.renderAction(
            action = CreatorInfoAction.GetCreatorInfo(
            creatorId = creatorId
            )
        )
        launchWhenStarted {
            viewModel.creatorInfoState.onEach { state ->
                renderState(state)
            }
        }
    }

    override fun renderState(state: CreatorInfoState) {
        when(state){
            is CreatorInfoState.Error -> {
                error(message = state.error.message)
            }
            CreatorInfoState.Loading -> {
                loading()
            }
            is CreatorInfoState.Succes -> {
                success(data = state.data)
            }
        }
    }

    private fun error(
        message:String
    ){
        binding?.let { binding ->
            binding.messageLayout.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.creatorInfo.visibility = View.GONE

            binding.message.text = message
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loading(){
        binding?.let { binding ->
            binding.messageLayout.visibility = View.VISIBLE
            binding.creatorInfo.visibility = View.GONE

            binding.message.text = "Loading..."
        }
    }

    private fun success(
        data: CreatorInfoStateSucces
    ){
        binding?.let { binding ->

            binding.messageLayout.visibility = View.GONE
            binding.creatorInfo.visibility = View.VISIBLE

            data.creatorInfo?.let { creatorInfo ->
                binding.creatorName.text = creatorInfo.name
                binding.creatorDescription.text = creatorInfo.description.parseHtml()
                binding.creatorPhoto.load(creatorInfo.image)

                var creatorPositions = ""

                creatorInfo.positions.forEachIndexed { index, item ->
                    creatorPositions = if (index == 0)
                        if (creatorInfo.positions.lastIndex == 0)
                            "${item.name}."
                        else
                            item.name
                    else if (creatorInfo.positions.lastIndex == index)
                        "$creatorPositions, ${item.name}."
                    else
                        "$creatorPositions, ${item.name}"
                }

                binding.creatorPositions.text = creatorPositions
            }

            val creatorVideoGamesLayoutManager = LinearLayoutManager(this.context)
            creatorVideoGamesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

            data.videoGamesCreatorInfo?.let { videoGames ->
                launchWhenStartedPagingData(
                    adapter = creatorVideoGamesAdapter
                ){ videoGames }
            }

            binding.videoGames.adapter = creatorVideoGamesAdapter
            binding.videoGames.layoutManager = creatorVideoGamesLayoutManager
        }
    }
}