package com.madproject.videogames.screen

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.madproject.core_common.naigation.NavCommand
import com.madproject.core_common.naigation.NavCommands
import com.madproject.core_common.naigation.Screen
import com.madproject.core_common.naigation.navigation
import com.madproject.videogames.R

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