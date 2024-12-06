package com.example.aufgabe3.ui.add

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aufgabe3.model.BookingEntry
import com.example.aufgabe3.viewmodel.SharedViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current
    val existingEntries = sharedViewModel.bookingsEntries.collectAsState().value

    var name by remember { mutableStateOf("") }
    var arrivalDate by remember { mutableStateOf<LocalDate?>(null) }
    var departureDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDateRangePicker by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Booking Entry") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Eingabefeld für den Namen
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Eingabefeld für den Datumsbereich
            OutlinedTextField(
                value = if (arrivalDate != null && departureDate != null) {
                    "${arrivalDate!!.format(dateFormatter)} - ${departureDate!!.format(dateFormatter)}"
                } else {
                    ""
                },
                onValueChange = {},
                label = { Text("Select Date Range") },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDateRangePicker = true },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Speichern-Button
            Button(
                onClick = {
                    // Validierung
                    when {
                        name.isBlank() -> {
                            Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                        existingEntries.any { it.name == name } -> {
                            Toast.makeText(context, "Duplicate name is not allowed", Toast.LENGTH_SHORT).show()
                        }
                        arrivalDate == null || departureDate == null -> {
                            Toast.makeText(context, "Please select a valid date range", Toast.LENGTH_SHORT).show()
                        }
                        departureDate!!.isBefore(arrivalDate!!) -> {
                            Toast.makeText(context, "End date cannot be earlier than start date", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            val newBooking = BookingEntry(
                                name = name,
                                arrivalDate = arrivalDate!!,
                                departureDate = departureDate!!
                            )
                            sharedViewModel.addBookingEntry(newBooking)
                            navController.popBackStack() // Zurück zur vorherigen Ansicht
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }

    // Zeige den DateRangePickerModal
    if (showDateRangePicker) {
        DateRangePickerModal(
            onDismissRequest = { showDateRangePicker = false },
            onDateSelected = { startDate, endDate ->
                arrivalDate = startDate
                departureDate = endDate
                if (endDate.isBefore(startDate)) {
                    Toast.makeText(context, "End date cannot be earlier than start date", Toast.LENGTH_SHORT).show()
                }
                showDateRangePicker = false
            }
        )
    }
}

@Composable
fun DateRangePickerModal(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate, LocalDate) -> Unit
) {
    val context = LocalContext.current

    // Zustände für Start- und Enddatum
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var isSelectingStartDate by remember { mutableStateOf(true) } // Start- oder Enddatum wählen

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            if (isSelectingStartDate) {
                startDate = selectedDate
                isSelectingStartDate = false // Wechsel zu Enddatum
                DatePickerDialog(
                    context,
                    { _, year2, month2, dayOfMonth2 ->
                        val selectedEndDate = LocalDate.of(year2, month2 + 1, dayOfMonth2)
                        endDate = selectedEndDate
                        onDateSelected(startDate!!, endDate!!)
                        onDismissRequest()
                    },
                    selectedDate.year,
                    selectedDate.monthValue - 1,
                    selectedDate.dayOfMonth
                ).show()
            }
        },
        LocalDate.now().year,
        LocalDate.now().monthValue - 1,
        LocalDate.now().dayOfMonth
    ).apply {
        setOnCancelListener { onDismissRequest() }
    }.show()
}

