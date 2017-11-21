package de.carey.kotbbble.ui.activity

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import de.carey.kotbbble.app.Constants
import kotlinx.android.synthetic.main.activity_web.*

class AuthorizeActivity : WebActivity() {

    @Suppress("OverridingDeprecatedMember")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web_view.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.contains("code")) {
                    val code = url.replace("${Constants.REDIRECT_URI}?code=", "")
                    val intent = Intent()
                    intent.putExtra(LoginActivity.EXTRA_AUTHORIZE_CODE, code)
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    view.loadUrl(url)
                }
                return true
            }
        })
    }
}
