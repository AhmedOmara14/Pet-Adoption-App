package com.omaradev.pet_adoption.domain.models

import com.omaradev.pet_adoption.data.dto.all_animals.PhotoNetwork
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class Animal(
    val age: String? = null,
    val attributes: Attributes? = null,
    val breeds: Breeds? = null,
    val coat: String? = null,
    val colors: Colors? = null,
    val contact: Contact? = null,
    val description: String? = null,
    val distance: Double? = null,
    val gender: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val photo: List<Photo>? = null,
    val size: String? = null,
    val status: String? = null,
    val published_at: String? = null,
    val tags: List<String>? = null,
    val type: String? = null,
) {
    fun convertDate(_date: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        return try {
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date = inputFormat.parse(_date) as Date
            outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null // Handle parsing error
        }
    }
}