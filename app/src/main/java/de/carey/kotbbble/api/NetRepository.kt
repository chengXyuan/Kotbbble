package de.carey.kotbbble.api

import de.carey.kotbbble.entity.Token
import de.carey.kotbbble.entity.User
import io.reactivex.Flowable

class NetRepository private constructor() {

    private val mApiStore: ApiStore = ApiClient.instance.getApiStore()
    private val mAuthStore: ApiStore = ApiClient.instance.createWebsiteRetrofit()

    private object Holder {
        val Instance = NetRepository()
    }

    companion object {
        val instance = Holder.Instance
    }

    fun getAccessToken(code: String): Flowable<Token> = mAuthStore.getToken(code = code)

    fun getUserInfo(): Flowable<User> = mApiStore.getUserInfo()

    fun getShots(type: String, sort: String, time: String, pageIndex: Int)
            = mApiStore.getShots(type, sort, time, pageIndex)

    fun getComments(shotId: Int, sort: String?, pageIndex: Int)
            = mApiStore.getComments(shotId, sort, pageIndex)

}