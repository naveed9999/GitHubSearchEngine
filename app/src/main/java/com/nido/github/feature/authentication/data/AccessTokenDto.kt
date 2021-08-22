package com.nido.github.feature.authentication.data

import com.nido.github.feature.authentication.api.AuthenticationResponse

object AccessTokenDto {
    fun fromResponse(response: AuthenticationResponse) = AccessToken(response.accessToken)
}