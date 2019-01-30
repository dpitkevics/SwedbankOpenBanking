package com.dproductions.sob

import com.dproductions.sob.helpers.AccountsHelper
import com.dproductions.sob.helpers.ConsentsHelper
import com.dproductions.sob.structures.credentials.ApiCredentials
import com.dproductions.sob.structures.credentials.OauthCredentials
import com.dproductions.sob.helpers.OauthHelper
import com.dproductions.sob.structures.banks.SwedbankBic
import com.dproductions.sob.structures.consents.Consent

class SwedbankOpenBankingApi(clientId: String, clientSecret: String, private val bic: SwedbankBic, private val isProduction: Boolean) {

    private val apiCredentials: ApiCredentials = ApiCredentials(clientId, clientSecret)

    fun getAuthenticationHelper(): OauthHelper {
        return OauthHelper(apiCredentials, bic, isProduction)
    }

    fun getConsentsHelper(oauthCredentials: OauthCredentials): ConsentsHelper {
        return ConsentsHelper(oauthCredentials, bic, isProduction)
    }

    fun getAccountsHelper(oauthCredentials: OauthCredentials, consent: Consent): AccountsHelper {
        return AccountsHelper(oauthCredentials, consent, bic, isProduction)
    }

}
