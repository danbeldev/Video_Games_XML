package com.example.feature_store_info.screens.storeInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_model.api.store.StoreInfo
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.core_network_domain.pagingSource.VideoGamesPagingSource
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import com.example.core_network_domain.useCase.store.GetStoreByIdUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class StoreInfoViewModel(
    private val getStoreByIdUseCase: GetStoreByIdUseCase,
    private val getVideoGamesUseCase: GetGamesUseCase
):ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getVideoGames(
        storeId:Int
    ): Flow<PagingData<VideoGameItem>> {
        return Pager(PagingConfig(pageSize = 20)){
            VideoGamesPagingSource(
                getGamesUseCase = getVideoGamesUseCase,
                stores = storeId.toString()
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getStoreById(
        id:Int,
        onSuccess:(StoreInfo) -> Unit,
        onError:(Throwable) -> Unit
    ) {
        compositeDisposable.add(
            getStoreByIdUseCase.invoke(id = id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onSuccess(it) },{ onError(it) })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    class Factory @Inject constructor(
        private val getStoreByIdUseCase: GetStoreByIdUseCase,
        private val getVideoGamesUseCase: GetGamesUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StoreInfoViewModel(
                getStoreByIdUseCase = getStoreByIdUseCase,
                getVideoGamesUseCase = getVideoGamesUseCase
            ) as T
        }
    }
}