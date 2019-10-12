package uk.co.alt236.btconnectionlog.sharedprefs.keymatcher

import android.content.SharedPreferences

class AnyKeyMatcher : KeyMatcher {
    override fun matches(prefs: SharedPreferences, candidate: String): Boolean {
        return true
    }

    companion object {
        @JvmStatic
        val ANY_KEY = "${AnyKeyMatcher::class.java.name}.ANY KEY"
    }
}