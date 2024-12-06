package com.example.aufgabe3.model

import java.time.LocalDate

data class BookingEntry(
    val name: String,
    val arrivalDate: LocalDate,
    val departureDate: LocalDate
)
