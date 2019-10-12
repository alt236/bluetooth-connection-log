package uk.co.alt236.btconnectionlog.sharedprefs.keymatcher

import android.content.SharedPreferences

interface KeyMatcher {
    fun matches(prefs: SharedPreferences, candidate: String): Boolean
}