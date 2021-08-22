package com.nido.github.di.codemodules

import com.nido.github.feature.authentication.ui.AuthenticationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthenticationFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeAuthenticate(): AuthenticationFragment
}