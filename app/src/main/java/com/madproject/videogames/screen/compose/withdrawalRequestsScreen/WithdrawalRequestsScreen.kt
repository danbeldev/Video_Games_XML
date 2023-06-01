package com.madproject.videogames.screen.compose.withdrawalRequestsScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madproject.videogames.R
import com.madproject.videogames.common.setClipboard
import com.madproject.videogames.data.user.model.userSumMoneyVersion2
import com.madproject.videogames.data.utils.UtilsDataStore
import com.madproject.videogames.data.utils.model.Utils
import com.madproject.videogames.data.withdrawalRequest.WithdrawalRequestDataStore
import com.madproject.videogames.data.withdrawalRequest.model.WithdrawalRequest
import com.madproject.videogames.data.withdrawalRequest.model.WithdrawalRequestStatus
import com.madproject.videogames.screen.theme.primaryBackground
import com.madproject.videogames.screen.theme.primaryText
import com.madproject.videogames.screen.theme.tintColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WithdrawalRequestsScreen(
    navController: NavController,
    withdrawalRequestStatus: WithdrawalRequestStatus
) {
    val context = LocalContext.current

    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    var deleteWithdrawalRequestId by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var utils by remember { mutableStateOf<Utils?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var withdrawalRequests by remember { mutableStateOf(listOf<WithdrawalRequest>()) }
    val withdrawalRequestDataStore = remember(::WithdrawalRequestDataStore)
    val utilsDataStore = remember(::UtilsDataStore)

    LaunchedEffect(key1 = Unit, block = {
        utilsDataStore.get({utils = it})
    })

    LaunchedEffect(key1 = userId, block = {
        withdrawalRequests = emptyList()
        isLoading = true
        withdrawalRequestDataStore.getAll({
            withdrawalRequests = it.filter {
                if(userId.isNotEmpty())
                    it.userId == userId
                else
                    withdrawalRequestStatus == it.status
            }
            isLoading = false
        }, {})
    })

    BackHandler {
        if(userId.isNotEmpty())
            userId = ""
        else
            navController.navigateUp()
    }

    if (deleteWithdrawalRequestId.isNotEmpty()) {
        DeleteWithdrawalRequestAlertDialog(
            onDismissRequest = {
                deleteWithdrawalRequestId = ""
            },
            confirm = {
                withdrawalRequestDataStore.updateStatus(
                    id = deleteWithdrawalRequestId,
                    status = when(withdrawalRequestStatus){
                        WithdrawalRequestStatus.WAITING -> WithdrawalRequestStatus.PAID
                        WithdrawalRequestStatus.PAID -> WithdrawalRequestStatus.WAITING
                    },
                    onSuccess = {
                        deleteWithdrawalRequestId = ""
                        withdrawalRequests = emptyList()
                        isLoading = true

                        withdrawalRequestDataStore.getAll({
                            withdrawalRequests = it.filter {
                                if(userId.isNotEmpty())
                                    it.userId == userId
                                else
                                    withdrawalRequestStatus == it.status
                            }
                            isLoading = false
                        }, {})
                    },
                    onError = {
                        Toast.makeText(context, "Ошибка: $it", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        )
    }


    Surface {

        Box(modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(
            listOf(
                Color(0xFF2C5364),
                Color(0xFF203A43),
                Color(0xFF0F2027)
            )
        )))

        LazyColumn(
            modifier = Modifier.size(
                width = screenWidthDp.dp,
                height = screenHeightDp.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(isLoading){
                item {
                    CircularProgressIndicator(color = tintColor)
                }
            }

            if(userId.isNotEmpty()){
                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        onClick = {
                            userId = ""
                        },
                        shape = AbsoluteRoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = tintColor
                        )
                    ) {
                        Text(text = "Вернуться назад", color = Color.White)
                    }
                }
            }

            items(withdrawalRequests) { item ->

                Card(
                    shape = AbsoluteRoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    backgroundColor = primaryBackground
                ) {
                    Column {
                        Text(
                            text = "Индификатор пользователя : ${item.userId}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.userId)
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Электронная почта : ${item.userEmail}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.userEmail)
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Номер телефона : ${item.phoneNumber}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.phoneNumber)
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Полноэкраная : ${item.countInterstitialAds}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(
                                            context,
                                            (item.countInterstitialAds.toString())
                                        )
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Полноэкраная переход 10 сек : ${item.countInterstitialAdsClick}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(
                                            context,
                                            item.countInterstitialAdsClick.toString()
                                        )
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Вознаграждением : ${item.countRewardedAds}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.countRewardedAds.toString())
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Вознаграждением переход на 10 сек: ${item.countRewardedAdsClick}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.countRewardedAdsClick.toString())
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Vpn: ${if(item.checkVpn == true) "Да" else "Нет"}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.achievementPrice.toString())
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Статус: " + item.status.text,
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.achievementPrice.toString())
                                    })
                                },
                            color = primaryText
                        )

                        utils?.let {

                            val sum = userSumMoneyVersion2(
                                utils = it,
                                countInterstitialAds = item.countInterstitialAds,
                                countInterstitialAdsClick = item.countInterstitialAdsClick,
                                countRewardedAds = item.countRewardedAds,
                                countRewardedAdsClick = item.countRewardedAdsClick,
                                countBannerAds = 0,
                                countBannerAdsClick = 0
                            )

                            Text(
                                text = "Сумма для ввывода : $sum",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, sum.toString())
                                        })
                                    },
                                color = primaryText
                            )
                        }
                        item.checkEmulator?.let { checkEmulator ->
                            Text(
                                text = "Эмулятор : ${if(checkEmulator) "Да" else "Нет"}",
                                color = primaryText,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, checkEmulator.toString())
                                        })
                                    }
                            )
                        }

                        item.checkVpn?.let { checkVpn ->
                            Text(
                                text = "Vpn : ${if(checkVpn) "Да" else "Нет"}",
                                color = primaryText,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, checkVpn.toString())
                                        })
                                    }
                            )
                        }

                        item.macAddress?.let { macAddress ->
                            Text(
                                text = "Mac address : $macAddress",
                                color = primaryText,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, macAddress)
                                        })
                                    }
                            )
                        }

                        item.iPv4?.let { iPv4 ->
                            Text(
                                text = "IPv4 : $iPv4",
                                color = primaryText,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, iPv4)
                                        })
                                    }
                            )
                        }

                        item.iPv6?.let { iPv6 ->
                            Text(
                                text = "IPv6 : $iPv6",
                                color = primaryText,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, iPv6)
                                        })
                                    }
                            )
                        }

                        item.deviceName?.let { deviceName ->
                            Text(
                                text = "Имя устройства : $deviceName",
                                color = primaryText,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, deviceName)
                                        })
                                    }
                            )
                        }

                        item.androidVersion?.let { androidVersion ->
                            Text(
                                text = "Версия Android : $androidVersion",
                                color = primaryText,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, androidVersion)
                                        })
                                    }
                            )
                        }

                        item.date?.let { date ->
                            Text(
                                text = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(
                                    Date(date)
                                ),
                                color = primaryText,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, item.achievementPrice.toString())
                                        })
                                    }
                            )
                        }

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            onClick = {
                                userId = if(userId.isNotEmpty())
                                    ""
                                else
                                    item.userId
                            },
                            shape = AbsoluteRoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = tintColor
                            )
                        ) {
                            Text(text = if(userId.isNotEmpty()) "Вернуться назад" else "Другие заявки пользователя")
                        }

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            onClick = { deleteWithdrawalRequestId = item.id },
                            shape = AbsoluteRoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = tintColor
                            )
                        ) {
                            Text(text = "Сменить статус", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun DeleteWithdrawalRequestAlertDialog(
    onDismissRequest: () -> Unit,
    confirm: () -> Unit
) {
    AlertDialog(
        shape = AbsoluteRoundedCornerShape(15.dp),
        onDismissRequest = onDismissRequest,
        buttons = {
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                onClick = confirm
            ) {
                Text(text = "Подтвердить", color = Color.Red)
            }
        }
    )
}