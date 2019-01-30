package com.dproductions.sob.structures.consents

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Consent(
    var consentId: String = "",
    var consentStatus: String = ""
) {

    companion object {

        fun from(consentId: String): Consent {
            return Consent(
                consentId
            )
        }

    }

    class Deserializer : ResponseDeserializable<Consent> {

        override fun deserialize(content: String): Consent {
            return Gson().fromJson(content, Consent::class.java)
        }

    }

}
