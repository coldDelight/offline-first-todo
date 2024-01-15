package com.colddelight.mtodo

import com.colddelight.mtodo.ui.MTodoApp
import javax.inject.Inject
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.colddelight.data.util.LoginHelper

import com.colddelight.designsystem.theme.MTodoTheme
import com.colddelight.login.LoginScreen
import com.colddelight.mtodo.ui.rememberMtodoAppState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var loginHelper: LoginHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MTodoTheme {
                val isLogin by loginHelper.isLogin.collectAsState(true)
                if (!isLogin) {
                    LoginScreen()
                } else {
                    MTodoApp()
                }
            }
        }
    }
}

