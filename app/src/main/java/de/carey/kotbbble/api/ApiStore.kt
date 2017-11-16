package de.carey.kotbbble.api

import de.carey.kotbbble.application.Constants
import de.carey.kotbbble.entity.Token
import de.carey.kotbbble.entity.User
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.*


interface ApiStore {

    @FormUrlEncoded
    @POST("/oauth/token")
    fun getToken(@Field("client_id") clientId: String = Constants.CLIENT_ID,
                 @Field("client_secret") clientSecret: String = Constants.CLIENT_SECRET,
                 @Field("code") code: String,
                 @Field("redirect_uri") redirectUri: String = Constants.REDIRECT_URI
    ): Flowable<Token>

    @GET("user")
    fun getUserInfo(): Flowable<User>

    @GET("shots")
    fun getShots(): Flowable<ResponseBody>

    @GET("shots/{id}/comments")
    fun getComments()

    @POST("shots/{id}/like")
    fun likeShot()

    @GET("shots/{id}/like")
    fun checkIfLikeShot()

    @DELETE("shots/{id}/like")
    fun unlikeShot()

    @POST("shots/{id}/comments")
    fun createComment()

    @GET("user/likes")
    fun getMyLikes()

    @GET("user/buckets")
    fun getMyBuckets()

    @POST("buckets")
    fun createBucket()

    @DELETE("buckets/{id}")
    fun deleteBucket()

    @PUT("buckets/{id}")
    fun modifyBucket()

    @GET("buckets/{id}/shots")
    fun getBucketShots()

    @DELETE("buckets/{id}/shots")
    fun removeShotFromBucket()

    @PUT("buckets/{id}/shots")
    fun addShot2Bucket()

    @GET("{user}/{id}/shots")
    fun getUserShot()

    @GET("user/following/{id}")
    fun checkIfFollowingUser()

    @PUT("users/{id}/follow")
    fun followUser()

    @DELETE("users/{id}/follow")
    fun unFollowUser()
}