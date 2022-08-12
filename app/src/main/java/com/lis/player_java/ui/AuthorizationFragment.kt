package com.lis.player_java.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.lis.player_java.vkaudiotoken.TokenReceiverOfficial
import android.widget.Toast
import com.lis.player_java.R
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.lis.player_java.tool.ImageFun
import com.lis.player_java.databinding.FragmentAuthorizationBinding
import com.lis.player_java.vkaudiotoken.TokenException
import com.lis.player_java.vkaudiotoken.TokenExceptionType
import com.lis.player_java.vkaudiotoken.network.VK_OFFICIAL
import kotlinx.coroutines.launch

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
        val login = usernameEditText.text.toString().ifEmpty {
            showToast(resources.getString(R.string.login_hint))
            return
        }
        val password = passwordEditText.text.toString().ifEmpty {
            showToast(resources.getString(R.string.password_hint))
            return }
        val code = codeEditText.text.toString()
            .ifEmpty { if (codeEditText.visibility == View.GONE) null else {
                showToast("code")
                return
            } }
        val captchaKey = captchaEditText.text.toString()
            .ifEmpty { if (captchaLayout.visibility == View.GONE) null else {
                showToast("captcha")
                return
            } }
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
                    errorMessage.text = resources.getString(R.string.sms_sent)
                    errorMessage.visibility = View.VISIBLE
                    codeEditText.visibility = View.VISIBLE
                }
                TokenExceptionType.TWO_FA_ERR -> {
                    errorMessage.text = resources.getString(R.string.two_fa_error)
                    errorMessage.visibility = View.VISIBLE
                    hideElement()
                }
                TokenExceptionType.REQUEST_ERR -> {
                    errorMessage.text = resources.getString(R.string.request_error)
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
                    errorMessage.text = resources.getString(R.string.token_not_sent)
                    errorMessage.visibility = View.VISIBLE
                    hideElement()
                }
            }
        }

    }

    private fun showToast(field: String) {
        Toast.makeText(requireContext(), "Поле: $field не заполненно", Toast.LENGTH_LONG).show()
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