package com.dproductions.sob.structures.currencies

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import java.lang.reflect.Type

@JsonAdapter(CurrencySerializer::class)
enum class Currency(val value: String) {
    Euro("EUR"),
    Unknown("XXX"),
}

object CurrencyHelper {

    fun fromValue(value: String): Currency {
        for (currency in Currency.values()) {
            if (currency.value == value) {
                return currency
            }
        }

        return Currency.Unknown
    }

}

class CurrencySerializer : JsonDeserializer<Currency> {

    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Currency {
        return CurrencyHelper.fromValue(json.toString())
    }

}
