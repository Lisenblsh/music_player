package com.lis.player_java.ui

import android.app.backup.BackupAgent
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.lis.player_java.vkaudiotoken.TokenReceiverOfficial
import android.widget.Toast
import com.lis.player_java.R
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lis.player_java.ImageFun
import com.lis.player_java.databinding.FragmentAuthorizationBinding
import com.lis.player_java.vkaudiotoken.TokenException
import com.lis.player_java.vkaudiotoken.TokenExceptionType
import com.lis.player_java.vkaudiotoken.network.VK_OFFICIAL
import kotlinx.coroutines.launch
import kotlin.math.log

class AuthorizationFragment : DialogFragment() {
    private lateinit var binding: FragmentAuthorizationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        binding.bindElement()
        return binding.root
    }

    private fun FragmentAuthorizationBinding.bindElement() {
        getTokenButton.setOnClickListener {
            lifecycleScope.launch {
                confirmListenerClick()
            }
        }
    }

    private var captchaSid: String? = null
    private suspend fun FragmentAuthorizationBinding.confirmListenerClick() {
        val login = usernameEditText.text.toString().ifEmpty { return }
        val password = passwordEditText.text.toString().ifEmpty { return }
        val code = codeEditText.text.toString()
            .ifEmpty { if (codeEditText.visibility == View.GONE) null else return }
        val captchaKey = captchaEditText.text.toString()
            .ifEmpty { if (captchaEditText.visibility == View.GONE) null else return }
        val tokenReceiver = TokenReceiverOfficial(login, password)
        try {
            val token = tokenReceiver.getToken(code, captchaSid, captchaKey)
            saveAuthInfo(token, VK_OFFICIAL.userAgent)
            onDestroyView()
        } catch (e: TokenException) {
            when (e.code) {
                TokenExceptionType.REGISTRATION_ERROR -> {
                    errorMessage.text = e.message
                    errorMessage.visibility = View.VISIBLE
                }
                TokenExceptionType.TWO_FA_REQ -> {
                    errorMessage.text = "Было отправлено смс с кодом подтверждения"
                    errorMessage.visibility = View.VISIBLE
                    codeEditText.visibility = View.VISIBLE
                }
                TokenExceptionType.TWO_FA_ERR -> {
                    errorMessage.text = "Ошибка двухфакторной аунтификации, попробуйте снова."
                    errorMessage.visibility = View.VISIBLE
                    hideElement()
                }
                TokenExceptionType.REQUEST_ERR -> {
                    errorMessage.text = "Ошибка запроса, попробуйте снова."
                    errorMessage.visibility = View.VISIBLE
                    hideElement()
                }
                TokenExceptionType.NEED_CAPTCHA -> {
                    captchaSid = e.captchaSid
                    errorMessage.text = e.message
                    errorMessage.visibility = View.VISIBLE
                    ImageFun().setImage(captchaImage, e.captchaImg)
                    captchaLayout.visibility = View.VISIBLE
                }
                TokenExceptionType.TOKEN_NOT_RECEIVED -> {
                    errorMessage.text = "Токен небыл отправлен"
                    errorMessage.visibility = View.VISIBLE
                    hideElement()
                }
            }
        }

    }

    private fun FragmentAuthorizationBinding.hideElement() {
        codeEditText.visibility = View.GONE
        captchaLayout.visibility = View.GONE
    }

    private fun saveAuthInfo(token: String, userAgent: String) {

        val preferences = requireActivity()
            .getSharedPreferences(getString(R.string.authorization_info), Context.MODE_PRIVATE)

        if (preferences != null) {
            with(preferences.edit()) {
                putString(getString(R.string.token_key), token)
                putString(getString(R.string.user_agent), userAgent)
                apply()
            }
        }
    }
}