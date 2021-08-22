package com.nido.github.feature.content.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.nido.github.base.BaseFragment
import com.nido.github.databinding.FragmentUserBinding

class UserFragment : BaseFragment() {

    private val args: UserFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUserBinding.inflate(inflater, container, false).apply {
        user = args.user
        userExternalUrl.setOnClickListener {
            args.user.htmlUrl?.let {
                openUrlInBrowser(it)
            }
        }
    }.root
}
