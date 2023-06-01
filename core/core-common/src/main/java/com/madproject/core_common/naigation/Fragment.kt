package com.madproject.core_common.naigation

import androidx.fragment.app.Fragment

fun Fragment.navigation(navCommand: NavCommand){
    (requireActivity() as? NavigationProvider)?.launch(navCommand)
}