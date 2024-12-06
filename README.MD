# Bewertungsschema für die Programmieraufgabe

**Gesamtpunktzahl: 10 Punkte**

## Ziel der Aufgabe

Es soll eine Android-App mit Jetpack Compose entwickeln, die folgende Funktionen beinhaltet:

- **Verwendung von DateTimePicker und Kalenderkomponenten.**
- **Ergänzung des bereitgestellten Codegerüsts.**
- **Implementierung spezifischer Funktionen gemäß den Anforderungen.**

---

## Bewertungskriterien und Punkteverteilung

### 1. `BookingEntry.kt` (1 Punkt)

- **Korrekte Implementierung der Datenklasse** (1 Punkt)
    - Attribute:
        - `arrivalDate` (0,3 Punkte): `LocalDate` oder entsprechender Typ zur Speicherung des Ankunftsdatums.
        - `departureDate` (0,3 Punkte): `LocalDate` oder entsprechender Typ zur Speicherung des Abreisedatums.
        - `name` (0,4 Punkte): `String` zur Speicherung des Namens der Buchung.

---

### 2. `AddScreen.kt` (4,5 Punkte)

- **Implementierung des `DateRangePickerModal`** (2,5 Punkte)
    - **Methode für den Dialog implementieren** (1 Punkt): Die Methode `DateRangePickerModal` soll den Dialog zur Datumsauswahl anzeigen.
    - **Logik zur Anzeige des Dialogs implementieren** (0,5 Punkte): Korrekte Integration des Dialogs in den `AddScreen`.
    - **Konvertierung der ausgewählten Long-Werte in `LocalDate`** (0,5 Punkte): Umwandlung der Werte und Speicherung in den entsprechenden Variablen.
    - **Error Handling** (0,5 Punkte): Validierung für fehlende Eingaben oder ungültige Daten.

- **Save-Button Funktionalität** (2 Punkte)
    - **Erstellen und Speichern eines `BookingEntry`-Objekts** (1 Punkt): Speicherung bei korrekten Eingaben im `SharedViewModel`.
    - **Fehlerbehandlung** (1 Punkt): Ausgabe einer Fehlermeldung bei ungültigen Eingaben.

---

### 3. `HomeScreen.kt` (3 Punkte)

- **Anzeige der Buchungseinträge** (1,5 Punkte)
    - **Verwendung von `LazyColumn`** (0,8 Punkte): Anzeige der Einträge, wenn die Liste `bookingsEntries` nicht leer ist.
    - **Anzeige bei leerer Liste** (0,7 Punkte): Nachricht bei leerer Liste.

- **`BookingEntryItem` Composable** (1,5 Punkte)
    - **Anzeige des Namens** (0,7 Punkte): Korrekte Darstellung des Namens.
    - **Anzeige des Datums im richtigen Format** (0,8 Punkte): Formatierung der Ankunfts- und Abreisedaten (`dd.MM.yyyy`).

---

### 4. `SharedViewModel.kt` (1,5 Punkte)

- **`addBookingEntry`-Methode implementieren** (1 Punkt): Hinzufügen neuer Einträge zur Liste.
- **`deleteBookingEntry`-Methode implementieren** (0,5 Punkte): Entfernen von Einträgen aus der Liste.

---

## Hinweise für die Studierenden

- **Fokus der Aufgabe:**
    - Hauptaugenmerk liegt auf der korrekten Implementierung des `DateRangePickers` und der Verarbeitung der ausgewählten Daten.
    - Verständnis von Jetpack Compose und State-Management mit ViewModels.

- **Dokumentation:**
    - Es wird empfohlen, die offizielle Android-Dokumentation zu Jetpack Compose zu verwenden.

---

**Viel Erfolg bei der Umsetzung der Aufgabe!**
