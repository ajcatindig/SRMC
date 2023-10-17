package com.example.srmc.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.srmc.core.session.SessionManager

class SessionManagerImpl(private val sharedPreferences : SharedPreferences) : SessionManager {
    override fun saveToken(token: String?) {
        sharedPreferences.edit(commit = true) {
            putString(KEY_TOKEN, token)
        }
    }

    override fun getToken(): String? = sharedPreferences.getString(KEY_TOKEN, null)

    override fun saveEmail(email : String?)
    {
        sharedPreferences.edit(commit = true) {
            putString(EMAIL, email)
        }
    }

    override fun getEmail() : String? = sharedPreferences.getString(EMAIL, null)

    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val EMAIL = "login_email"
    }
}

object SRMCSharedPreferencesFactory {
    private const val FILE_NAME_SESSION_PREF = "auth_shared_pref"

    fun sessionPreferences(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
                context ,
                FILE_NAME_SESSION_PREF ,
                MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build() ,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV ,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}