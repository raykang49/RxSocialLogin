package com.github.windsekirun.rxsociallogin.github

import android.annotation.SuppressLint
import com.github.windsekirun.rxsociallogin.SocialLogin
import com.github.windsekirun.rxsociallogin.intenal.net.OkHttpHelper
import com.github.windsekirun.rxsociallogin.intenal.oauth.BaseOAuthActivity
import com.github.windsekirun.rxsociallogin.intenal.oauth.OAuthWebViewClient
import com.github.windsekirun.rxsociallogin.intenal.oauth.clearCookies
import com.github.windsekirun.rxsociallogin.model.SocialType
import kotlinx.android.synthetic.main.activity_oauth.*

class GithubOAuthActivity : BaseOAuthActivity() {
    private val githubConfig: GithubConfig by lazy { SocialLogin.getConfig(SocialType.GITHUB) as GithubConfig }

    @SuppressLint("SetJavaScriptEnabled")
    override fun init() {
        var url = "${GithubOAuthConstants.GITHUB_URL}?client_id=${githubConfig.clientId}"

        if (githubConfig.scopeList.isNotEmpty()) {
            val scope = githubConfig.scopeList.joinToString(",")
            url += scope
        }

        if (githubConfig.clearCookies) {
            clearCookies()
        }

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = OAuthWebViewClient {
            requestOAuthToken(it)
        }

        webView.loadUrl(url)

        setToolbar(githubConfig.activityTitle)
    }

    private fun requestOAuthToken(code: String) {
        val formArray = arrayOf("client_id" to githubConfig.clientId,
                "client_secret" to githubConfig.clientSecret, "code" to code)

        val header = "Accept" to "application/json"

        disposable = OkHttpHelper.post(GithubOAuthConstants.GITHUB_OAUTH, header, formArray).requestAccessToken()
    }
}