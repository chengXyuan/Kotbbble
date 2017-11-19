package de.carey.kotbbble.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import de.carey.kotbbble.app.App


class SPUtils private constructor() {

    private val mPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.instance)

    private object Holder {
        val INSTANCE = SPUtils()
    }

    companion object {
        val instance = Holder.INSTANCE
    }

    fun putString(key: String, value: String?) = mPreferences.edit().putString(key, value).apply()

    fun getString(key: String, defValue: String?): String? = mPreferences.getString(key, defValue)
}