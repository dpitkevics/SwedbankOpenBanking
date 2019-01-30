package com.dproductions.sob.structures.consents

data class ConsentAccess(
    var accounts: String = "null",
    var allPsd2: String = "null",
    var availableAccounts: String = "allAccounts",
    var balances: String = "null",
    var transactions: String = "null"
) {

    companion object {

        fun createDefault(): ConsentAccess {
            return ConsentAccess()
        }

    }

}