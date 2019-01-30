package com.dproductions.sob.helpers

import com.dproductions.sob.structures.consents.Consent
import com.dproductions.sob.structures.credentials.OauthCredentials
import java.util.*

abstract class ApiHelper(private val isProduction: Boolean) {

    private val baseUrl = "https://psd2.api.swedbank.com"

    private val environmentPrefix: String
        get() = if (isProduction) { "v1" } else { "sandbox/v1" }

    protected abstract val endpoint: String

    protected fun getOauthHelperEndpoint(): String {
        return "$baseUrl/psd2"
    }

    protected fun getConsentsHelperEndpoint(): String {
        return "$baseUrl/$environmentPrefix/consents"
    }

    protected fun getAccountsHelperEndpoint(): String {
        return "$baseUrl/$environmentPrefix/accounts"
    }

    protected fun getAuthenticatedHeaders(oauthCredentials: OauthCredentials, consent: Consent): Map<String, Any> {
        return mapOf(
            "Authorization" to "Bearer ${oauthCredentials.accessToken}",
            "Consent-ID" to consent.consentId,
            "X-Request-ID" to UUID.randomUUID().toString(),
            "Date" to Date().toString()
        )
    }

}
