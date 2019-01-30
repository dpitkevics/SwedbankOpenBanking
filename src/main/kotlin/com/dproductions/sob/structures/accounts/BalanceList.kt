package com.dproductions.sob.structures.accounts

import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import java.lang.reflect.Type

@JsonAdapter(BalanceListDeserializer::class)
data class BalanceList(
    var balanceList: MutableMap<String, Balance> = mutableMapOf()
)

class BalanceListDeserializer : JsonDeserializer<BalanceList> {

    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): BalanceList {
        val jsonObject = json as JsonObject

        val balanceList = BalanceList()

        for (key in jsonObject.keySet()) {
            val balanceJson = jsonObject.getAsJsonObject(key)
            val balance = Gson().fromJson(balanceJson, Balance::class.java)

            balanceList.balanceList[key] = balance
        }

        return balanceList
    }

}
