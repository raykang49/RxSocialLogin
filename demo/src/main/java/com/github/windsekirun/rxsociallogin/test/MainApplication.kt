package com.github.windsekirun.rxsociallogin.test

import android.app.Application

import com.github.windsekirun.rxsociallogin.SocialLogin
import com.github.windsekirun.rxsociallogin.model.SocialType
import com.github.windsekirun.rxsociallogin.facebook.FacebookConfig
import com.github.windsekirun.rxsociallogin.kakao.KakaoConfig


/**
 * SocialLogin
 * Class: MainApplication
 * Created by Pyxis on 7/2/18.
 *
 *
 * Description:
 */

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SocialLogin.init(this)

        val facebookConfig = FacebookConfig.Builder()
                .setApplicationId(getString(R.string.facebook_api_key))
                .setRequireEmail()
                .setBehaviorOnCancel()
                .build()

        SocialLogin.addType(SocialType.FACEBOOK, facebookConfig)

        val kakaoConfig = KakaoConfig.Builder()
                .setRequireEmail()
                .setRequireAgeRange()
                .setRequireBirthday()
                .setRequireEmail()
                .setRequireGender()
                .build()

        SocialLogin.addType(SocialType.KAKAO, kakaoConfig)
    }
}