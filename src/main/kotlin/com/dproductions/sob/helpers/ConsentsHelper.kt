package com.dproductions.sob.helpers

import com.dproductions.sob.exceptions.ConsentsException
import com.dproductions.sob.structures.banks.SwedbankBic
import com.dproductions.sob.structures.consents.Consent
import com.dproductions.sob.structures.consents.CreateConsentRequest
import com.dproductions.sob.structures.credentials.OauthCredentials
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.HttpException
import com.github.kittinunf.fuel.coroutines.awaitObject
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import io.mikael.urlbuilder.UrlBuilder
import java.util.*

class ConsentsHelper(private val oauthCredentials: OauthCredentials, private val bic: SwedbankBic, isProduction: Boolean) : ApiHelper(isProduction) {

    override val endpoint = getConsentsHelperEndpoint()

    suspend fun createConsent(createConsentRequest: CreateConsentRequest): Consent {
        val url = UrlBuilder
            .fromString(endpoint)
            .addParameter("bic", bic.value)

        val consent: Consent

        try {
            consent = url.toString()
                .httpPost()
                .body(Gson().toJson(createConsentRequest))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer ${oauthCredentials.accessToken}")
                .header("X-Request-ID", UUID.randomUUID().toString())
                .header("Date", Date().toString())
                .awaitObject(Consent.Deserializer())
        } catch (exception: Exception) {
            when (exception){
                is HttpException -> throw ConsentsException(exception.message)
                is FuelError -> throw ConsentsException(exception.message)
                else -> throw ConsentsException("Generic exception received with message: ${exception.message}")
            }
        }

        return consent
    }

}
