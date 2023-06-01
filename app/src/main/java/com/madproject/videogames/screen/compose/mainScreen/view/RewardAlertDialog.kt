package com.madproject.videogames.screen.compose.mainScreen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madproject.videogames.data.user.model.userSumMoneyVersion2
import com.madproject.videogames.data.utils.model.Utils
import com.madproject.videogames.screen.theme.primaryBackground
import com.madproject.videogames.screen.theme.primaryText
import com.madproject.videogames.screen.theme.secondaryBackground
import com.madproject.videogames.screen.theme.tintColor

@Composable
fun RewardAlertDialog(
    utils: Utils?,
    countInterstitialAds:Int,
    countInterstitialAdsClick:Int,
    countRewardedAds:Int,
    countRewardedAdsClick:Int,
    countBannerAdsClick: Int,
    countBannerAds: Int,
    onDismissRequest:() -> Unit,
    onSendWithdrawalRequest: (phoneNumber: String) -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("Введите номер телефона") }

    AlertDialog(
        backgroundColor = primaryBackground,
        shape = AbsoluteRoundedCornerShape(20.dp),
        modifier = Modifier.height(425.dp).border(3.dp, tintColor, AbsoluteRoundedCornerShape(20.dp)),
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Деньги придут Вам в течение трех рабочих дней." +
                    "\nВывод на киви кошелёк." +
                    "\nМинимальная сумма для вывода ${utils?.min_price_withdrawal_request ?: ""} ₽",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(5.dp).fillMaxWidth(),
                color = primaryText
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W900,
                        color = Color.Red
                    )
                }

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier.padding(5.dp),
                    shape = AbsoluteRoundedCornerShape(15.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
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
                        Text(text = "Номер телефона", color = primaryText)
                    }
                )
            }
        },
        buttons = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                ),
                onClick = {
                    if(utils != null){
                        verifySendWithdrawalRequest(
                            utils = utils,
                            countInterstitialAds = countInterstitialAds,
                            countInterstitialAdsClick = countInterstitialAdsClick,
                            countRewardedAds = countRewardedAds,
                            countRewardedAdsClick = countRewardedAdsClick,
                            countBannerAdsClick = countBannerAdsClick,
                            countBannerAds = countBannerAds,
                            phoneNumber = phoneNumber.trim(),
                            onError = {
                                errorMessage = it
                            },
                            onSendWithdrawalRequest = {
                                errorMessage = ""
                                onSendWithdrawalRequest(phoneNumber)
                            }
                        )
                    }
                }
            ) {
                Text(text = "Отправить", color = Color.White)
            }
        }
    )
}

private fun verifySendWithdrawalRequest(
    utils: Utils,
    countInterstitialAds: Int,
    countInterstitialAdsClick: Int,
    countRewardedAds: Int,
    countRewardedAdsClick: Int,
    countBannerAdsClick: Int,
    countBannerAds: Int,
    phoneNumber: String,
    onSendWithdrawalRequest: (phoneNumber: String) -> Unit,
    onError: (String) -> Unit
){
    if(userSumMoneyVersion2(
            utils = utils,
            countInterstitialAds = countInterstitialAds,
            countInterstitialAdsClick = countInterstitialAdsClick,
            countRewardedAds = countRewardedAds,
            countRewardedAdsClick = countRewardedAdsClick,
            countBannerAds = countBannerAds,
            countBannerAdsClick = countBannerAdsClick
        ) < utils.min_price_withdrawal_request){
        onError("Минимальная сумма для вывода ${utils.min_price_withdrawal_request} рублей")
    }else if(phoneNumber.isEmpty()){
        onError("Укажите номер телефона")
    }else {
        onSendWithdrawalRequest(phoneNumber)
    }
}