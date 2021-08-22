package com.nido.github.feature.authentication.data

import com.nido.github.data.BaseDataSource
import com.nido.github.feature.authentication.api.AuthenticationApi
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor(private val api: AuthenticationApi) :
    BaseDataSource() {

    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        oAuthCode: String,
        redirectUrl: String
    ) = getResult {
        api.getAccessToken(
            ACCESS_TOKEN_URL,
            clientId,
            clientSecret,
            oAuthCode,
            redirectUrl
        )
    }

    companion object {
        private const val ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token"
    }
}