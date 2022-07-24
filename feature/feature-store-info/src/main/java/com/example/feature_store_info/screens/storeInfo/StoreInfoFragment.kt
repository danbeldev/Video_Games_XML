package com.example.feature_store_info.screens.storeInfo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.core_common.extension.parseHtml
import com.example.feature_store_info.R
import com.example.feature_store_info.databinding.FragmentStoreInfoBinding
import com.example.feature_store_info.di.StoreCompanyViewModel
import com.example.feature_store_info.screens.storeInfo.viewModel.StoreInfoViewModel
import dagger.Lazy
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

    override fun onAttach(context: Context) {

        ViewModelProvider(this).get<StoreCompanyViewModel>()
            .storeDetailComponent.injectStoreInfoFragment(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentStoreInfoBinding.bind(view)

        val storeId = arguments?.getInt(STORE_ID_KEY) ?: 0

        viewModel.getStoreById(
            storeId, { store ->
                binding.nameStore.text = store.name
                binding.descriptionStore.text = store.description.parseHtml()
                binding.videoGameCount.text = store.gamesCount.toString()
            },{
                Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
            }
        )
    }
}