package com.madproject.core_common.naigation

import android.os.Bundle
import androidx.navigation.NavOptions

data class NavCommand(
    val target: NavCommands,
    var args: Bundle? = null,
    val navOptions: NavOptions? = null
)
