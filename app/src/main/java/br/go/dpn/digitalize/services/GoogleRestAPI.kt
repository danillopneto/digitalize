package br.go.dpn.digitalize.services

import API_KEY
import SEARCH_ENGINE_ID
import br.go.dpn.digitalize.model.ImageSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GoogleRestAPI {
    @Headers("Content-Type: application/json")
    @GET("/customsearch/v1?key=$API_KEY&cx=$SEARCH_ENGINE_ID&searchType=image")
    fun getImages(@Query("q") filter: String, @Query("start") startIndex: Int): Call<ImageSearchResult>
}