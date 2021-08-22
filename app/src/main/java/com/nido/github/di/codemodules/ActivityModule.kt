package com.nido.github.di.codemodules

import com.nido.github.feature.authentication.common.AuthenticationActivity
import com.nido.github.feature.content.common.MainActivity
import com.nido.github.feature.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [AuthenticationFragmentModule::class])
    abstract fun contributeAuthenticationActivity(): AuthenticationActivity
}