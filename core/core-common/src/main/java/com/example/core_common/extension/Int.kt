package com.example.core_common.extension

import android.content.Context
import android.util.TypedValue

fun Int?.toDp(context: Context):Int? {
    if (this == null)
        return this
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}