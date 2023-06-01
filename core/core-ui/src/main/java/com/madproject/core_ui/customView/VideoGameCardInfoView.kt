package com.madproject.core_ui.customView

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.madproject.core_model.api.platform.PlatformName
import com.madproject.core_ui.R
import com.madproject.core_ui.databinding.VideoGameCardInfoViewBinding

class VideoGameCardInfoView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr:Int,
    defStyleRes:Int
):ConstraintLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):this(
        context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs:AttributeSet?):this(context, attrs, 0)
    constructor(context: Context):this(context, null)

    private val binding:VideoGameCardInfoViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.video_game_card_info_view, this, true)

        binding = VideoGameCardInfoViewBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeAttributes(
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        if (attrs == null) return

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.VideoGameCardInfoView, defStyleAttr, defStyleRes
        )

        val resIdVideoGameImage = typedArray.getResourceId(
            R.styleable.VideoGameCardInfoView_video_game_image,
            R.drawable.video_game_background_image
        )

        val videoGameTitle = typedArray.getString(R.styleable.VideoGameCardInfoView_video_game_title) ?: ""

        val videoGameMetacriticRating = typedArray.getString(R.styleable.VideoGameCardInfoView_video_game_metacritic_rating) ?: ""

        val videoGameGenres = typedArray.getString(R.styleable.VideoGameCardInfoView_video_game_genres) ?: ""
        val videoGameStores = typedArray.getString(R.styleable.VideoGameCardInfoView_video_game_stores) ?: ""

        val videoGamePlatformWindows = typedArray.getBoolean(R.styleable.VideoGameCardInfoView_video_game_platform_windows,false)
        val videoGamePlatformPs = typedArray.getBoolean(R.styleable.VideoGameCardInfoView_video_game_platform_ps,false)
        val videoGamePlatformXbox = typedArray.getBoolean(R.styleable.VideoGameCardInfoView_video_game_platform_xbox,false)
        val videoGamePlatformNintendo = typedArray.getBoolean(R.styleable.VideoGameCardInfoView_video_game_platform_nintendo,false)
        val videoGamePlatformAndroid = typedArray.getBoolean(R.styleable.VideoGameCardInfoView_video_game_platform_android,false)
        val videoGamePlatformIos = typedArray.getBoolean(R.styleable.VideoGameCardInfoView_video_game_platform_ios,false)
        val videoGamePlatformLinux = typedArray.getBoolean(R.styleable.VideoGameCardInfoView_video_game_platform_linux,false)

        setVideoGameImage(resIdVideoGameImage)

        setVideoGameTitle(videoGameTitle)
        setVideoGameMetacriticRating(videoGameMetacriticRating)

        setVideoGameGenre(videoGameGenres)
        setVideoGameStore(videoGameStores)

        setVideoGamePlatformWindows(videoGamePlatformWindows)
        setVideoGamePlatformPs(videoGamePlatformPs)
        setVideoGamePlatformXbox(videoGamePlatformXbox)
        setVideoGamePlatformNintendo(videoGamePlatformNintendo)
        setVideoGamePlatformAndroid(videoGamePlatformAndroid)
        setVideoGamePlatformIos(videoGamePlatformIos)
        setVideoGamePlatformLinux(videoGamePlatformLinux)
    }

    fun setVideoGameImage(
        data: Any?,
        imageLoader: ImageLoader = context.imageLoader,
        builder: ImageRequest.Builder.() -> Unit = {}
    ){ binding.videoGameImage.load(data, imageLoader, builder) }

    fun setVideoGameImage(resId:Int){ binding.videoGameImage.setImageResource(resId) }

    fun setVideoGameImage(bm: Bitmap){ binding.videoGameImage.setImageBitmap(bm)}

    fun setPlatforms(platforms:List<PlatformName?>){
        platforms.forEach { platform ->
            setPlatform(platform)
        }
    }

    fun setPlatform(platform:PlatformName?){

        if (platform == null) return

        when(platform){
            PlatformName.PC -> setVideoGamePlatformWindows(true)
            PlatformName.PlayStation -> setVideoGamePlatformPs(true)
            PlatformName.Xbox -> setVideoGamePlatformXbox(true)
            PlatformName.Linux -> setVideoGamePlatformLinux(true)
            PlatformName.Mac -> setVideoGamePlatformIos(true)
            PlatformName.Ios -> setVideoGamePlatformIos(true)
            PlatformName.Android -> setVideoGamePlatformAndroid(true)
            PlatformName.Nintendo -> setVideoGamePlatformNintendo(true)
        }
    }

    fun setVideoGamePlatformWindows(visible:Boolean){ binding.videoGamePlatformWindows.visibility = visible(visible) }

    fun setVideoGamePlatformPs(visible:Boolean){ binding.videoGamePlatformPs.visibility = visible(visible) }

    fun setVideoGamePlatformXbox(visible:Boolean){ binding.videoGamePlatformXbox.visibility = visible(visible) }

    fun setVideoGamePlatformNintendo(visible:Boolean){ binding.videoGamePlatformNintendo.visibility = visible(visible) }

    fun setVideoGamePlatformAndroid(visible:Boolean){ binding.videoGamePlatformAndroid.visibility = visible(visible) }

    fun setVideoGamePlatformIos(visible:Boolean){ binding.videoGamePlatformIos.visibility = visible(visible) }

    fun setVideoGamePlatformLinux(visible:Boolean){ binding.videoGamePlatformLinux.visibility = visible(visible) }

    fun setVideoGameMetacriticRating(rating:String?){ binding.videoGameMetacriticRating.text = rating }

    fun setVideoGameTitle(title:String?){ binding.videoGameTitle.text = title }

    fun setVideoGameGenres(genres:List<String>){
        var genre = ""

        genres.forEachIndexed { index, name ->
            if (index > 1) return@forEachIndexed

            genre = if (index == 0)
                name
            else
                "$genre, $name"
        }

        setVideoGameGenre(genre)
    }

    fun setVideoGameStores(stores:List<String>){
        var store = ""

        stores.forEachIndexed { index, name ->
            if (index > 0) return@forEachIndexed

            store = if (index == 0)
                name
            else
                "$store, $name"
        }

        setVideoGameStore(store)
    }

    fun setVideoGameGenre(genre:String?){ binding.videoGameGenres.text = genre }

    fun setVideoGameStore(stores:String?){ binding.videoGameStores.text = stores }

    private fun visible(visible:Boolean):Int = if(visible) View.VISIBLE else View.GONE
}