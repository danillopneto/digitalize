package br.go.dpn.digitalize.model

class ImageResult(val link: String, val title: String, val image: ImageDetails) {
    constructor() : this("","", ImageDetails())
}
