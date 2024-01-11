package com.colddelight.mtodo

import com.colddelight.mtodo.ui.MTodoApp
import javax.inject.Inject
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.colddelight.designsystem.theme.MTodoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    @Inject
//    lateinit var loginHelper: LoginHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MTodoTheme {
                MTodoApp()
            }
        }
    }
}

