package com.dproductions.sob.structures.accounts

import com.dproductions.sob.structures.currencies.Currency
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

data class Account(
    var id: String = "",
    var iban: String = "",
    var accountType: AccountType = AccountType.Current,
    var currency: Currency = Currency.Unknown
) {

    class Deserializer : ResponseDeserializable<Account> {

        override fun deserialize(content: String): Account {
            return Gson().fromJson(content, Account::class.java)
        }

    }

    class ArrayDeserializer : ResponseDeserializable<Array<Account>> {

        override fun deserialize(content: String): Array<Account> {
            return Gson().fromJson(content, Array<Account>::class.java)
        }

    }

    class ObjectDeserializer : ResponseDeserializable<Array<Account>> {

        override fun deserialize(content: String): Array<Account>? {
            val json = JsonParser().parse(content) as JsonObject
            val accountList = json.get("accountList")
            val jsonArray = Gson().toJson(accountList)

            return Gson().fromJson(jsonArray, Array<Account>::class.java)
        }

    }

}
