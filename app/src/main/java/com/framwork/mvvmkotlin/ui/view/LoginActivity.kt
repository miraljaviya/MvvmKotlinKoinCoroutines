package com.framwork.mvvmkotlin.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.observe
import com.framwork.mvvmkotlin.R
import com.framwork.mvvmkotlin.base.BaseActivity
import com.framwork.mvvmkotlin.databinding.LoginActivityBinding
import com.framwork.mvvmkotlin.state.ScreenState
import com.framwork.mvvmkotlin.ui.viewModel.LoginActivityVM
import com.framwork.mvvmkotlin.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

/*
{
    "email": "eve.holt@reqres.in",
    "password": "cityslicka"
}*/

class LoginActivity : BaseActivity<LoginActivityVM, LoginActivityBinding>(R.layout.activity_login) {

    override val viewModel: LoginActivityVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (sharedPreferences.getBoolean(Constants.PREF_ISLOGIN, false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        viewModel.state().observe(this) { state ->
            renderState(state)
        }
        etPassword.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnLogin.performClick()
                true
            } else false
        }
    }

    private fun renderState(state: ScreenState) = state.run {
        when (state) {
            is ScreenState.LoginSuccess -> {
                sharedPreferencesEdit.putBoolean(Constants.PREF_ISLOGIN, true).apply()
                sharedPreferencesEdit.putString("token", state.response.token).apply()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            is ScreenState.LoginFailure -> {
                enableViews(true)
                Snackbar.make(root, state.error, Snackbar.LENGTH_SHORT).show()
            }
            is ScreenState.EmailValidationError -> {
                enableViews(true)
                tilEmail.error = state.error
            }
            is ScreenState.PasswordValidationError -> {
                enableViews(true)
                tilEmail.error = ""
                tilPassword.error = state.error
            }
            ScreenState.Loading -> {
                tilEmail.error = ""
                tilPassword.error = ""
                enableViews(false)
            }
        }
    }

    private fun enableViews(enable: Boolean) {
        btnLogin.isEnabled = enable
        etEmail.isEnabled = enable
        etPassword.isEnabled = enable
        progress.visibility = if (enable) View.INVISIBLE else View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        viewModel.state().removeObservers(this)
    }

}
