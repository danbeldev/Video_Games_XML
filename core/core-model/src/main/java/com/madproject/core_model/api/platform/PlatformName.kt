package com.madproject.core_model.api.platform

import com.google.gson.annotations.SerializedName

enum class PlatformName {
    PC,
    PlayStation,
    Xbox,
    Linux,
    @SerializedName("Apple Macintosh")
    Mac,
    @SerializedName("iOS")
    Ios,
    Android,
    Nintendo
}