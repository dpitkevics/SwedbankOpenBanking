package com.dproductions.sob.structures.accounts

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class AccountBalances(
    var balances: Array<BalanceList> = emptyArray()
) {

    class Deserializer : ResponseDeserializable<AccountBalances> {

        override fun deserialize(content: String): AccountBalances {
            return Gson().fromJson(content, AccountBalances::class.java)
        }

    }

}
