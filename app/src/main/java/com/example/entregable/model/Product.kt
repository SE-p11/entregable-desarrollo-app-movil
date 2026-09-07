package com.example.entregable.model

data class Product(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUri: String? = null
)