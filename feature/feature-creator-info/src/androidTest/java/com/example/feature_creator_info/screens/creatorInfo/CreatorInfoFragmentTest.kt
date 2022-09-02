package com.example.feature_creator_info.screens.creatorInfo

import androidx.activity.ComponentActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

internal class CreatorInfoFragmentTest:TestCase(Kaspresso.Builder.simple()) {

    private val screen = CreatorInfoScreenTest

    @get:Rule
    val activityTestRule = ActivityScenarioRule(ComponentActivity::class.java)

    @Test
    fun test_creator_name_text_view() = run {
        screen {
            creatorNameTextView {
                isVisible()
            }
        }
    }
}