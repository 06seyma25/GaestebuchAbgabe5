package com.example.aufgabe3.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aufgabe3.model.BookingEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SharedViewModel : ViewModel() {
    private val _bookingsEntries = MutableStateFlow<List<BookingEntry>>(emptyList())
    val bookingsEntries: StateFlow<List<BookingEntry>> = _bookingsEntries

    fun addBookingEntry(entry: BookingEntry) {
        _bookingsEntries.update { currentEntries -> currentEntries + entry }
    }

    fun deleteBookingEntry(entry: BookingEntry) {
        _bookingsEntries.update { currentEntries -> currentEntries - entry }
    }
}
