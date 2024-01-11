package com.colddelight.data.util

import com.colddelight.datastore.datasource.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginHelperImpl @Inject constructor(
    private val tokenDataSource: UserPreferencesDataSource
) : LoginHelper {

    override val isLogin: Flow<Boolean>
        get() = tokenDataSource.token.map {
            it.isNotEmpty()
        }

}