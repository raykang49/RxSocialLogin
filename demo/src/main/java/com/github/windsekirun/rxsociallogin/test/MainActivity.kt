package com.github.windsekirun.rxsociallogin.test

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.windsekirun.rxsociallogin.RxSocialLogin
import com.github.windsekirun.rxsociallogin.facebook.FacebookLogin
import com.github.windsekirun.rxsociallogin.github.GithubLogin
import com.github.windsekirun.rxsociallogin.google.GoogleLogin
import com.github.windsekirun.rxsociallogin.kakao.KakaoLogin
import com.github.windsekirun.rxsociallogin.line.LineLogin
import com.github.windsekirun.rxsociallogin.linkedin.LinkedinLogin
import com.github.windsekirun.rxsociallogin.model.LoginResultItem
import com.github.windsekirun.rxsociallogin.naver.NaverLogin
import com.github.windsekirun.rxsociallogin.twitter.TwitterLogin
import com.github.windsekirun.rxsociallogin.wordpress.WordpressLogin
import com.github.windsekirun.rxsociallogin.yahoo.YahooLogin
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.android.synthetic.main.activity_main.*
import pyxis.uzuki.live.richutilskt.utils.getKeyHash


class MainActivity : AppCompatActivity() {
    private lateinit var kakaoLogin: KakaoLogin
    private lateinit var facebookLogin: FacebookLogin
    private lateinit var naverLogin: NaverLogin
    private lateinit var lineLogin: LineLogin
    private lateinit var twitterLogin: TwitterLogin
    private lateinit var googleLogin: GoogleLogin
    private lateinit var githubLogin: GithubLogin
    private lateinit var linkedinLogin: LinkedinLogin
    private lateinit var wordpressLogin: WordpressLogin
    private lateinit var yahooLogin: YahooLogin
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(MainActivity::class.java.simpleName, "KeyHash: ${getKeyHash()}")

        // optional, if you happen 'UndeliverableException', use this methods.
        // warning, this value is global handler.
        // you can see wiki at https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling
        RxJavaPlugins.setErrorHandler { e -> }

        kakaoLogin = KakaoLogin(this)
        facebookLogin = FacebookLogin(this)
        naverLogin = NaverLogin(this)
        lineLogin = LineLogin(this)
        twitterLogin = TwitterLogin(this)
        googleLogin = GoogleLogin(this)
        githubLogin = GithubLogin(this)
        linkedinLogin = LinkedinLogin(this)
        wordpressLogin = WordpressLogin(this)
        yahooLogin = YahooLogin(this)

        val consumer = Consumer<LoginResultItem> {
            val typeStr = it.toString()
            Log.d(MainActivity::class.java.simpleName, "onNext: typeStr: $typeStr")
            txtResult.text = typeStr
        }

        val error = Consumer<Throwable> {
            Log.d(MainActivity::class.java.simpleName, "onError: ${it.message}")
        }

        // warning, do this in MainThread.
        val kakaoDisposable = RxSocialLogin.kakao(kakaoLogin)
                .subscribe(consumer, error)

        val facebookDisposable = RxSocialLogin.facebook(facebookLogin)
                .subscribe(consumer, error)

        val naverDisposable = RxSocialLogin.naver(naverLogin)
                .subscribe(consumer, error)

        val lineDisposable = RxSocialLogin.line(lineLogin)
                .subscribe(consumer, error)

        val twitterDisposable = RxSocialLogin.twitter(twitterLogin)
                .subscribe(consumer, error)

        val googleDisposable = RxSocialLogin.google(googleLogin)
                .subscribe(consumer, error)

        val githubDisposable = RxSocialLogin.github(githubLogin)
                .subscribe(consumer, error)

        val linkedinDisposable = RxSocialLogin.linkedin(linkedinLogin)
                .subscribe(consumer, error)

        val wordpressDisposable = RxSocialLogin.wordpress(wordpressLogin)
                .subscribe(consumer, error)

        val yahooDisposable = RxSocialLogin.yahoo(yahooLogin)
                .subscribe(consumer, error)

        compositeDisposable.add(kakaoDisposable)
        compositeDisposable.add(facebookDisposable)
        compositeDisposable.add(naverDisposable)
        compositeDisposable.add(lineDisposable)
        compositeDisposable.add(twitterDisposable)
        compositeDisposable.add(googleDisposable)
        compositeDisposable.add(githubDisposable)
        compositeDisposable.add(linkedinDisposable)
        compositeDisposable.add(wordpressDisposable)
        compositeDisposable.add(yahooDisposable)

        btnKakao.setOnClickListener {
            kakaoLogin.onLogin()
        }

        btnFacebook.setOnClickListener {
            facebookLogin.onLogin()
        }

        btnNaver.setOnClickListener {
            naverLogin.onLogin()
        }

        btnLine.setOnClickListener {
            lineLogin.onLogin()
        }

        btnTwiitter.setOnClickListener {
            twitterLogin.onLogin()
        }

        btnGoogle.setOnClickListener {
            googleLogin.onLogin()
        }

        btnGithub.setOnClickListener {
            githubLogin.onLogin()
        }

        btnLinkedIn.setOnClickListener {
            linkedinLogin.onLogin()
        }

        btnWordpress.setOnClickListener {
            wordpressLogin.onLogin()
        }

        btnYahoo.setOnClickListener {
            yahooLogin.onLogin()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        kakaoLogin.onActivityResult(requestCode, resultCode, data)
        facebookLogin.onActivityResult(requestCode, resultCode, data)
        naverLogin.onActivityResult(requestCode, resultCode, data)
        lineLogin.onActivityResult(requestCode, resultCode, data)
        twitterLogin.onActivityResult(requestCode, resultCode, data)
        googleLogin.onActivityResult(requestCode, resultCode, data)
        githubLogin.onActivityResult(requestCode, resultCode, data)
        linkedinLogin.onActivityResult(requestCode, resultCode, data)
        wordpressLogin.onActivityResult(requestCode, resultCode, data)
        yahooLogin.onActivityResult(requestCode, resultCode, data)
    }
}
