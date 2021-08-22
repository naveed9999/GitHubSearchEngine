package com.nido.github.feature.authentication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.nido.github.R
import com.nido.github.base.BaseFragment
import com.nido.github.data.ResultData
import com.nido.github.databinding.FragmentAuthenticationBinding
import com.nido.github.di.helper.injectViewModel
import com.nido.github.feature.content.common.MainActivity

class AuthenticationFragment : BaseFragment() {

    private lateinit var redirectUrl: String
    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAuthenticationBinding.inflate(inflater, container, false).apply {
            viewModel = injectViewModel(viewModelFactory)
            redirectUrl =
                "${getString(R.string.manifest_scheme)}://${getString(R.string.manifest_host)}"

            authenticateWithGithub.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(viewModel.getOAuthUrl(redirectUrl))
                    )
                )
            }

            continueWithoutAuthentication.setOnClickListener {
                viewModel.removeAccessToken()
                nextActivity(MainActivity::class.java)
            }
        }.root
    }

    override fun onResume() {
        super.onResume()
        val uri = requireActivity().intent.data
        if (viewModel.verifyOAuthResponse(uri, getString(R.string.manifest_scheme))) {
            val code = viewModel.getCode(uri!!)

            viewModel.getAccessToken(code!!, redirectUrl)
                .observe(viewLifecycleOwner, Observer {
                    when (it) {
                        is ResultData.Loading -> showLoadingDialog(R.string.getting_access_token)
                        is ResultData.Success -> {
                            cancelLoadingScreen()
                            nextActivity(MainActivity::class.java)
                        }
                        is ResultData.Failure -> {
                            cancelLoadingScreen()
                            showToastMessage(it.message)
                        }
                    }
                })
        }
    }
}