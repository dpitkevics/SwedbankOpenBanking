package com.dproductions.sob.helpers

import com.dproductions.sob.structures.banks.SwedbankBic
import com.dproductions.sob.structures.credentials.ApiCredentials
import com.dproductions.sob.structures.credentials.OauthCredentials
import com.dproductions.sob.exceptions.OauthException
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.HttpException
import com.github.kittinunf.fuel.coroutines.awaitObject
import com.github.kittinunf.fuel.httpPost
import io.mikael.urlbuilder.UrlBuilder

class OauthHelper(private val apiCredentials: ApiCredentials, private val bic: SwedbankBic, private val isProduction: Boolean) : ApiHelper(isProduction) {

    override val endpoint = getOauthHelperEndpoint()

    fun getAuthenticationUrl(callbackUrl: String, state: String? = null): String {
        val url = UrlBuilder
            .fromString("$endpoint/authorize")
            .addParameter("bic", bic.value)
            .addParameter("state", state)
            .addParameter("client_id", apiCredentials.clientId)
            .addParameter("redirect_uri", callbackUrl)
            .addParameter("response_type", "code")
            .addParameter("scope", getAuthenticationScope())

        return url.toString()
    }

    private fun getAuthenticationScope(): String {
        if (isProduction) {
            return "PSD2"
        }

        return "PSD2sandbox"
    }

    suspend fun getOauthCredentials(callbackUrl: String, authenticationCode: String, state: String? = null): OauthCredentials {
        val url = UrlBuilder
            .fromString("$endpoint/token")
            .addParameter("state", state)
            .addParameter("client_id", apiCredentials.clientId)
            .addParameter("client_secret", apiCredentials.clientSecret)
            .addParameter("redirect_uri", callbackUrl)
            .addParameter("grant_type", "authorization_code")
            .addParameter("code", authenticationCode)

        val credentials: OauthCredentials

        try {
             credentials = url.toString()
                .httpPost()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .awaitObject(OauthCredentials.Deserializer())
        } catch (exception: Exception) {
            when (exception){
                is HttpException -> throw OauthException(exception.message)
                is FuelError -> throw OauthException(exception.message)
                else -> throw OauthException("Generic exception received with message: ${exception.message}")
            }
        }

        return credentials
    }

}
