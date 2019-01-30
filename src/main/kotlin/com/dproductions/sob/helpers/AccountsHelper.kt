package com.dproductions.sob.helpers

import com.dproductions.sob.exceptions.AccountsException
import com.dproductions.sob.structures.accounts.Account
import com.dproductions.sob.structures.accounts.AccountBalances
import com.dproductions.sob.structures.banks.SwedbankBic
import com.dproductions.sob.structures.consents.Consent
import com.dproductions.sob.structures.credentials.OauthCredentials
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.HttpException
import com.github.kittinunf.fuel.coroutines.awaitObject
import com.github.kittinunf.fuel.httpGet
import io.mikael.urlbuilder.UrlBuilder

class AccountsHelper(private val oauthCredentials: OauthCredentials, private val consent: Consent, private val bic: SwedbankBic, isProduction: Boolean) : ApiHelper(isProduction) {

    override val endpoint = getAccountsHelperEndpoint()

    suspend fun getAccounts(): Array<Account> {
        val url = UrlBuilder
            .fromString(endpoint)
            .addParameter("bic", bic.value)

        val accounts: Array<Account>

        try {
            accounts = url.toString()
                .httpGet()
                .header(getAuthenticatedHeaders(oauthCredentials, consent))
                .awaitObject(Account.ObjectDeserializer())
        } catch (exception: Exception) {
            throw getException(exception)
        }

        return accounts
    }

    suspend fun getAccountBalances(accountId: String): AccountBalances {
        val url = UrlBuilder
            .fromString("$endpoint/$accountId/balances")
            .addParameter("bic", bic.value)

        val balanceList: AccountBalances

        try {
            balanceList = url.toString()
                .httpGet()
                .header(getAuthenticatedHeaders(oauthCredentials, consent))
                .awaitObject(AccountBalances.Deserializer())
        } catch (exception: Exception) {
            throw getException(exception)
        }

        return balanceList
    }

    private fun getException(exception: Exception): AccountsException {
        return when (exception){
            is HttpException -> AccountsException(exception.message)
            is FuelError -> AccountsException(exception.message)
            else -> throw AccountsException("Generic exception received with message: ${exception.message}")
        }
    }

}
