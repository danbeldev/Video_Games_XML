package com.madproject.feature_video_game_info.screens.qrCodeVideoGameScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.madproject.feature_video_game_info.R
import com.madproject.feature_video_game_info.databinding.FragmentQrCodeVideoGameBinding

class QrCodeVideoGameFragment:Fragment(R.layout.fragment_qr_code_video_game) {

    companion object {
        const val VIDEO_GAME_ID_KEY = "id"
    }

    lateinit var binding: FragmentQrCodeVideoGameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentQrCodeVideoGameBinding.bind(view)

        val videoGame = arguments!!.getInt(VIDEO_GAME_ID_KEY)
//
//        val viewPagerAdapter = PagerAdapter()

        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}