package com.tagitech.provelib.exceptions

class OrderNotFoundException(orderId: String) : RuntimeException("Order with ID $orderId not found")
