package com.dproductions.sob.structures.credentials

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class OauthCredentials(
    @SerializedName("access_token")
    var accessToken: String = "",

    @SerializedName("refresh_token")
    var refreshToken: String = "",

    @SerializedName("token_type")
    var tokenType: String = "",

    @SerializedName("expires_in")
    var expiresIn: Long = 0,

    @SerializedName("scope")
    var scope: String = ""
) {

    companion object {

        fun from(accessToken: String): OauthCredentials {
            return OauthCredentials(accessToken)
        }

    }

    class Deserializer : ResponseDeserializable<OauthCredentials> {

        override fun deserialize(content: String): OauthCredentials {
            return Gson().fromJson(content, OauthCredentials::class.java)
        }

    }

}
