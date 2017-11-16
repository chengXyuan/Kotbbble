package de.carey.kotbbble.entity

import java.io.Serializable

data class Token(val access_token: String,
                 val token_type: String,
                 val scope: String,
                 val created_at: Long) : Serializable

data class User(val id: Int,
                val name: String,
                val username: String,
                val html_url: String,
                val avatar_url: String,
                val bio: String,
                val location: String?,
                val links: Links,
                val buckets_count: Int,
                val comments_received_count: Int,
                val followers_count: Int,
                val followings_count: Int,
                val likes_count: Int,
                val likes_received_count: Int,
                val projects_count: Int,
                val rebounds_received_count: Int,
                val shots_count: Int,
                val teams_count: Int,
                val can_upload_shot: Boolean,
                val type: String,
                val pro: Boolean,
                val buckets_url: String,
                val followers_url: String,
                val following_url: String,
                val likes_url: String,
                val projects_url: String,
                val shots_url: String,
                val teams_url: String,
                val created_at: String,
                val updated_at: String)

data class Links(val web: String, val twitter: String)