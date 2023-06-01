package com.madproject.videogames.screen.compose.mainScreen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.madproject.videogames.R
import com.madproject.videogames.common.RootUtils.getAndroidVersion
import com.madproject.videogames.common.RootUtils.getDeviceName
import com.madproject.videogames.common.RootUtils.getIPAddress
import com.madproject.videogames.common.RootUtils.getMacAddress
import com.madproject.videogames.common.RootUtils.isProbablyRunningOnEmulator
import com.madproject.videogames.common.openBrowser
import com.madproject.videogames.common.vpn
import com.madproject.videogames.data.user.model.UserRole
import com.madproject.videogames.data.user.model.createUserLoading
import com.madproject.videogames.data.user.model.userSumMoneyVersion2
import com.madproject.videogames.data.utils.UtilsDataStore
import com.madproject.videogames.data.utils.model.Utils
import com.madproject.videogames.data.withdrawalRequest.model.WithdrawalRequest
import com.madproject.videogames.screen.compose.mainScreen.view.RewardAlertDialog
import com.madproject.videogames.screen.view.OnLifecycleEvent
import com.madproject.videogames.screen.compose.mainScreen.view.InfoAlertDialog
import com.madproject.videogames.screen.theme.primaryBackground
import com.madproject.videogames.screen.theme.primaryText
import com.madproject.videogames.screen.theme.tintColor
import com.madproject.videogames.screen.view.YandexAdsBanner
import com.madproject.videogames.screen.view.addCountBannerWatch
import com.madproject.videogames.yandexAds.InterstitialYandexAds
import com.madproject.videogames.yandexAds.RewardedYandexAds
import org.joda.time.Period

