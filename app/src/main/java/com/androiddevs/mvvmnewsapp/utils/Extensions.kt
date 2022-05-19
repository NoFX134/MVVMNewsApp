package com.androiddevs.mvvmnewsapp.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun clearDescription(description: String?): String? {
    return description?.let { Regex("""[</a-z>]""").replace(it, "") }
}
fun editDate(publishedAt: String?):String{
    return ZonedDateTime.parse(publishedAt)
        .format(DateTimeFormatter.ofPattern(Constants.TIME_PATTERN, Locale("ru")))
}
