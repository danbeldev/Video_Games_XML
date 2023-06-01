package com.madproject.videogames.screen.compose.withdrawalRequestsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.madproject.videogames.data.withdrawalRequest.WithdrawalRequestDataStore
import com.madproject.videogames.data.withdrawalRequest.model.WithdrawalRequest

class WithdrawalRequestsViewModel(
    private val withdrawalRequestDataStore: WithdrawalRequestDataStore = WithdrawalRequestDataStore()
): ViewModel() {

    fun getWithdrawalRequests(onSuccess: (List<WithdrawalRequest>) -> Unit){
        withdrawalRequestDataStore.getAll(onSuccess) { message ->
            Log.e("getWithdrawalRequests",message)
        }
    }

    fun deleteWithdrawalRequest(id:String, onSuccess: () -> Unit, onError:(message: String) -> Unit){
        withdrawalRequestDataStore.deleteById(id, onSuccess, onError)
    }
}