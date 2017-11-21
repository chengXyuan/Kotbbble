package de.carey.kotbbble.api

import de.carey.kotbbble.app.Constants
import de.carey.kotbbble.entity.Comment
import de.carey.kotbbble.entity.Shot
import de.carey.kotbbble.entity.Token
import de.carey.kotbbble.entity.User
import io.reactivex.Flowable
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
    fun getShots(@Query("list") type: String,
                 @Query("sort") sort: String,
                 @Query("timeframe") timeFrame: String,
                 @Query("page") pageIndex: Int,
                 @Query("per_page") pageSize: Int = Constants.PAGE_SIZE): Flowable<List<Shot>>

    @GET("shots/{id}/comments")
    fun getComments(@Path("id") shotId: Int,
                    @Query("comments_sort") sort: String?,
                    @Query("page") pageIndex: Int,
                    @Query("per_page") pageSize: Int = Constants.PAGE_SIZE): Flowable<List<Comment>>

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