package com.example.core_ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.example.core_ui.R
import com.example.core_ui.databinding.CardInfoViewBinding

class CardInfoView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr:Int,
    defStyleRes:Int
) : ConstraintLayout(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
) {
    private val binding:CardInfoViewBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):this(
        context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs:AttributeSet?):this(context, attrs, 0)
    constructor(context: Context):this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.card_info_view, this, true)

        binding = CardInfoViewBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeAttributes(
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ){
        if (attrs == null) return

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.CardInfoView, defStyleAttr, defStyleRes
        )

        val resIdImageBackground = typedArray.getResourceId(
            R.styleable.CardInfoView_src_image_background,
            R.drawable.video_game_background_image
        )

        val title = typedArray.getString(R.styleable.CardInfoView_title) ?: ""

        val gamesCount = typedArray.getString(R.styleable.CardInfoView_games_count) ?: ""

        val videoGameOneText = typedArray.getString(R.styleable.CardInfoView_video_game_one_text) ?: ""
        val videoGameOneCount = typedArray.getString(R.styleable.CardInfoView_video_game_one_count) ?: ""

        val videoGameTwoText = typedArray.getString(R.styleable.CardInfoView_video_game_two_text) ?: ""
        val videoGameTwoCount = typedArray.getString(R.styleable.CardInfoView_video_game_two_count) ?: ""

        val videoGameThreeText = typedArray.getString(R.styleable.CardInfoView_video_game_three_text) ?: ""
        val videoGameThreeCount = typedArray.getString(R.styleable.CardInfoView_video_game_three_count) ?: ""

        setImageBackground(resIdImageBackground)

        title(title)

        gamesCount(gamesCount)

        videoGameOneText(videoGameOneText)
        videoGameOneCount(videoGameOneCount)

        videoGameTwoText(videoGameTwoText)
        videoGameTwoCount(videoGameTwoCount)

        videoGameThreeText(videoGameThreeText)
        videoGameThreeCount(videoGameThreeCount)

        typedArray.recycle()
    }

    fun setImageBackground(
        data: Any?,
        imageLoader: ImageLoader = context.imageLoader,
        builder: ImageRequest.Builder.() -> Unit = {}
    ){ binding.backgroundImage.load(data, imageLoader, builder) }

    fun setImageBackground(resId:Int){ binding.backgroundImage.setImageResource(resId) }

    fun title(text: String) { binding.title.text = text }

    fun gamesCount(text:String) { binding.popularItemsCount.text = text }

    fun videoGameOneText(text: String) { binding.videoGameOneText.text = text }

    fun videoGameOneCount(text:String) { binding.videoGameOneCount.text = text }

    fun videoGameTwoText(text: String) { binding.videoGameTwoText.text = text }

    fun videoGameTwoCount(text: String) { binding.videoGameTwoCount.text = text }

    fun videoGameThreeText(text: String) { binding.videoGameThreeText.text = text }

    fun videoGameThreeCount(text: String) { binding.videoGameThreeCount.text = text }

    fun onVideoGameOneClick(onClick:(View) -> Unit) {
        binding.videoGameOneText.setOnClickListener { onClick(it) } }

    fun onVideoGameTwoClick(onClick:(View) -> Unit) {
        binding.videoGameTwoText.setOnClickListener { onClick(it) } }

    fun onVideoGameThreeClick(onClick:(View) -> Unit) {
        binding.videoGameThreeText.setOnClickListener { onClick(it) } }
}