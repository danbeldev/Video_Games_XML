package com.madproject.videogames.screen.compose.adminScreen.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.madproject.videogames.screen.theme.primaryBackground
import com.madproject.videogames.screen.theme.primaryText
import com.madproject.videogames.screen.theme.secondaryBackground
import com.madproject.videogames.screen.theme.tintColor

@Composable
fun AddInfoAlertDialog(
    info: String,
    onDismissRequest: () -> Unit,
    onUpdateInfo: (String) -> Unit
) {
    var infoMutable by remember { mutableStateOf("") }
    val focusRequester = remember(::FocusRequester)

    LaunchedEffect(key1 = Unit, block = {
        infoMutable = info
        focusRequester.requestFocus()
    })

    AlertDialog(
        backgroundColor = primaryBackground,
        shape = AbsoluteRoundedCornerShape(20.dp),
        onDismissRequest = onDismissRequest,
        title = {
            OutlinedTextField(
                value = infoMutable,
                onValueChange = { infoMutable = it },
                modifier = Modifier
                    .padding(5.dp)
                    .height(200.dp)
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = primaryText,
                    disabledTextColor = tintColor,
                    backgroundColor = secondaryBackground,
                    cursorColor = tintColor,
                    focusedBorderColor = tintColor,
                    unfocusedBorderColor = secondaryBackground,
                    disabledBorderColor = secondaryBackground
                ),
            )
        },
        buttons = {
            Button(
                modifier = Modifier.padding(15.dp).fillMaxWidth(),
                onClick = { onUpdateInfo(infoMutable) },
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                )
            ) {
                Text(text = "Сохранить", color = primaryText)
            }
        }
    )
}