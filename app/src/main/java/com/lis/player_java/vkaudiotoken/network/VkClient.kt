package com.lis.player_java.vkaudiotoken.network

data class VkClient(
    val userAgent: String,
    val clientSecret: String,
    val clientId: String
)

val KATE = VkClient(
    "KateMobileAndroid/56 lite-460 (Android 4.4.2; SDK 19; x86; unknown Android SDK built for x86; en)",
    "lxhD8OD7dMsqtXIm5IUY",
    "2685278"
)

val VK_OFFICIAL = VkClient(
    "VKAndroidApp/5.64 (Android 11; SDK 30; x86_64; unknown Android SDK built for x86_64; en; 320x240)",
    "hHbZxrka2uZ6jB1inYsH",
    "2274003"
)