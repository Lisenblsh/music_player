package com.lis.player_java.vkaudiotoken

class TokenException(
    val code: TokenExceptionType,
    val validationSid: String? = null,
    val captchaSid: String? = null,
    val captchaImg: String? = null,
    override val message: String? = null
) : Exception(message)

enum class TokenExceptionType {
    REGISTRATION_ERROR,
    TOKEN_NOT_RECEIVED,
    NEED_CAPTCHA,
    REQUEST_ERR,
    TWO_FA_REQ,
    TWO_FA_ERR,
}