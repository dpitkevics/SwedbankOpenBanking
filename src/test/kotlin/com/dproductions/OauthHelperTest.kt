package com.dproductions

import com.dproductions.sob.SwedbankOpenBankingApi
import com.dproductions.sob.structures.banks.SwedbankBic
import org.junit.Test
import kotlin.test.assertEquals

class OauthHelperTest {

    @Test
    fun testGetAuthenticationUrl() {
        val api = SwedbankOpenBankingApi("test", "test", SwedbankBic.LatviaSandbox, false)
        val authHelper = api.getAuthenticationHelper()

        assertEquals(
            "https://psd2.api.swedbank.com/psd2/authorize?bic=SANDLV22&state=randomstate&client_id=test&redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2Foauth%2Fcallback&response_type=code&scope=PSD2sandbox",
            authHelper.getAuthenticationUrl(
                callbackUrl = "http://127.0.0.1:8080/oauth/callback",
                state = "randomstate"
            )
        )
    }

}
