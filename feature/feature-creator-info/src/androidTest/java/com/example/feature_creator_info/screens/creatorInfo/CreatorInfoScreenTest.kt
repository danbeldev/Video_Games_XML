package com.example.feature_creator_info.screens.creatorInfo

import com.kaspersky.kaspresso.screens.KScreen
import com.example.feature_creator_info.R
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.scroll.KScrollView
import io.github.kakaocup.kakao.text.KTextView

internal object CreatorInfoScreenTest:KScreen<CreatorInfoScreenTest>() {

    override val layoutId: Int = R.layout.fragment_creator_info

    override val viewClass: Class<*> = CreatorInfoFragment::class.java

    val scrollView = KScrollView {
        withId(R.id.scroll_view)
    }

    val creatorNameTextView = KTextView {
        withId(R.id.creator_name)
    }

    val creatorPositionsTextView = KTextView {
        withId(R.id.creator_positions)
    }

    val creatorPhotoImageView = KImageView {
        withId(R.id.creator_photo)
    }

    val creatorDescriptionTextView = KTextView {
        withId(R.id.creator_description)
    }

    val videoGamesNameTextView = KTextView {
        withId(R.id.video_games_name)
    }

    val videoGamesRecyclerView = KRecyclerView({
        withId(R.id.video_games)
    },{

    })
}