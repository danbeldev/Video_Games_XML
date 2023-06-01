package com.madproject.videogames.screen.compose.authScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yandex.mobile.ads.banner.AdSize
import com.madproject.videogames.screen.compose.mainScreen.MainButton
import com.madproject.videogames.screen.view.YandexAdsBanner
import com.madproject.videogames.data.utils.UtilsDataStore
import com.madproject.videogames.data.utils.model.Utils
import com.madproject.videogames.screen.theme.primaryText
import com.madproject.videogames.screen.theme.secondaryBackground
import com.madproject.videogames.screen.theme.tintColor

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    val _screenWidthDp = LocalConfiguration.current.screenWidthDp
    val _screenHeightDp = LocalConfiguration.current.screenHeightDp

    var screenWidthDp by remember { mutableStateOf(400) }
    var screenHeightDp by remember { mutableStateOf(1000) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    var utils by remember { mutableStateOf<Utils?>(null) }
    val utilsDataStore = remember(::UtilsDataStore)

    LaunchedEffect(key1 = Unit, block = {
        screenWidthDp = _screenWidthDp
        screenHeightDp = _screenHeightDp

        utilsDataStore.get(onSuccess = {
            utils = it
        })
    })

//    Image(
//        bitmap = Bitmap.createScaledBitmap(
//            BitmapFactory.decodeResource(context.resources, R.drawable.main_background),
//            screenWidthDp,
//            screenHeightDp,
//            false
//        ).asImageBitmap(),
//        contentDescription = null,
//        modifier = Modifier.size(
//            width = screenWidthDp.dp,
//            height = screenHeightDp.dp
//        )
//    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                listOf(
                    Color(0xFF2C5364),
                    Color(0xFF203A43),
                    Color(0xFF0F2027)
                )
            )
        ))

    LazyColumn(
        modifier = Modifier.size(
            width = screenWidthDp.dp,
            height = screenHeightDp.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            utils?.let {
                YandexAdsBanner(
                    size = AdSize.BANNER_240x400,
                    adUnitId = it.banner_yandex_ads_id
                )
            }
        }

        item {
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W900
            )

            OutlinedTextField(
                modifier = Modifier.padding(5.dp),
                value = email,
                onValueChange = { email = it },
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = primaryText,
                    disabledTextColor = tintColor,
                    backgroundColor = secondaryBackground,
                    cursorColor = tintColor,
                    focusedBorderColor = tintColor,
                    unfocusedBorderColor = secondaryBackground,
                    disabledBorderColor = secondaryBackground
                ),
                label = {
                    Text(text = "Электронная почта", color = primaryText)
                }
            )

            OutlinedTextField(
                modifier = Modifier.padding(5.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = primaryText,
                    disabledTextColor = tintColor,
                    backgroundColor = secondaryBackground,
                    cursorColor = tintColor,
                    focusedBorderColor = tintColor,
                    unfocusedBorderColor = secondaryBackground,
                    disabledBorderColor = secondaryBackground
                ),
                label = {
                    Text(text = "Пароль", color = primaryText)
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            MainButton(
                text = "Авторизоваться",
                onClick = {
                    try {
                        viewModel.signIn(email.trim(),password.trim(),{
                            navController.navigate("Ads")
                        },{
                            error = it
                        })
                    }catch(e: IllegalArgumentException){
                        error = "Заполните все поля"
                    }catch (e:Exception){
                        error = "Ошибка"
                    }
                }
            )

            MainButton(
                text = "Зарегистрироваться",
                onClick = {
                    try {
                        viewModel.registration(email.trim(),password.trim(),{
                            navController.navigate("Ads")
                        },{
                            error = it
                        })
                    }catch(e: IllegalArgumentException){
                        error = "Заполните все поля"
                    }catch (e:Exception){
                        error = "Ошибка"
                    }
                }
            )
        }
    }
}