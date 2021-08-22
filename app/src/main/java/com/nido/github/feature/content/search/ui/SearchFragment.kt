package com.nido.github.feature.content.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nido.github.R
import com.nido.github.base.BaseFragment
import com.nido.github.data.ResultData
import com.nido.github.databinding.FragmentSearchBinding
import com.nido.github.di.helper.injectViewModel
import com.nido.github.feature.authentication.common.AuthenticationActivity
import com.nido.github.feature.content.owner.data.Owner
import com.nido.github.feature.content.search.data.Repository
import com.nido.github.feature.content.user.data.User
import com.nido.github.utils.toggleSideView
import com.nido.github.view.RecyclerItemDecorator

class SearchFragment : BaseFragment(), SearchListener {

    private val searchAdapter = SearchAdapter(this)
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater, container, false).apply {
        searchRepositoryRecyclerView.apply {
            adapter = searchAdapter
            addItemDecoration(
                RecyclerItemDecorator(
                    resources.getDimension(R.dimen.minimal_padding).toInt()
                )
            )
        }
        viewModel = injectViewModel(viewModelFactory)
        viewModel.setInitialQuery("Android")
        observeSearchQuery()
        observeUser(this)
        implementListeners(this)
        setSideToggleButton(this)
    }.root

    private fun observeSearchQuery() {
        viewModel.repositoryLive.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultData.Loading -> showLoadingDialog(R.string.loading_dialog_search_repository)
                is ResultData.Success -> {
                    cancelLoadingScreen()
                    if (it.value != null) {
                        searchAdapter.submitList(it.value)
                    }
                    viewModel.isLoadingOnScroll = false
                }
                is ResultData.Failure -> {
                    cancelLoadingScreen()
                    showToastMessage(it.message)
                    viewModel.isLoadingOnScroll = false
                }
            }
        })
    }

    private fun observeUser(binding: FragmentSearchBinding) {
        viewModel.getUserLive().observe(viewLifecycleOwner, Observer { userResult ->
            when (userResult) {
                is ResultData.Loading -> binding.searchToolbarParent.visibility = View.GONE
                is ResultData.Success -> {
                    if (userResult.value != null) {
                        binding.user = userResult.value
                        binding.searchToolbarParent.visibility = View.VISIBLE
                        binding.searchRepositoryUserProfile.setOnClickListener {
                            onUserProfileClick(userResult.value)
                        }
                        binding.searchRepositoryUserAvatar.setOnClickListener {
                            onUserProfileClick(userResult.value)
                        }
                    }
                }
                is ResultData.Failure -> {
                    binding.searchToolbarParent.visibility = View.GONE
                }
            }
        })
    }

    private fun implementListeners(binding: FragmentSearchBinding) {
        binding.searchQueryParent.setEndIconOnClickListener {
            initiateSearch(binding)
        }

        binding.searchQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                initiateSearch(binding)
            }
            true
        }


        binding.searchLogout.setOnClickListener { onLogoutClick() }

        binding.searchRepositoryRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!viewModel.isLoadingOnScroll) {
                        viewModel.nextPage()
                        viewModel.isLoadingOnScroll = true
                    }
                }
            }
        })
    }

    private fun initiateSearch(binding: FragmentSearchBinding) {
        binding.searchQuery.text.toString().also {
            if (it != "") {
                viewModel.searchRepositories(it)
            } else {
                showToastMessage(R.string.query_empty_warning)
            }
            clearSearchInputFocus(binding)
        }
    }

    private fun clearSearchInputFocus(binding: FragmentSearchBinding) {
        if (binding.searchQueryParent.hasFocus()) {
            binding.searchQueryParent.clearFocus()
        }
    }

    private fun setSideToggleButton(binding: FragmentSearchBinding) {
        binding.apply {
            searchSortParent.setOnLongClickListener {
                searchSortParent.toggleSideView(!viewModel.isToggleButtonShown).start()
                viewModel.isToggleButtonShown = !viewModel.isToggleButtonShown
                true
            }

            searchSortParent.setOnClickListener {
                viewModel.nextSort()
            }

            viewModel.getSortTypeLive().observe(viewLifecycleOwner, Observer { sort ->
                searchSortType.text = sort.getType()
            })
        }
    }

    private fun onLogoutClick() {
        viewModel.deleteUserData()
        nextActivity(AuthenticationActivity::class.java)
    }

    private fun onUserProfileClick(user: User) {
        val action = SearchFragmentDirections.actionSearchFragmentToUserFragment(user)
        findNavController().navigate(action)
    }

    override fun onSearchRepositoryClick(repository: Repository) {
        val action = SearchFragmentDirections.actionSearchFragmentToRepositoryFragment(repository)
        findNavController().navigate(action)
    }

    override fun onSearchOwnerClick(owner: Owner) {
        val action = SearchFragmentDirections.actionSearchFragmentToOwnerFragment(owner)
        findNavController().navigate(action)
    }
}