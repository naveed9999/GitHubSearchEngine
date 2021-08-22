package com.nido.github.di.codemodules

import com.nido.github.feature.content.owner.ui.OwnerFragment
import com.nido.github.feature.content.repository.RepositoryFragment
import com.nido.github.feature.content.search.ui.SearchFragment
import com.nido.github.feature.content.user.ui.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeRepositoryFragment(): RepositoryFragment

    @ContributesAndroidInjector
    abstract fun contributeOwnerFragment(): OwnerFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment
}