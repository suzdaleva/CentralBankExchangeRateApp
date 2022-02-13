package com.manicpixie.cfttest.domain.model


data class Currency(
    val charCode: String,
    var name: String,
    val nominal: Int,
    val previous: Double,
    val value: Double
)
