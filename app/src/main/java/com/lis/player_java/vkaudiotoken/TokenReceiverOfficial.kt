package com.lis.player_java.vkaudiotoken

import com.google.gson.Gson
import com.lis.player_java.vkaudiotoken.network.RetrofitService
import com.lis.player_java.vkaudiotoken.network.VK_OFFICIAL
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random


class TokenReceiverOfficial(
    private val login: String,
    private val password: String
) {

    suspend fun getToken(
        code: String? = null,
        captchaSid: String? = null,
        captchaKey: String? = null
    ): String {
        return getNonRefreshed(code, captchaSid, captchaKey)
    }

    private suspend fun getNonRefreshed(
        code: String? = null,
        captchaSid: String? = null,
        captchaKey: String? = null
    ): String {
        val deviceID = generateRandomString()

        val retrofitService = RetrofitService.create(VK_OFFICIAL.userAgent, "https://oauth.vk.com/")
        val lang = if(Locale.getDefault().language == "ru") "ru" else "en"
        Locale.getDefault().language
        val response = retrofitService.getToken(
            lang = lang,
            clientId = VK_OFFICIAL.clientId,
            clientSecret = VK_OFFICIAL.clientSecret,
            username = login,
            password = password,
            deviceID = deviceID,
            code = code,
            captchaSid = captchaSid,
            captchaKey = captchaKey
        )

        return checkResponse(response)
    }

    private suspend fun checkResponse(response: Response<String>): String {
        if (response.isSuccessful) {
            val body: TokenModel = Gson().fromJson(response.body(), TokenModel::class.java)
            return body.accessToken
        } else if (response.code() == 401) {
            val errorBody: TokenErrorModel? =
                Gson().fromJson(response.errorBody()?.charStream(), TokenErrorModel::class.java)
            when (errorBody?.error) {
                TokenErrorType.INVALID_CLIENT.getTitle() -> {
                    throw TokenException(
                        code = TokenExceptionType.REGISTRATION_ERROR,
                        message = errorBody.errorDescription
                    )
                }
                TokenErrorType.NEED_VALIDATION.getTitle() -> {
                    errorBody.validationSid?.let { TwoFAHelper().validatePhone(it) }
                    throw TokenException(
                        code = TokenExceptionType.TWO_FA_REQ,
                        validationSid = errorBody.validationSid,
                        message = errorBody.errorDescription
                    )
                }
                TokenErrorType.INVALID_REQUEST.getTitle() -> {
                    throw TokenException(
                        code = TokenExceptionType.REQUEST_ERR,
                    )
                }
                TokenErrorType.NEED_CAPTCHA.getTitle() -> {
                    throw TokenException(
                        code = TokenExceptionType.NEED_CAPTCHA,
                        captchaSid = errorBody.captchaSid,
                        captchaImg = errorBody.captchaImg,
                        message = errorBody.errorDescription
                    )
                }
            }
        }
        throw TokenException(TokenExceptionType.TOKEN_NOT_RECEIVED, "")
    }

    private fun generateRandomString(): String {
        val length = 16
        val characters = "0123456789abcdef"
        val characterLength = characters.length
        var randomString = ""
        for (i in 0 until length) {
            randomString += characters[Random.nextInt(0, characterLength - 1)]
        }
        return randomString
    }

}

interface CoroutineCallback<RESULT> {

    companion object {

        @JvmOverloads
        fun <R> call(
            callback: CoroutineCallback<R>,
            dispatcher: CoroutineDispatcher = Dispatchers.Default
        ): Continuation<R> {
            return object : Continuation<R> {
                override val context: CoroutineContext
                    get() = dispatcher

                override fun resumeWith(result: Result<R>) {
                    callback.onComplete(result.getOrNull(), result.exceptionOrNull())
                }
            }
        }
    }

    fun onComplete(result: RESULT?, error: Throwable?)
}




