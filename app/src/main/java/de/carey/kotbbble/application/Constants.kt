package de.carey.kotbbble.application

interface Constants {
    companion object {
        val CLIENT_ID = "3542445d22853ca416185b1274bec04d2e74aaeb73b96e06ab0c509b262f9ae2"
        val CLIENT_SECRET = "dde630a4996682ffc2c0bd8c36473d530cd092d16d759708f4d7fb18939b942a"
        val CLIENT_ACCESS_TOKEN = "5dc2d0f99753f94cfe69350b183465b89f3df9e2e0550081e011458c7db1eb3a"

        val REDIRECT_URI = "https://www.google.com/"
        val OAUTH_URL = "https://dribbble.com/oauth/authorize?client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&scope=public write comment upload"

        val BUGLY_ID = ""

        val SP_ACCESS_TOKEN = "sp_access_token"
    }
}