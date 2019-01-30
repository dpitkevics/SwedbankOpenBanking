package com.dproductions.sob.structures.accounts

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import java.lang.reflect.Type

@JsonAdapter(AccountTypeSerializer::class)
enum class AccountType(val value: String) {
    Current("CURRENT"),
}

class AccountTypeSerializer : JsonDeserializer<AccountType> {

    override fun deserialize(json: JsonElement?, type: Type, context: JsonDeserializationContext): AccountType {
        val jsonAccountType = json.toString()

        for (accountType in AccountType.values()) {
            if (accountType.value == jsonAccountType) {
                return accountType
            }
        }

        return AccountType.Current
    }

}
