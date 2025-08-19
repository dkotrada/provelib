package com.tagitech.provelib.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class OrderItemDto(
    @field:NotBlank(message = "Product ID cannot be blank")
    val productId: String,

    @field:Positive(message = "Quantity must be positive")
    val quantity: Int,

    @field:Positive(message = "Price must be positive")
    val price: Double
)
