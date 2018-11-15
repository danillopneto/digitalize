package br.go.dpn.digitalize.model

class ImageSearchResult(val items: List<ImageResult>, val queries: QueriesSearchResult) {
    constructor() : this(arrayListOf(), QueriesSearchResult())
}