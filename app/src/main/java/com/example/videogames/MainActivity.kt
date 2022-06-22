package com.example.videogames

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.core_common.naigation.NavCommand
import com.example.core_common.naigation.NavCommands
import com.example.core_common.naigation.NavigationProvider
import com.example.core_common.R

class MainActivity : AppCompatActivity(), NavigationProvider {

    private val navController:NavController
        get() = findNavController(com.example.videogames.R.id.containerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.videogames.R.layout.activity_main)
    }

    override fun launch(navCommand: NavCommand) {
        when(val target = navCommand.target){
            is NavCommands.Browser -> openBrowser(target.url)
            is NavCommands.DeepLink -> openDeepLink(
                target.url,
                target.isModal,
                target.isSingleTop
            )
        }
    }

    private fun openDeepLink(url:Uri, isModal:Boolean, isSingleTop: Boolean){
        val navOption = if (isModal){
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_up_enter)
                .setExitAnim(R.anim.slide_out_up_enter)
                .setPopEnterAnim(R.anim.slide_in_up_exit)
                .setPopExitAnim(R.anim.slide_out_up_exit)
                .setLaunchSingleTop(isSingleTop)
                .setPopUpTo(if (isSingleTop) com.example.videogames.R.id.nav_graph_application_xml else -1
                    , inclusive = isSingleTop)
                .build()
        }else{
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_left)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .setLaunchSingleTop(isSingleTop)
                .setPopUpTo(if (isSingleTop) com.example.videogames.R.id.nav_graph_application_xml else
                    -1, inclusive = isSingleTop)
                .build()
        }

        navController.navigate(url, navOption)
    }

    private fun openBrowser(url:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.setPackage("com.android.chrome")
        try {
            this.startActivity(browserIntent)
        }catch (e:ActivityNotFoundException){
            browserIntent.setPackage(null)
            this.startActivity(browserIntent)
        }
    }
}