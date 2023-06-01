package com.madproject.videogames.screen.compose.adminScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madproject.videogames.data.utils.UtilsDataStore
import com.madproject.videogames.data.utils.model.Utils
import com.madproject.videogames.data.withdrawalRequest.model.WithdrawalRequestStatus
import com.madproject.videogames.screen.compose.adminScreen.view.AddInfoAlertDialog
import com.madproject.videogames.screen.theme.tintColor
import com.madproject.videogames.R

@Composable
fun AdminScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val utilsDataStore = remember(::UtilsDataStore)
    var utils by remember { mutableStateOf<Utils?>(null) }
    var addInfoAlertDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        utilsDataStore.get({utils = it})
    })

    Surface {

        if(addInfoAlertDialog){
            AddInfoAlertDialog(
                info = utils?.info ?: "",
                onDismissRequest = { addInfoAlertDialog = false },
                onUpdateInfo = {
                    utilsDataStore.updateInfo(it){
                        addInfoAlertDialog = false
                        utilsDataStore.get({utils = it})
                    }
                }
            )
        }

        Box(modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
            listOf(
                Color(0xFF2C5364),
                Color(0xFF203A43),
                Color(0xFF0F2027)
            )
        )))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                ),
                onClick = {
                    navController.navigate(
                        "WithdrawalRequestsScreen/${WithdrawalRequestStatus.PAID}"
                    )
                }
            ) {
                Text(
                    text = "Заявки статус '${WithdrawalRequestStatus.PAID.text}'",
                    color = Color.White
                )
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                ),
                onClick = {
                    navController.navigate(
                        "WithdrawalRequestsScreen/${WithdrawalRequestStatus.WAITING}"
                    )
                }
            ) {
                Text(
                    text = "Заявки статус '${WithdrawalRequestStatus.WAITING.text}'",
                    color = Color.White
                )
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                ),
                onClick = {
                    addInfoAlertDialog = true
                }
            ) {
                Text(text = "Изменить информацию", color = Color.White)
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                ),
                onClick = {
                    navController.navigate("Settings")
                }
            ) {
                Text(text = "Настройки", color = Color.White)
            }
        }
    }
}