@SuppressLint("NewApi")
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    var user by remember { mutableStateOf(createUserLoading()) }

    var rewardAlertDialog by remember { mutableStateOf(false) }
    var infoAlertDialog by remember { mutableStateOf(false) }
    var utils by remember { mutableStateOf<Utils?>(null) }
    val utilsDataStore = remember(::UtilsDataStore)

    val interstitialYandexAds = remember {
        InterstitialYandexAds(context, onDismissed = { adClickedDate, returnedToDate ->
            user ?: return@InterstitialYandexAds

            if(adClickedDate != null && returnedToDate != null){
                if((Period(adClickedDate, returnedToDate)).seconds >= 10){
                    viewModel.updateCountInterstitialAdsClick(user!!.countInterstitialAdsClick + 1)
                }else {
                    viewModel.updateCountInterstitialAds(user!!.countInterstitialAds + 1)
                }
            } else {
                viewModel.updateCountInterstitialAds(user!!.countInterstitialAds + 1)
            }
        })
    }

    val rewardedYandexAds = remember {
        RewardedYandexAds(context, onDismissed = { adClickedDate, returnedToDate, rewarded ->
            if(adClickedDate != null && returnedToDate != null && rewarded && user != null){
                if((Period(adClickedDate, returnedToDate)).seconds >= 10){
                    viewModel.updateCountRewardedAdsClick(user!!.countRewardedAdsClick + 1)
                }else {
                    viewModel.updateCountRewardedAds(user!!.countRewardedAds + 1)
                }
            } else if(rewarded && user != null){
                viewModel.updateCountRewardedAds(user!!.countRewardedAds + 1)
            }
        })
    }

    OnLifecycleEvent { owner, event ->
        if(event == Lifecycle.Event.ON_DESTROY){
            rewardedYandexAds.destroy()
            interstitialYandexAds.destroy()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getUser { user = it }

        utilsDataStore.get(
            onSuccess = { utils = it }
        )
    })

    if(rewardAlertDialog){
        RewardAlertDialog(
            utils = utils,
            countBannerAdsClick = user.countBannerAdsClick,
            countBannerAds = user.countBannerAds,
            countInterstitialAds = user.countInterstitialAds,
            countInterstitialAdsClick = user.countInterstitialAdsClick,
            countRewardedAds = user.countRewardedAds,
            countRewardedAdsClick = user.countRewardedAdsClick,
            onDismissRequest = {
                rewardAlertDialog = false
            },
            onSendWithdrawalRequest = { phoneNumber ->
                val withdrawalRequest = WithdrawalRequest(
                    countInterstitialAds = user.countInterstitialAds,
                    countInterstitialAdsClick = user.countInterstitialAdsClick,
                    countRewardedAds = user.countRewardedAds,
                    countRewardedAdsClick = user.countRewardedAdsClick,
                    phoneNumber = phoneNumber,
                    userEmail = user.email,
                    version = 2,
                    achievementPrice = user!!.achievementPrice,
                    checkVpn = vpn(),
                    referralLinkMoney = user!!.referralLinkMoney,
                    giftMoney = user!!.giftMoney,
                    macAddress = getMacAddress(context),
                    checkEmulator = isProbablyRunningOnEmulator,
                    iPv4 = getIPAddress(true),
                    iPv6 = getIPAddress(false),
                    deviceName = getDeviceName(),
                    androidVersion = getAndroidVersion()
                )

                viewModel.sendWithdrawalRequest(withdrawalRequest,{
                    rewardAlertDialog = false
                    Toast.makeText(context, "Заявка отправлена", Toast.LENGTH_SHORT).show()
                },{ message ->
                    Toast.makeText(context, "Ошибка: $message", Toast.LENGTH_SHORT).show()
                })
            }
        )
    }

    if(infoAlertDialog){
        InfoAlertDialog(info = utils?.info ?: "") {
            infoAlertDialog = false
        }
    }

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

    Column {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            BaseLottieAnimation(
//                type = LottieAnimationType.LOGIN,
//                modifier = Modifier.size(150.dp)
//            )

            Board(
                text = if(utils == null)
                    "0.0"
                else
                    userSumMoneyVersion2(
                        utils = utils!!,
                        countInterstitialAds = user.countInterstitialAds,
                        countInterstitialAdsClick = user.countInterstitialAdsClick,
                        countRewardedAds = user.countRewardedAds,
                        countRewardedAdsClick = user.countRewardedAdsClick,
                        countBannerAds = user.countBannerAds,
                        countBannerAdsClick = user.countBannerAdsClick
                    ).toString()
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Смотрите рекламу и зарабатывайте !",
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.W900,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                color = primaryText
            )


            Spacer(modifier = Modifier.height((screenHeightDp / 15).dp))

            MainButton(text = "Полноэкранная") {
                interstitialYandexAds.show()
            }

            MainButton(text = "Видео") {
                rewardedYandexAds.show()
            }

            MainButton(text = "Награда") {
                rewardAlertDialog = true
            }

            if(user.userRole == UserRole.ADMIN){
                MainButton(text = "Админ"){
                    navController.navigate("Admin")
                }
            }

            utils?.let {
                YandexAdsBanner(
                    adUnitId = it.banner_yandex_ads_id,
                    returnedToApplication = { adClickedDate, returnedToDate ->
                        addCountBannerWatch(
                            adClickedDate = adClickedDate,
                            returnedToDate = returnedToDate,
                            user = user,
                            updateCountBannerAdsClick = {
                                viewModel.updateCountBannerAdsClick(it)
                            },
                            updateCountBannerAds = {
                                viewModel.updateCountBannerAds(it)
                            }
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    context.openBrowser("https://t.me/+GD7DP0UDtdIzMDUy")
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.telegram),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(5.dp)
                    )
                }

                AnimatedVisibility(visible = utils != null && utils!!.info.isNotEmpty()) {
                    IconButton(onClick = {
                        infoAlertDialog = true
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.info),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainButton(
    text:String,
    onClick: () -> Unit
) {
    val gradient = Brush.horizontalGradient(listOf(
        Color(0xFFFFE259),
        Color(0xFFFFA751)
    ))

    Button(
        modifier = Modifier
            .padding(
                horizontal = 15.dp,
                vertical = 5.dp
            )
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        shape = AbsoluteRoundedCornerShape(15.dp),
        onClick = { onClick() })
    {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 50.dp, vertical = 16.dp)
                .clip(AbsoluteRoundedCornerShape(20.dp))
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = Color.White)
        }
    }
}

@Composable
private fun Board(text: String) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = AbsoluteRoundedCornerShape(15.dp),
        border = BorderStroke(3.dp, tintColor),
        backgroundColor = primaryBackground
    ) {
        Text(
            text = "$text ₽",
            fontSize = 35.sp,
            fontWeight = FontWeight.W900,
            modifier = Modifier.padding(10.dp),
            color = primaryText,
            textAlign = TextAlign.Center
        )
    }
}