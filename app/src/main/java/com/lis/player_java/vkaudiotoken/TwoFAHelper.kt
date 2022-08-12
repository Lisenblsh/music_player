package com.lis.player_java.vkaudiotoken

import com.lis.player_java.vkaudiotoken.TokenException
import com.lis.player_java.vkaudiotoken.TokenExceptionType
import com.lis.player_java.vkaudiotoken.network.RetrofitService
import com.lis.player_java.vkaudiotoken.network.VK_OFFICIAL

class TwoFAHelper {
    suspend fun validatePhone(validationSid: String){
        val retrofitService = RetrofitService.create(VK_OFFICIAL.userAgent, "https://api.vk.com/method/")

        val response = retrofitService.validatePhone(validationSid)

        if(!response.isSuccessful){
            throw TokenException(TokenExceptionType.TWO_FA_ERR, message = "")
        }
    }
}