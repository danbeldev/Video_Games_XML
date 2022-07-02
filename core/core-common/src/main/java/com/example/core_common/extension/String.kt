package com.example.core_common.extension

import android.os.Build
import android.text.Html

fun String.parseHtml():String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(
            this,
            Html.FROM_HTML_MODE_LEGACY
        ).toString()
    }else{
        Html.fromHtml(this).toString()
    }
}

fun String?.replaceRange(int: Int):String{
    this?.let {
        if (this.length < int)
            return this
        return this.replaceRange(
            int until this.length,
            "..."
        )
    }
    return ""
}