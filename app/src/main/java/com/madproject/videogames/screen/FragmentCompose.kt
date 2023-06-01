package com.madproject.videogames.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.madproject.videogames.screen.compose.adminScreen.AdminScreen
import com.madproject.videogames.screen.compose.authScreen.AuthScreen
import com.madproject.videogames.screen.compose.settingsScreen.SettingsScreen
import com.madproject.videogames.screen.compose.withdrawalRequestsScreen.WithdrawalRequestsScreen
import com.madproject.videogames.screen.compose.mainScreen.MainScreen

class FragmentCompose:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = rememberNavController()
                val auth = remember { FirebaseAuth.getInstance() }

                NavHost(
                    navController = navController,
                    startDestination = if(auth.currentUser == null)
                        "Auth"
                    else
                        "Ads"
                ){
                    composable("Auth"){
                        AuthScreen(navController = navController)
                    }

                    composable("Ads"){
                        MainScreen(navController = navController)
                    }

                    composable("Admin"){
                        AdminScreen(navController = navController)
                    }

                    composable("Settings"){
                        SettingsScreen(navController = navController)
                    }

                    composable(
                        "WithdrawalRequestsScreen/{status}",
                        arguments = listOf(
                            navArgument("status"){
                                type = NavType.StringType
                            }
                        )
                    ) {
                        WithdrawalRequestsScreen(
                            navController = navController,
                            withdrawalRequestStatus = enumValueOf(
                                it.arguments!!.getString("status").toString()
                            )
                        )
                    }
                }
            }
        }
    }
}