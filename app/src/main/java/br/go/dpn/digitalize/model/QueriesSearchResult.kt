package br.go.dpn.digitalize.model

class QueriesSearchResult(val request: List<QuerieResult>, val nextPage: List<QuerieResult>) {
    constructor() : this(arrayListOf(), arrayListOf())
}