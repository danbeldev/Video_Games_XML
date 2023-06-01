package com.madproject.videogames.screen.compose.mainScreen.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madproject.videogames.screen.theme.primaryBackground
import com.madproject.videogames.screen.theme.primaryText
import com.madproject.videogames.screen.theme.tintColor

@Composable
fun InfoAlertDialog(
    info: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .border(3.dp, tintColor, AbsoluteRoundedCornerShape(20.dp)),
        backgroundColor = primaryBackground,
        shape = AbsoluteRoundedCornerShape(20.dp),
        onDismissRequest = onDismissRequest,
        buttons = {
            Text(
                text = info,
                color = primaryText,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    )
}