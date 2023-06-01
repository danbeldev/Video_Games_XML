package com.madproject.videogames.data.withdrawalRequest.model

enum class WithdrawalRequestStatus(val text: String) {
    WAITING("Ожидает оплаты"),
    PAID(text = "Счет оплачен")
}

data class WithdrawalRequest(
    var id:String = "",
    var userId:String = "",
    val userEmail:String = "",
    val phoneNumber:String = "",
    val countInterstitialAds: Int = 0,
    val countInterstitialAdsClick: Int = 0,
    val countRewardedAds: Int = 0,
    val countRewardedAdsClick: Int = 0,
    val version: Int? = 1,
    val achievementPrice: Double = 0.0,
    val referralLinkMoney: Double = 0.0,
    val giftMoney: Double = 0.0,
    val checkVpn: Boolean? = null,
    val status: WithdrawalRequestStatus = WithdrawalRequestStatus.WAITING,
    val date: Long? = null,
    val checkEmulator: Boolean? = null,
    val macAddress: String? = null,
    val iPv4: String? = null,
    val iPv6: String? = null,
    val deviceName: String? = null,
    val androidVersion: String? = null,
){
    fun dataMap(): MutableMap<String, Any> {
        val map = mutableMapOf<String,Any>()

        map["id"] = id
        map["userId"] = userId
        map["userEmail"] = userEmail
        map["phoneNumber"] = phoneNumber
        map["countInterstitialAds"] = countInterstitialAds
        map["countInterstitialAdsClick"] = countInterstitialAdsClick
        map["countRewardedAds"] = countRewardedAds
        map["countRewardedAdsClick"] = countRewardedAdsClick
        map["achievementPrice"] = achievementPrice
        map["referralLinkMoney"] = referralLinkMoney
        map["giftMoney"] = giftMoney
        checkVpn?.let {
            map["checkVpn"] = checkVpn
        }
        map["status"] = status
        date?.let {
            map["date"] = date
        }
        version?.let {
            map["version"] = version
        }
        checkEmulator?.let {
            map["checkEmulator"] = checkEmulator
        }
        macAddress?.let {
            map["macAddress"] = macAddress
        }
        iPv4?.let {
            map["iPv4"] = iPv4
        }
        iPv6?.let {
            map["iPv6"] = iPv6
        }
        deviceName?.let {
            map["deviceName"] = deviceName
        }
        androidVersion?.let {
            map["androidVersion"] = androidVersion
        }

        return map
    }
}