package com.nido.github.feature.content.search.data

import com.nido.github.data.BaseDataSource
import com.nido.github.feature.content.search.api.SearchApi
import javax.inject.Inject

class SearchDataSource @Inject constructor(private val api: SearchApi) : BaseDataSource() {
    suspend fun searchQuery(query: String, page: Int, perPage: Int, sortType: SortType) =
        getResult { api.searchQueryRepositories(query, page, perPage, sortType.getType()) }
}