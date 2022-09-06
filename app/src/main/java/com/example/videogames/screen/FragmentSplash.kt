package com.example.videogames.screen

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.core_common.naigation.NavCommand
import com.example.core_common.naigation.NavCommands
import com.example.core_common.naigation.Screen
import com.example.core_common.naigation.navigation
import com.example.videogames.R

class FragmentSplash:Fragment(R.layout.fragment_splash){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigation(
            NavCommand(
                NavCommands.DeepLink(
                    url = Uri.parse(Screen.Main.route),
                    isModal = true,
                    isSingleTop = true
                )
            )
        )
    }
}