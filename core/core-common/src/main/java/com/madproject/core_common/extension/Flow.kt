package com.madproject.core_common.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchWhenStarted(lifecycle:Lifecycle, lifecycleScope:LifecycleCoroutineScope){
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            this@launchWhenStarted.collect()
        }
    }
}

fun <T:Any, VH : RecyclerView.ViewHolder>Flow<PagingData<T>>.launchWhenStartedPagingData(
    adapter: PagingDataAdapter<T,VH>,
    lifecycle:Lifecycle,
    lifecycleScope:LifecycleCoroutineScope
){
    lifecycleScope.launchWhenStarted {
        lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
            this@launchWhenStartedPagingData.collectLatest(adapter::submitData)
        }
    }
}

fun <T>Fragment.launchWhenStarted(flow: suspend () -> Flow<T>){
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.invoke().collect()
        }
    }
}

fun <T:Any, VH : RecyclerView.ViewHolder>Fragment.launchWhenStartedPagingData(
    adapter: PagingDataAdapter<T,VH>,
    flow: suspend () -> Flow<PagingData<T>>
){
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.invoke().collectLatest(adapter::submitData)
        }
    }
}