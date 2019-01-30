package com.dproductions.sob.structures.consents

import org.joda.time.DateTime

data class CreateConsentRequest(
    var access: ConsentAccess = ConsentAccess(),
    var combinedServiceIndicator: Boolean = false,
    var frequencyPerDay: String = "1",
    var recurringIndicator: Boolean = false,
    var validUntil: String = ""
) {

    companion object {

        fun createDefault(consentAccess: ConsentAccess, validUntil: DateTime): CreateConsentRequest {
            return CreateConsentRequest(
                access = consentAccess,
                validUntil = validUntil.toString("yyyy-MM-dd")
            )
        }

    }

}
