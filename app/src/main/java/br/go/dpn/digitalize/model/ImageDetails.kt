package br.go.dpn.digitalize.model

class ImageDetails(val height: Int, val width: Int, val thumbnailLink: String) {
    constructor() : this(0,0, "")
}