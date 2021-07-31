package id.pahlevikun.cookingtimer.common.extension

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import id.pahlevikun.cookingtimer.common.helper.LocalizationHelper
import java.util.regex.Pattern

fun String.capitalizeEveryFirstLetter(context: Context): String {
    val capBuffer = StringBuffer()
    val capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(this)
    while (capMatcher.find()) {
        capMatcher.appendReplacement(
            capBuffer,
            capMatcher.group(1).orEmpty()
                .toUpperCase(LocalizationHelper.getLocale(context)) + capMatcher.group(2).orEmpty()
                .toLowerCase(LocalizationHelper.getLocale(context))
        )
    }

    return capMatcher.appendTail(capBuffer).toString()
}

fun String?.toCapitalSentence(): String {
    return if (this != null) {
        if (this.isNullOrBlank()) {
            ""
        } else {
            Character.toUpperCase(this.toCharArray()[0]) + this.substring(1)
        }
    } else {
        ""
    }
}

fun String.removeNonNumeric(): String {
    val regex = Regex("[^0-9 ]")
    return this.replace(regex, "")
}

fun String?.isValidEmail(): Boolean {
    return TextUtils.isEmpty(this).not() && Patterns.EMAIL_ADDRESS.matcher(this.orEmpty()).matches()
}

fun String?.toIntOrZero(): Int {
    return try {
        this.orEmpty().toInt()
    } catch (e: NumberFormatException) {
        0
    }
}

fun String?.toLongOrZero(): Long {
    return try {
        this.orEmpty().toLong()
    } catch (e: NumberFormatException) {
        0L
    }
}