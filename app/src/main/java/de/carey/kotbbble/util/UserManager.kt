package de.carey.kotbbble.util

import de.carey.kotbbble.entity.User

class UserManager private constructor() {

    private object Holder {
        val INSTANCE = UserManager()
    }

    companion object {
        val instance = Holder.INSTANCE
        private val KEY_CURRENT_USER = "current_user"
        private var user: User? = null
    }

    fun getUser(): User? {
        if (user == null) {
            val jsonStr = SPUtils.instance.getString(KEY_CURRENT_USER, null)
            user = JsonUtil.fromJson(jsonStr, User::class.java)
        }
        return user
    }

    fun setUser(user: User) {
        UserManager.Companion.user = user
        SPUtils.instance.putString(KEY_CURRENT_USER, JsonUtil.toJson(user))
    }

    fun clearUser() {
        user = null
        SPUtils.instance.putString(KEY_CURRENT_USER, null)
    }
}