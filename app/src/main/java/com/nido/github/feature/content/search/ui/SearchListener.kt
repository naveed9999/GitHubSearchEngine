package com.nido.github.feature.content.search.ui

import com.nido.github.feature.content.owner.data.Owner
import com.nido.github.feature.content.search.data.Repository

interface SearchListener {
    fun onSearchRepositoryClick(repository: Repository)
    fun onSearchOwnerClick(owner: Owner)
}