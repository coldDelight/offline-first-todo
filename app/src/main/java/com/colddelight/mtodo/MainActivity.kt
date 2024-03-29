package com.colddelight.mtodo

import com.colddelight.mtodo.ui.MTodoApp
import javax.inject.Inject
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.colddelight.data.worktask.SyncTask
import com.colddelight.data.util.LoginHelper
import com.colddelight.designsystem.theme.MTodoTheme
import com.colddelight.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var loginHelper: LoginHelper
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContent {
            MTodoTheme {
//                MTodoApp()
                val isLogin by loginHelper.isLogin.collectAsState(false)
                if (!isLogin) {
                    LoginScreen()
                } else {
                    SyncTask.initialize(this)
                    MTodoApp()
                }
            }
        }
    }
}

