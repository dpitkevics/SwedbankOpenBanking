package com.dproductions.sob.structures.accounts

import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.lang.reflect.Type

@JsonAdapter(BalanceDeserializer::class)
data class Balance(
    var amount: Amount = Amount(),
    var date: DateTime = DateTime()
)

class BalanceDeserializer : JsonDeserializer<Balance> {

    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Balance {
        val jsonObject = json as JsonObject
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd")

        return Balance(
            Gson().fromJson(jsonObject.get("amount"), Amount::class.java),
            DateTime.parse(jsonObject.get("date").asString, formatter)
        )
    }

}
