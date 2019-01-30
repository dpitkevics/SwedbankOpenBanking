package com.dproductions.sob.structures.accounts

import com.dproductions.sob.structures.currencies.Currency
import com.dproductions.sob.structures.currencies.CurrencyHelper
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.JsonAdapter
import java.lang.reflect.Type

@JsonAdapter(AmountDeserializer::class)
data class Amount(
    var currency: Currency = Currency.Unknown,
    var amount: Float = 0.0f
)

class AmountDeserializer : JsonDeserializer<Amount> {

    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Amount {
        val jsonObject = json as JsonObject

        return Amount(
            CurrencyHelper.fromValue(jsonObject.get("currency").asString),
            jsonObject.get("content").asFloat
        )
    }

}
