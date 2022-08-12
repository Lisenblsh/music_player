package com.lis.player_java.vkaudiotoken

import com.google.gson.annotations.SerializedName

data class TokenModel(
    @SerializedName("access_token")
    val accessToken: String
)

data class TokenErrorModel(
    val error: String,
    @SerializedName("validation_sid")
    val validationSid: String? = null,
    @SerializedName("error_description")
    val errorDescription: String? = null,
    @SerializedName("captcha_sid")
    val captchaSid: String? = null,
    @SerializedName("captcha_img")
    val captchaImg: String? = null
)

enum class TokenErrorType {
    INVALID_CLIENT {
        override fun getTitle() = "invalid_client"
    },
    NEED_VALIDATION {
        override fun getTitle() = "need_validation"
    },
    NEED_CAPTCHA {
        override fun getTitle() = "need_captcha"
    },
    INVALID_REQUEST {
        override fun getTitle() = "invalid_request"
    };

    abstract fun getTitle(): String
}