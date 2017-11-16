package de.carey.kotbbble.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import de.carey.kotbbble.R
import kotlinx.android.synthetic.main.activity_web.*


open class WebActivity : AppCompatActivity() {

    companion object {
        val WEB_TITLE = "web_title"
        val WEB_LOAD_URL = "web_load_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        iv_back.setOnClickListener { finish() }
        tv_title.text = intent.getStringExtra(WEB_TITLE)

        initWebView()
    }

    @Suppress("OverridingDeprecatedMember")
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val settings = web_view.settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS

        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                tv_title.text = view?.title
            }
        }
        web_view.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    progress_bar.visibility = View.GONE
                } else {
                    if (progress_bar.visibility == View.GONE) {
                        progress_bar.visibility = View.VISIBLE
                    }
                    progress_bar.progress = newProgress
                }
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                tv_title.text = title
            }
        }
        web_view.loadUrl(intent.getStringExtra(WEB_LOAD_URL))
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
            return
        }
        super.onBackPressed()
    }

}
