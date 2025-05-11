package com.artiuil.lab.cleverdictionary.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun formatLastOpened(lastOpened: Long): String {
    val lastOpenedDate = Instant.ofEpochMilli(lastOpened).atZone(ZoneId.systemDefault()).toLocalDate()
    val today = LocalDate.now()
    val daysDiff = ChronoUnit.DAYS.between(lastOpenedDate, today)
    return when {
        daysDiff < 1 -> "Today"
        daysDiff < 7 -> "Last Week"
        daysDiff < 30 -> "Last Month"
        else -> lastOpenedDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH))
    }
}

fun formatCreationDate(created: Long): String {
    if (created == 0L) return "Unknown"
    val createdDate = Instant.ofEpochMilli(created).atZone(ZoneId.systemDefault()).toLocalDate()
    return createdDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH))
}