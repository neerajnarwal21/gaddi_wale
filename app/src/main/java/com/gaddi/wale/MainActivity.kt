package com.gaddi.wale

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.loadUrl("http://www.gaddiwale.in")
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (logoIV.visibility == View.GONE) {
                    progressPB.visibility = View.VISIBLE
                }
                return super.shouldOverrideUrlLoading(view, request)

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressPB.visibility = View.GONE
            }
            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                logoIV.visibility = View.GONE
                swipeRL.isRefreshing = false
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressPB.progress = newProgress
                progressPB.secondaryProgress = (Random().nextFloat() * newProgress + newProgress).toInt()
                }
        }
        swipeRL.setOnRefreshListener { webView.reload() }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else
            showExit()
    }

    fun showExit() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to exit ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ -> finishAffinity() }
            .setNegativeButton("No", null)
        val alert = builder.create()
        alert.show()
    }
}
