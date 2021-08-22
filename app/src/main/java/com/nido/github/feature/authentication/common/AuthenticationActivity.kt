package com.nido.github.feature.authentication.common

import android.os.Bundle
import com.nido.github.R
import com.nido.github.base.BaseActivity

class AuthenticationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }
}
