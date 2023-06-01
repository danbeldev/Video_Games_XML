package com.madproject.videogames.screen.compose.settingsScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madproject.videogames.data.utils.UtilsDataStore
import com.madproject.videogames.data.utils.model.Utils
import com.madproject.videogames.data.utils.model.UtilsAdsType
import com.madproject.videogames.screen.compose.mainScreen.MainButton
import com.madproject.videogames.screen.theme.primaryBackground
import com.madproject.videogames.screen.theme.primaryText
import com.madproject.videogames.screen.theme.secondaryBackground
import com.madproject.videogames.screen.theme.tintColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    navController: NavController
) {
    val utilsDataStore = remember(::UtilsDataStore)

    var bannerYandexAdsId by remember { mutableStateOf("") }
    var my_target_rewarded_yandex_ads_id by remember { mutableStateOf("") }
    var my_target_interstitial_yandex_ads_id by remember { mutableStateOf("") }
    var my_target_banner_yandex_ads_id by remember { mutableStateOf("") }
    var stort_id by remember { mutableStateOf("") }
    var interstitialAdsClickPrice by remember { mutableStateOf("") }
    var interstitialAdsPrice by remember { mutableStateOf("") }
    var interstitialYandexAdsId by remember { mutableStateOf("") }
    var minPriceWithdrawalRequest by remember { mutableStateOf("") }
    var rewardedAdsClick by remember { mutableStateOf("") }
    var rewardedAdsPrice by remember { mutableStateOf("") }
    var rewardedYandexAdsId by remember { mutableStateOf("") }
    var bannerAdsPrice by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    var bannerAdsClickPrice by remember { mutableStateOf("") }
    var adsType by remember { mutableStateOf(UtilsAdsType.YANDEX) }

    LaunchedEffect(key1 = Unit, block = {
        utilsDataStore.get(onSuccess = {  utils ->
            bannerYandexAdsId = utils.banner_yandex_ads_id
            interstitialAdsClickPrice = utils.interstitial_ads_click_price.toString()
            interstitialAdsPrice = utils.interstitial_ads_price.toString()
            interstitialYandexAdsId = utils.interstitial_yandex_ads_id
            minPriceWithdrawalRequest = utils.min_price_withdrawal_request.toString()
            rewardedAdsClick = utils.rewarded_ads_click.toString()
            rewardedAdsPrice = utils.rewarded_ads_price.toString()
            rewardedYandexAdsId = utils.rewarded_yandex_ads_id
            bannerAdsPrice = utils.banner_ads_price.toString()
            bannerAdsClickPrice = utils.banner_ads_click_price.toString()
            info = utils.info
            adsType = utils.adsType
            my_target_rewarded_yandex_ads_id = utils.my_target_rewarded_yandex_ads_id
            my_target_interstitial_yandex_ads_id = utils.my_target_interstitial_yandex_ads_id
            my_target_banner_yandex_ads_id = utils.my_target_banner_yandex_ads_id
            stort_id = utils.stort_id
        })
    })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {

                LazyRow {
                    item {
                        UtilsAdsType.values().forEach {
                            Card(
                                modifier = Modifier.padding(10.dp),
                                shape = AbsoluteRoundedCornerShape(15.dp),
                                backgroundColor = secondaryBackground,
                                border = if(it == adsType)
                                    BorderStroke(2.dp, tintColor)
                                else
                                    null,
                                onClick = {
                                    adsType = it
                                }
                            ) {
                                Text(
                                    text = it.name,
                                    modifier = Modifier.padding(15.dp),
                                    color = primaryText
                                )
                            }
                        }
                    }
                }

                Divider(color = tintColor)

                BaseOutlinedTextField(
                    label = "Полноэкраная (с переходом)",
                    value = interstitialAdsClickPrice,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { interstitialAdsClickPrice = it }
                )

                BaseOutlinedTextField(
                    label = "Полноэкраная (без перехода)",
                    value = interstitialAdsPrice,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { interstitialAdsPrice = it }
                )

                BaseOutlinedTextField(
                    label = "Видео (с перехода)",
                    value = rewardedAdsClick,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { rewardedAdsClick = it }
                )

                BaseOutlinedTextField(
                    label = "Видео (без перехода)",
                    value = rewardedAdsPrice,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { rewardedAdsPrice = it }
                )

//                BaseOutlinedTextField(
//                    label = "Баннер (с переходом больше 10 секунд)",
//                    value = bannerAdsClickPrice,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    onValueChange = { bannerAdsClickPrice = it }
//                )
//
//                BaseOutlinedTextField(
//                    label = "Баннер (с переходом меньше 10 секунд)",
//                    value = bannerAdsPrice,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    onValueChange = { bannerAdsPrice = it }
//                )

                BaseOutlinedTextField(
                    label = "Минимальная сумма для вывода",
                    value = minPriceWithdrawalRequest,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { minPriceWithdrawalRequest = it }
                )

                Divider(color = tintColor)

                BaseOutlinedTextField(
                    label = "Yandex Баннер id",
                    value = bannerYandexAdsId,
                    onValueChange = { bannerYandexAdsId = it }
                )

                BaseOutlinedTextField(
                    label = "Yandex Полноэкраная id",
                    value = interstitialYandexAdsId,
                    onValueChange = { interstitialYandexAdsId = it }
                )

                BaseOutlinedTextField(
                    label = "Yandex Видео id",
                    value = rewardedYandexAdsId,
                    onValueChange = { rewardedYandexAdsId = it }
                )

                Divider(color = tintColor)

                BaseOutlinedTextField(
                    label = "My target Баннер id",
                    value = my_target_banner_yandex_ads_id,
                    onValueChange = { my_target_banner_yandex_ads_id = it }
                )

                BaseOutlinedTextField(
                    label = "My target Полноэкраная id",
                    value = my_target_interstitial_yandex_ads_id,
                    onValueChange = { my_target_interstitial_yandex_ads_id = it }
                )

                BaseOutlinedTextField(
                    label = "My target Видео id",
                    value = my_target_rewarded_yandex_ads_id,
                    onValueChange = { my_target_rewarded_yandex_ads_id = it }
                )

                Divider(color = tintColor)

                BaseOutlinedTextField(
                    label = "Stort id",
                    value = stort_id,
                    onValueChange = { stort_id = it }
                )

                Divider(color = tintColor)

                Spacer(modifier = Modifier.height(10.dp))

                MainButton(
                    text = "Сохранить",
                    onClick = {
                        utilsDataStore.create(
                            utils = Utils(
                                banner_yandex_ads_id = bannerYandexAdsId,
                                interstitial_ads_click_price = interstitialAdsClickPrice.toDouble(),
                                interstitial_ads_price = interstitialAdsPrice.toDouble(),
                                interstitial_yandex_ads_id = interstitialYandexAdsId,
                                min_price_withdrawal_request = minPriceWithdrawalRequest.toDouble(),
                                rewarded_ads_click = rewardedAdsClick.toDouble(),
                                rewarded_ads_price = rewardedAdsPrice.toDouble(),
                                rewarded_yandex_ads_id = rewardedYandexAdsId,
                                banner_ads_click_price = bannerAdsClickPrice.toDouble(),
                                banner_ads_price = bannerAdsPrice.toDouble(),
                                info = info,
                                adsType = adsType,
                                stort_id = stort_id,
                                my_target_banner_yandex_ads_id = my_target_banner_yandex_ads_id,
                                my_target_interstitial_yandex_ads_id = my_target_interstitial_yandex_ads_id,
                                my_target_rewarded_yandex_ads_id = my_target_rewarded_yandex_ads_id
                            ),
                            onSuccess = { navController.navigateUp() }
                        )
                    },
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun BaseOutlinedTextField(
    label: String,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit = {}
){
    Text(
        text = label,
        fontWeight = FontWeight.W900,
        modifier = Modifier.padding(5.dp)
    )

    OutlinedTextField(
        modifier = Modifier.padding(bottom = 5.dp, start = 5.dp),
        shape = AbsoluteRoundedCornerShape(15.dp),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = primaryText,
            disabledTextColor = tintColor,
            backgroundColor = secondaryBackground,
            cursorColor = tintColor,
            focusedBorderColor = tintColor,
            unfocusedBorderColor = secondaryBackground,
            disabledBorderColor = secondaryBackground
        )
    )
}