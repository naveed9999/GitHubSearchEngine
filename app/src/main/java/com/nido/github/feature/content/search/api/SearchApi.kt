package com.nido.github.feature.content.search.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/repositories")
    suspend fun searchQueryRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int,
        @Query("sort") sort: String
    ): Response<SearchResponse>
}