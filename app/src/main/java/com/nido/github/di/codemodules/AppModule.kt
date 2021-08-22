package com.nido.github.di.codemodules

import com.nido.github.di.modules.PersistenceModule
import com.nido.github.di.modules.RemoteModule
import com.nido.github.di.modules.ViewModelModule
import dagger.Module

@Module(
    includes = [
        PersistenceModule::class,
        RemoteModule::class,
        ViewModelModule::class
    ]
)
interface AppModule